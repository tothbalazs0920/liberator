package productfinder.mail;

import org.junit.Test;
import productfinder.properties.PropertiesMap;

public class MailTest {

    @Test
    public void can_send_email() {
        new Mail(new PropertiesMap()).sendMail("x", "y");
    }
}
