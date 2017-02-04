package productfinder.main;

import productfinder.dao.Product;
import productfinder.dao.ProductDao;
import productfinder.mail.Mail;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import productfinder.scraper.Listing;
import productfinder.scraper.Scraper;

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
            Date date = new java.util.Date();
            Product product = new Product(url,
                    price,
                    "DKK",
                    "dba",
                    name,
                    item,
                    new Timestamp(date.getTime()));
            if (url == null) {
                continue;
            }
            if (!isItemRelevant(product, item)) {
                continue;
            }
            if (price > threshold) {
                continue;
            }
            mail.sendMail("dba: " + url, " " +
                    url + " " + "price: " +
                    price + " description: " + name);
            count++;
        }
        return count;
    }

    public boolean isItemRelevant(Product product, String item) {
        if ((product.getID().contains(item) ||
                product.getDescription().contains(item)) && !productDao.isIdInDb(product.getID())) {
            productDao.create(product);
            return true;
        }
        return false;
    }
}
