package productfinder.dao;


import java.sql.Timestamp;

public class Product {

    private String ID;
    private double price;
    private String currency;
    private String site;
    private String description;
    private String productType;
    private Timestamp postedAt;

    public Product(String ID,
                   double price,
                   String currency,
                   String site,
                   String description,
                   String productType,
                   Timestamp postedAt) {
        this.ID = ID;
        this.price = price;
        this.currency = currency;
        this.site = site;
        this.description = description;
        this.productType = productType;
        this.postedAt = postedAt;
    }

    public String getID() {
        return ID;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getSite() {
        return site;
    }

    public String getDescription() {
        return description;
    }

    public String getProductType() {
        return productType;
    }

    public Timestamp getPostedAt() {
        return postedAt;
    }
}
