package productfinder.main;


import productfinder.dao.ProductDao;
import productfinder.mail.Mail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.mockito.Mockito;
import productfinder.scraper.Scraper;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ControllerScraperIntegrationTest {

    @Test
    public void process_returns_expected_amount_of_items() {
        Mail mail = Mockito.mock(Mail.class);
        ProductDao productDao = Mockito.mock(ProductDao.class);
        Controller controller = new Controller(new Scraper(), mail, productDao);
        File input = new File("voxListingPage.htm");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "http://www.dba.dk/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int found = controller.process(doc, "vox", 10000);
        assertEquals(14, found);
        verify(mail, times(14)).sendMail(anyString(), anyString());
    }
}
