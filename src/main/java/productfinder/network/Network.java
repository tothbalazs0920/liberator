package productfinder.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class Network {
    final static String DIR = "c:\\Program Files\\pia_manager\\";
    final static String SHUT_DOWN = DIR + "taskkill /F /IM pia_manager.exe /T";
    final static String START = DIR + "pia_manager.exe";

    public void startVpn() {
        executeCommand(START);
    }

    public void shutDownVpn() {
        executeCommand(SHUT_DOWN);
    }

    private void executeCommand(String command) {
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Document connect(String url, String item) {
        try {
            return Jsoup.connect(url + item)
                    .userAgent(new UserAgents().getUserAgent())
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
