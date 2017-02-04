package productfinder.main;

import productfinder.dao.ProductDao;
import productfinder.mail.Mail;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import productfinder.network.Network;
import productfinder.properties.PropertiesMap;
import productfinder.scraper.Scraper;

import java.util.Random;

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
        Network network = new Network();
        while (1 == 1) {
            network.startVpn();
            Sleep(60000);
            controller.process(network.connect(url, item), item, threshold);
            Sleep(60000);
            network.shutDownVpn();
            Sleep(580000 + new Random().nextInt(6000));
        }
    }

    public static void Sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
