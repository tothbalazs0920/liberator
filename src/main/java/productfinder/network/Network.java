package productfinder.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Random;

public class Network {
    final static String DIR = "/etc/openvpn/";//"c:\\Program Files\\pia_manager\\";
    final static String SHUT_DOWN = DIR + "sudo killall openvpn";//"taskkill /F /IM pia_manager.exe /T";
    final static String START = DIR + "sudo openvpn"; //"pia_manager.exe";
    private String[] servers = {"Romania.ovpn", "Denmark.ovpn", "Norway.ovpn", "Switzerland.ovpn"};

    public void startVpn() {
        executeCommand(START + " " + servers[new Random().nextInt(4)]);
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
