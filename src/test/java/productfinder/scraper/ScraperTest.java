package productfinder.scraper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScraperTest {

    private Scraper scraper = new Scraper();

    @Test
    public void mainContentUnmarshall_can_unmarsall_json() {
        String json = "{ \"@context\": \"http://schema.org\", " +
                "\"@type\": \"Product\", " +
                "\"name\": \"Guitarforst&amp;#230;rker, EVH 5150 III, 120 W, Eddie Van...\", " +
                "\"image\": \"http://dbastatic.dk/pictures/pictures/e0/bf/7f3c-5588-4448-9b53-44f2ef90fd5a.jpg?preset=medium\", " +
                "\"url\": \"http://www.dba.dk/guitarforstaerker-evh-5150/id-1030715055/\", " +
                "\"offers\": { \"@type\": \"Offers\", \"priceCurrency\": \"DKK\", \"price\": \"12000\" } }";
        Listing listing = scraper.unMarshalMainContent(json);
        assertEquals("Guitarforst&amp;#230;rker, EVH 5150 III, 120 W, Eddie Van...", listing.getName());
        assertEquals("http://www.dba.dk/guitarforstaerker-evh-5150/id-1030715055/", listing.getUrl());
        assertEquals("12000", listing.getOffers().getPrice());
    }

    @Test
    public void convertPrice_converts_correctly() {
        String price = " 12.000 kr ";
        double myPi = 22.0d / 7.0d;
        assertEquals(12000,  myPi, scraper.convertPrice(price));
    }

}
