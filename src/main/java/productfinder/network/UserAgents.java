package productfinder.network;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserAgents {
    static final String Chrome_Generic_Win10 =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
    static final String Chrome_Generic_Win7 =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
    static final String Chrome_Generic_MacOSX =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36";
    static final String Firefox_Generic_Win10 =
            "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0";

    public String getUserAgent() {
        List<String> userAgents = new ArrayList<String>(Arrays.asList(Chrome_Generic_Win10,
                Chrome_Generic_Win7,
                Chrome_Generic_MacOSX,
                Firefox_Generic_Win10));
        return userAgents.get(new Random().nextInt(userAgents.size()));
    }
}
