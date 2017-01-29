package main;


import liberator.Controller;
import mail.Mail;
import org.junit.Test;
import properties.PropertiesMap;
import scraper.Scraper;

public class MainTest {

    @Test
    public void mainTest(){
        String url = "http://www.dba.dk/soeg/?soeg=";
        String item = "kemper";
        double threshold = 10500;
        Controller controller = new Controller(new Scraper(), new Mail(new PropertiesMap()));
        int found = controller.process(controller.connect(url, item), item, threshold);
        System.out.println("");
    }
}
