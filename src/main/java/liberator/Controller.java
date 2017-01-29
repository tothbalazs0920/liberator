package liberator;

import mail.Mail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scraper.Listing;
import scraper.Scraper;

import java.io.IOException;

public class Controller {

    private Scraper scraper;
    private Mail mail;

    public Controller(Scraper scraper, Mail mail) {
        this.scraper = scraper;
        this.mail = mail;
    }

    public Document connect(String url, String item) {
        try {
            return Jsoup.connect(url + item).get();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public int process(Document doc, String item, double threshold) {
        if (doc == null) {
            return 0;
        }

        Elements dbaListings = scraper.getDbaListings(doc);
        if (dbaListings == null) {
            return 0;
        }
        int count = 0;
        for (Element dbaListing : dbaListings) {
            String url = null;
            String name = null;
            double price = Double.MAX_VALUE;
            Listing listing = scraper.getListing(dbaListing);
            if (listing != null) {
                if (listing.getName() != null) {
                    name = listing.getName();
                }
                if (listing.getUrl() != null) {
                    url = listing.getUrl();
                } else {
                    url = scraper.getUrl(dbaListing);
                }
                if (listing.getOffers() != null
                        && listing.getOffers().getPrice() != null) {
                    price = scraper.convertPrice(listing.getOffers().getPrice());
                } else {
                    price = scraper.getPrice(dbaListing);
                }
            }
            if (url == null) {
                continue;
            }
            if (!scraper.isItemRelevant(url, name, item)) {
                continue;
            }
            if (price > threshold) {
                continue;
            }
            mail.sendMail("dba: " + url, " " +
                    url + " " + "price: " +
                    price + "description: " + name);
            System.out.println("Send sms with " + url);
            count++;
        }
        return count;
    }
}
