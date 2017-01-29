package mail;

import org.junit.Test;
import properties.PropertiesMap;

public class MailTest {

    @Test
    public void can_send_email() {
        new Mail(new PropertiesMap()).sendMail("x", "y");
    }
}
