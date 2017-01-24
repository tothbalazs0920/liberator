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
    public void scrapeTest(){
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
    public void isItemRelevantTest(){
        String item = "url";
        String url1 = "url1";
        String url2 = "url2";
        String url3 = "url3";
        assertTrue(scraper.isItemRelevant(url1, item));
        assertTrue(scraper.isItemRelevant(url2, item));
        assertTrue(scraper.isItemRelevant(url3, item));
        assertFalse(scraper.isItemRelevant(url1, item));
        assertFalse(scraper.isItemRelevant(url2, item));
    }

    @Test
    public void mainContentUnmarshallTest() {
        String json = "{ \"@context\": \"http://schema.org\", " +
                "\"@type\": \"Product\", " +
                "\"name\": \"Guitarforst&amp;#230;rker, EVH 5150 III, 120 W, Eddie Van...\", " +
                "\"image\": \"http://dbastatic.dk/pictures/pictures/e0/bf/7f3c-5588-4448-9b53-44f2ef90fd5a.jpg?preset=medium\", " +
                "\"url\": \"http://www.dba.dk/guitarforstaerker-evh-5150/id-1030715055/\", " +
                "\"offers\": { \"@type\": \"Offer\", \"priceCurrency\": \"DKK\", \"price\": \"12000\" } }";

    }

}
