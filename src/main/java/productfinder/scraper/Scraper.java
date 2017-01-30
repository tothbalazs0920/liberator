package productfinder.scraper;

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
        this.checkedItems = new HashSet<String>();
    }

    public Document connect(String url, String item) {
        try {
            return Jsoup.connect(url + item).get();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Elements getDbaListings(Document doc) {
        if (doc.getElementsByClass("dbaListing") != null) {
            return doc.getElementsByClass("dbaListing");
        }
        return null;
    }

    public Listing getListing(Element dbaListing) {
        String mainContent = dbaListing.getElementsByTag("script").get(0).dataNodes().get(0).getWholeData();
        mainContent = mainContent.replace("\r\n", "");
        return unMarshalMainContent(mainContent);
    }

    public double convertPrice(String price) {
        try {
            return Double.parseDouble(price.replaceAll("\\D+", ""));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Double.MAX_VALUE;
        }
    }

    public double getPrice(Element dbaListing) {
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


    public String getUrl(Element dbaListing) {
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
}
