package main;


import productfinder.dao.ProductDao;
import productfinder.liberator.Controller;
import productfinder.mail.Mail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import productfinder.scraper.Scraper;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ControllerUnitTest {

    @Mock
    private Scraper scraper;
    @Mock
    private Mail mail;
    @Mock
    private ProductDao productDao;

    @InjectMocks
    private Controller controller;

    @Test
    public void process_returns_expected_amount_of_items(){
        File input = new File("voxListingPage.htm");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "http://www.dba.dk/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int found = controller.process(doc, "vox", 10000);
        assertEquals(14, found);
    }

    @Test
    public void isItemRelevant_doesnt_return_multiple_times_for_the_same_url_input(){
        String item = "url";
        String name = "name";
        String url1 = "url1";
        assertTrue(controller.isItemRelevant(url1, name, item));
        assertFalse(controller.isItemRelevant(url1, name, item));
    }

    @Test
    public void isItemRelevant_returns_true_if_name_is_null_and_url_is_relevant(){
        String item = "url";
        String url1 = "url1";
        assertTrue(controller.isItemRelevant(url1, null, item));
    }
}
