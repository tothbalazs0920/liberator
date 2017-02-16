package productfinder.main;

import productfinder.network.Network;

public class Vpn {

    public static void main(String[] args) {
        Network network = new Network();
        network.startVpn();
        System.out.println("Connecting to vpn");
        Sleep(15000);
        network.shutDownVpn();
        System.out.println("Shutting down vpn");
        network.startVpn();
        Sleep(15000);
        System.out.println("Connecting to another vpn");
        network.shutDownVpn();
        System.out.println("Shutting down vpn");
    }

    public static void Sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
