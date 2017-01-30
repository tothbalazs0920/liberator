package productfinder.liberator;

import productfinder.dao.Product;
import productfinder.dao.ProductDao;
import productfinder.mail.Mail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import productfinder.scraper.Listing;
import productfinder.scraper.Scraper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;


public class Controller {

    private ProductDao productDao;
    private Scraper scraper;
    private Mail mail;

    public Controller(Scraper scraper, Mail mail, ProductDao productDao) {
        this.scraper = scraper;
        this.mail = mail;
        this.productDao = productDao;
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
            if (!isItemRelevant(url, name, item)) {
                continue;
            }
            if (price > threshold) {
                continue;
            }
//            productfinder.mail.sendMail("dba: " + url, " " +
//                    url + " " + "price: " +
//                    price + "description: " + name);
            System.out.println("Send sms with " + url);
            count++;
        }
        return count;
    }

    void saveToDb(String url, String item) {


    }

    public boolean isItemRelevant(String url, String name, String item) {
        Date date = new java.util.Date();
        Product product = new Product(url,45,
                "DKK",
                "dba",
                name,
                item, new Timestamp(date.getTime()));
        productDao.create(product);
        if (/*!checkedItems.contains(url) && */(url.contains(item) || name.contains(item))) {
            saveToDb(url, item);
            return true;
        }
        saveToDb(url, item);
        return false;
    }
}
