package scraper;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Listing {
    private String name;
    private String url;
    private Offers offers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Offers getOffers() {
        return offers;
    }

    public void setOffers(Offers offers) {
        this.offers = offers;
    }
}
