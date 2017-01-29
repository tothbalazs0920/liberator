package properties;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PropertiesMapTest {

    @Test
    public void can_read_properties() {
        PropertiesMap map = new PropertiesMap();
        assertTrue(map.get("password") != null);
        assertTrue(map.get("fromEmail") != null);
        assertTrue(map.get("toEmail") != null);
    }
}
