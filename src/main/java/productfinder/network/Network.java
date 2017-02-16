package productfinder.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Random;

public class Network {

    private String[] servers = {"Romania.ovpn", "Denmark.ovpn", "Norway.ovpn", "Switzerland.ovpn"};

    public void startVpn() {
        String[] command = { "sudo", "openvpn", "/etc/openvpn/" + servers[new Random().nextInt(4)] };
        System.out.println(command);
        executeCommand(command);
    }

    public void shutDownVpn() {
        String[] command = { "sudo", "killall", "openvpn" };
        executeCommand(command);
    }

    private void executeCommand(String[] command) {
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