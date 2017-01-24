package scraper;

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
            String mainContent = dbaListing.getElementsByTag("script").get(0).dataNodes().get(0).getWholeData();
            mainContent = mainContent.replace("\r\n", "");
            String url = getMainContent(dbaListing);
            if (url == null) {
                continue;
            }
            if (!isItemRelevant(url, item)) {
                continue;
            }
            double price = getPrice(dbaListing);
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

    double convertPrice(String price) {
        try {
            String amount = price.replaceAll("\\D+", "");
            return Double.parseDouble(amount);
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
            if(amount > 100000) {
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


    String getMainContent(Element dbaListing) {
        if (dbaListing.getElementsByClass("listingLink") != null
                && dbaListing.getElementsByClass("listingLink").get(0) != null) {
            return dbaListing.getElementsByClass("listingLink").get(0).attr("href");
        }
        return null;
    }

    void saveToDb(String url, String item) {
        checkedItems.add(url);
    }

    boolean isItemRelevant(String url, String item) {
        if (!checkedItems.contains(url) && url.contains(item)) {
            saveToDb(url, item);
            return true;
        }
        saveToDb(url, item);
        return false;
    }
}
