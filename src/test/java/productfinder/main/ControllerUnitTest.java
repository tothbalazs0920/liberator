package productfinder.main;


import productfinder.dao.Product;
import productfinder.dao.ProductDao;
import productfinder.mail.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import productfinder.scraper.Scraper;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
    public void isItemRelevant_doesnt_return_multiple_times_for_the_same_url_input(){
        String item = "url";
        String name = "name";
        String url1 = "url1";
        Date date = new java.util.Date();
        Product product = new Product(url1,
                45,
                "DKK",
                "dba",
                name,
                item,
                new Timestamp(date.getTime()));
        when(productDao.isIdInDb(url1)).thenReturn(true);
        assertFalse(controller.isItemRelevant(product, item));
    }

    @Test
    public void isItemRelevant_returns_true_if_name_is_null_and_url_is_relevant(){
        String item = "url";
        String name = "name";
        String url1 = "url1";
        Date date = new java.util.Date();
        Product product = new Product(url1,
                45,
                "DKK",
                "dba",
                name,
                item,
                new Timestamp(date.getTime()));
        assertTrue(controller.isItemRelevant(product, item));
    }
}
