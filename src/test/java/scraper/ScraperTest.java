package scraper;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

public class ScraperTest {

    Scraper scraper = new Scraper();

    @Test
    public void scrape_returns_expected_amount_of_items(){
        File input = new File("voxListingPage.htm");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "http://www.dba.dk/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scraper scraper = new Scraper();
        int found = scraper.scrape(doc, "vox", 10000);
        assertEquals(14, found);
    }

    @Test
    public void mainTest(){
        String url = "http://www.dba.dk/soeg/?soeg=";
        String item = "kemper";
        double threshold = 10000;
        Scraper scraper = new Scraper();
        int found = scraper.scrape(scraper.connect(url, item), item, threshold);
        System.out.println("");
    }


    @Test
    public void isItemRelevant_doesnt_return_multiple_times_for_the_same_url_input(){
        String item = "url";
        String name = "name";
        String url1 = "url1";
        assertTrue(scraper.isItemRelevant(url1, name, item));
        assertFalse(scraper.isItemRelevant(url1, name, item));
    }

    @Test
    public void isItemRelevant_returns_true_if_name_is_null_and_url_is_relevant(){
        String item = "url";
        String url1 = "url1";
        assertTrue(scraper.isItemRelevant(url1, null, item));
    }

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
