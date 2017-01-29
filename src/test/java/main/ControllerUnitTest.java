package main;


import liberator.Controller;
import mail.Mail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import scraper.Scraper;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ControllerUnitTest {

    @Mock
    private Scraper scraper;
    @Mock
    private Mail mail;

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
}
