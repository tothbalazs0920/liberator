package liberator;

import mail.Mail;
import properties.PropertiesMap;
import scraper.Scraper;

public class Main {
    public static void main(String[] args) {

        String url = "http://www.dba.dk/soeg/?soeg=vox";
        String item = "vox";
        double threshold = 5000;

        PropertiesMap properties = new PropertiesMap();
        Scraper scraper = new Scraper();
        Mail mailSender = new Mail(properties);
        Controller controller = new Controller(scraper, mailSender);

        while (1 == 1) {
            controller.process(scraper.connect(url, item), item, threshold);
            try {
                Thread.sleep(3000);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                continue;
            }
        }
    }

}
