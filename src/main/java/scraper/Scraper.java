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
    int count = 0;

    public Scraper() {
        checkedItems = new HashSet<String>();
    }

    public void scrape(String item, double threshold) {
        try {
            Document doc = Jsoup.connect("http://www.dba.dk/soeg/?soeg=" + item).get();
            Elements dbaListings = doc.getElementsByClass("dbaListing");

            for (Element dbaListing : dbaListings) {
                String url = getMainContent(dbaListing);
                if(url == null){
                    continue;
                }
                if(!isItemRelevant(url, item)) {
                    continue;
                }
                double price = getPrice(dbaListing);
                if(price > threshold) {
                    continue;
                }
                System.out.println("Send sms with " + url );
                count++;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(count);
    }

    double convertPrice(String price) {
        try {
            return Double.parseDouble(price.replaceAll("\\D+", ""));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return Double.MAX_VALUE;
    }

    double getPrice(Element dbaListing) {
        if (dbaListing.getElementsByAttributeValue("title", "Pris") != null
                && dbaListing.getElementsByAttributeValue("title", "Pris").get(0) != null
                && dbaListing.getElementsByAttributeValue("title", "Pris").get(0)
                .childNode(0) != null
                && dbaListing.getElementsByAttributeValue("title", "Pris").get(0)
                .childNode(0).toString() != null) {
            return convertPrice(dbaListing
                            .getElementsByAttributeValue("title", "Pris")
                            .get(0)
                            .childNode(0)
                            .toString());
        }
        return Double.MAX_VALUE;
    }

    String getMainContent(Element dbaListing) {
        if( dbaListing.getElementsByClass("listingLink") != null
                &&  dbaListing.getElementsByClass("listingLink").get(0) != null) {
            return dbaListing.getElementsByClass("listingLink").get(0).attr("href");
        }
        return null;
    }

    void saveToDb(String url, String item) {
        checkedItems.add(url);
    }

    boolean isItemRelevant(String url, String item) {
        if(!checkedItems.contains(url) && url.contains(item)) {
            saveToDb(url, item);
            return true;
        }
        saveToDb(url, item);
        return false;
    }
}
