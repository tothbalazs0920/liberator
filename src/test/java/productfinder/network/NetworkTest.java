package productfinder.network;


import org.junit.Test;
import productfinder.main.Main;

public class NetworkTest {

    private Network network = new Network();

    @Test
    public void can_shut_down_vpn() {
        network.shutDownVpn();
    }

    @Test
    public void can_start_vpn() {
        network.startVpn();
    }

    @Test
    public void can_shut_down_and_start_vpn(){
        network.startVpn();
        Main.Sleep(60000);
        network.shutDownVpn();
        Main.Sleep(10000);
        network.startVpn();
        Main.Sleep(60000);
        network.shutDownVpn();
    }
}
