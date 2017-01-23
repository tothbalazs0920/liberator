package scraper;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ScraperTest {

    Scraper scraper = new Scraper();

    @Test
    public void scrapeTest(){
        scraper.scrape("marshall", 10000);
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

}
