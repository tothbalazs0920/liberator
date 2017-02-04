package productfinder.properties;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesMap extends HashMap<String, String> {

    private InputStream inputStream;

    public PropertiesMap() {
        super();
        getPropValues();
    }

    public void getPropValues() {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            this.put("password", prop.getProperty("password"));
            this.put("fromEmail", prop.getProperty("fromEmail"));
            this.put("toEmail", prop.getProperty("toEmail"));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
