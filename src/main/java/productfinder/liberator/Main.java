package productfinder.liberator;

import productfinder.dao.ProductDao;
import productfinder.mail.Mail;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import productfinder.properties.PropertiesMap;
import productfinder.scraper.Scraper;

public class Main {
    public static void main(String[] args) {

        String url = "http://www.dba.dk/soeg/?soeg=";
        String item = "vox";
        double threshold = 10000;
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        PropertiesMap properties = new PropertiesMap();
        Scraper scraper = new Scraper();
        Mail mailSender = new Mail(properties);
        ProductDao productDao = applicationContext.getBean(ProductDao.class);
        Controller controller = new Controller(scraper, mailSender, productDao);

        //while (1 == 1) {
            controller.process(scraper.connect(url, item), item, threshold);
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                //continue;
            }
        //}
    }

}
