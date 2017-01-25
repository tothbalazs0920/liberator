package scraper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Scraper {

    private final Set<String> checkedItems;

    public Scraper() {
        checkedItems = new HashSet<String>();
    }

    public static void main(String[] args) {

        String url = "http://www.dba.dk/soeg/?soeg=vox";
        String item = "vox";
        double threshold = 5000;
        Scraper scraper = new Scraper();
        while (1 == 1) {
            scraper.scrape(scraper.connect(url, item), item, threshold);
            try {
                Thread.sleep(3000);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                continue;
            }
        }
    }

    Document connect(String url, String item) {
        try {
            return Jsoup.connect(url + item).get();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public int scrape(Document doc, String item, double threshold) {
        if (doc == null) {
            return 0;
        }

        Elements dbaListings = getDbaListings(doc);
        if (dbaListings == null) {
            return 0;
        }
        int count = 0;
        for (Element dbaListing : dbaListings) {
            String url = null;
            String name = null;
            double price = Double.MAX_VALUE;
            Listing listing = getListing(dbaListing);
            if (listing != null) {
                if (listing.getName() != null) {
                    name = listing.getName();
                }
                if (listing.getUrl() != null) {
                    url = listing.getUrl();
                } else {
                    url = getUrl(dbaListing);
                }
                if (listing.getOffers() != null
                        && listing.getOffers().getPrice() != null) {
                    price = convertPrice(listing.getOffers().getPrice());
                } else {
                    price = getPrice(dbaListing);
                }
            }
            if (url == null) {
                continue;
            }
            if (!isItemRelevant(url, name, item)) {
                continue;
            }
            if (price > threshold) {
                continue;
            }
            System.out.println("Send sms with " + url);
            count++;
        }
        return count;
    }

    Elements getDbaListings(Document doc) {
        if (doc.getElementsByClass("dbaListing") != null) {
            return doc.getElementsByClass("dbaListing");
        }
        return null;
    }

    Listing getListing(Element dbaListing) {
        String mainContent = dbaListing.getElementsByTag("script").get(0).dataNodes().get(0).getWholeData();
        mainContent = mainContent.replace("\r\n", "");
        return unMarshalMainContent(mainContent);
    }

    double convertPrice(String price) {
        try {
            return Double.parseDouble(price.replaceAll("\\D+", ""));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Double.MAX_VALUE;
        }
    }

    double getPrice(Element dbaListing) {
        if (dbaListing.getElementsByAttributeValue("title", "Pris") != null
                && dbaListing.getElementsByAttributeValue("title", "Pris").get(0) != null
                && dbaListing.getElementsByAttributeValue("title", "Pris").get(0)
                .childNode(0) != null
                && dbaListing.getElementsByAttributeValue("title", "Pris").get(0)
                .childNode(0).toString() != null) {
            double amount = convertPrice(dbaListing
                    .getElementsByAttributeValue("title", "Pris")
                    .get(0)
                    .childNode(0)
                    .toString());
            if (amount > 100000) {
                if (dbaListing.getElementsByAttributeValue("title", "Pris").get(0)
                        .childNode(0).childNode(0) != null) {
                    return convertPrice(dbaListing
                            .getElementsByAttributeValue("title", "Pris")
                            .get(0)
                            .childNode(0)
                            .childNode(0)
                            .toString());
                }
            }
            return amount;
        }
        return Double.MAX_VALUE;
    }


    String getUrl(Element dbaListing) {
        if (dbaListing.getElementsByClass("listingLink") != null
                && dbaListing.getElementsByClass("listingLink").get(0) != null) {
            return dbaListing.getElementsByClass("listingLink").get(0).attr("href");
        }
        return null;
    }

    Listing unMarshalMainContent(String mainContent) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(mainContent, Listing.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void saveToDb(String url, String item) {
        checkedItems.add(url);
    }

    boolean isItemRelevant(String url, String name, String item) {
        if (!checkedItems.contains(url) && (url.contains(item) || name.contains(item))) {
            saveToDb(url, item);
            return true;
        }
        saveToDb(url, item);
        return false;
    }
}
