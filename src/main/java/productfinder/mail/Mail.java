package productfinder.mail;

import productfinder.properties.PropertiesMap;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

    private PropertiesMap propertiesMap;

    public Mail(PropertiesMap propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    public void sendMail(String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(propertiesMap.get("fromEmail"), propertiesMap.get("password"));
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(propertiesMap.get("fromEmail")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(propertiesMap.get("toEmail")));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
