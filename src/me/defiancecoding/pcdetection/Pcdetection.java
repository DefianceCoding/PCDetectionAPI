package me.defiancecoding.antibot.api.proxycheck;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by DefianceCoding on 12/21/2017. Updated 7/20/18 to /v2 API
 */
public final class PCDetection {

    public String status;
    public String node;
    public String ip;
    public String asn;
    public String provider;
    public String country;
    public String isocode;
    public String proxy;
    public String type;
    public String port;
    public String last_seen_human;
    public String last_seen_unix;
    public String query_time;
    public String message;
    public String error;

    /**
     * Allows you to lookup and determine info of a specified IPv4 or IPv6 Address and check
     * if it belongs to a VPN Network, Proxy Server, Tor Network, and or Mobile Network
     * <p>
     * <p>
     * This class facilitates and simplifies using the web API, and allows you to
     * easily implement the functionality in your applications.
     * <p>
     * API Homepage: https://ProxyCheck.io
     *
     * @author DefianceCoding
     */

    private String api_key;
    private String api_url = "http://proxycheck.io/v2/";
    private int api_timeout = 5000;

    private int useVpn = 0;
    private int useAsn = 0;
    private int useNode = 0;
    private int useTime = 0;
    private int useInf = 0;
    private int usePort = 0;
    private int useSeen = 0;
    private int useDays = 7;
    private String tag;

    public PCDetection(String key) {
        this.api_key = key;
    }

    public PCDetection(String key, int timeout) {
        this.api_key = key;
        this.api_timeout = timeout;
    }

    /**
     * Here is where you can set your API Key from your user dashboard
     *
     * @param key APIKey from RESTful API
     */

    public void set_api_key(String key) {
        this.api_key = key;
    }

    /**
     * When the vpn flag is supplied we will perform a VPN check on the IP Address and present the result to you.
     *
     * @param var Boolean value to set if you will check for VPN's
     */

    public void setUseVpn(boolean var) {
        if (var) {
            useVpn = 1;
        } else {
            useVpn = 0;
        }
    }

    /**
     * When the asn flag is supplied we will perform an ASN check on the IP Address and present you with the Provider name, ASN, Country and country isocode for the IP Address.
     *
     * @param var Boolean value to set if you will check for the ASN
     */

    public void setUseAsn(boolean var) {
        if (var) {
            useAsn = 1;
        } else {
            useAsn = 0;
        }
    }

    /**
     * When the node flag is supplied we will display which node within our cluster answered your API call. This is only really needed for diagnosing problems with our support staff.
     *
     * @param var Boolean value to set if you will check nodes
     */

    public void setUseNode(boolean var) {
        if (var) {
            useNode = 1;
        } else {
            useNode = 0;
        }
    }

    /**
     * When the time flag is supplied we will display how long this query took to be answered by our API excluding network overhead.
     *
     * @param var Boolean value to set if you will check time it took to get results (note this doesn't count network overhead)
     */

    public void setUseTime(boolean var) {
        if (var) {
            useTime = 1;
        } else {
            useTime = 0;
        }
    }

    /**
     * When the inf flag is set to 0 (to disable it) we will not run this query through our real-time inference engine. In the absense of this flag or if it's set to 1 we will run the query through our real-time inference engine.
     *
     * @param var Boolean value to set if you will check IPs in real time
     */

    public void setUseInf(boolean var) {
        if (var) {
            useInf = 1;
        } else {
            useInf = 0;
        }
    }

    /**
     * When the port flag is supplied we will display to you the port number we saw this IP Address operating a proxy server on.
     *
     * @param var Boolean value to set if you will check Ports
     */

    public void setUsePort(boolean var) {
        if (var) {
            usePort = 1;
        } else {
            usePort = 0;
        }
    }

    /**
     * When the seen flag is supplied we will display to you the most recent time we saw this IP Address operating as a proxy server.
     *
     * @param var Boolean value to set if you will check last seen used as a proxy
     */

    public void setUseSeen(boolean var) {
        if (var) {
            useSeen = 1;
        } else {
            useSeen = 0;
        }
    }

    /**
     * When the days flag is supplied we will restrict our proxy results to between now and the amount of days you specify. For example if you supplied &days=2 we would only check our database for Proxies that we saw within the past 48 hours. By default without this flag supplied we search within the past 7 days.
     *
     * @param var Number (int) value of how many days to go back in the database
     *            this flag is used for if checking for newer or even older based proxies
     */

    public void setUseDays(int var) {
        useDays = var;
    }

    /**
     * When the tag flag is supplied we will tag your query with the message you supply. You can supply your tag using the POST method and we recommend you do so.
     *
     * @param var String that allows you to add a tag to users dashboards stating where the lookup came from
     */

    public void setTag(String var) {
        tag = var;
    }

    /**
     * Units are measured in milliseconds and is the max time the API will try to poll info.
     *
     * @param timeout Time in Milliseconds the query will take before timing out
     */

    public void set_api_timeout(int timeout) {
        this.api_timeout = timeout;
    }

    /**
     * Determines weather or not to use SSL when querying from the API Host
     */

    public void useSSL() {
        this.api_url = this.api_url.replace("http://", "https://");
    }

    /**
     * This method is used in filling the variables. This must be called at least once!
     *
     * @param Ip IP to gather information on
     * @throws IOException
     * @throws ParseException
     */

    public void parseResults(String Ip) throws IOException, ParseException {
        String query_url = this.get_query_url(Ip);
        String query_result = this.query(query_url, this.api_timeout);

        JSONParser parser = new JSONParser();
        JSONObject main = (JSONObject) parser.parse(query_result);
        JSONObject sub = (JSONObject) main.get(Ip);

        status = (String) main.get("status");
        node = (String) main.get("node");
        ip = Ip;
        asn = (String) sub.get("asn");
        provider = (String) sub.get("provider");
        country = (String) sub.get("country");
        isocode = (String) sub.get("isocode");
        proxy = (String) sub.get("proxy");
        type = (String) sub.get("type");
        port = (String) sub.get("port");
        last_seen_human = (String) sub.get("last seen human");
        last_seen_unix = (String) sub.get("last seen unix");
        query_time = (String) main.get("query time");
        message = (String) main.get("message");
        error = (String) sub.get("error");

    }

    /**
     * Gets full page response in String readable format normally will be used in debugging
     *
     * @param ip IP to query
     * @return String result
     * @throws IOException
     */

    public String getResponseAsString(String ip) throws IOException {
        String query_url = this.get_query_url(ip);
        String query_result = this.query(query_url, this.api_timeout);

        return query_result;
    }

    /**
     * Generates the URL used for Query based on settings above
     *fully you have fun
     * @param ip IP To query
     * @return String URL
     */

    public String get_query_url(String ip) {
        String url = this.api_url + ip + "?key=" + this.api_key + "&vpn=" + useVpn + "&asn=" + useAsn + "&node=" + useNode + "&time=" + useTime
                + "&inf=" + useInf + "&port=" + usePort + "&seen=" + useSeen + "&days=" + useDays + "&tag=" + tag;
        return url;
    }

    /**
     * Function that will pull data from the API URL.
     *
     * @param url       full based url from arguments given
     * @param timeout   timeout in milliseconds before api will give up
     * @return JSON Output of query
     * @throws MalformedURLException
     * @throws IOException
     */

    public String query(String url, int timeout)
            throws MalformedURLException, IOException {
        StringBuilder response = new StringBuilder();
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("User-Agent", "Defiance-AntiBot.v1.2.0");
        connection.setRequestProperty("tag", "Defiance-AntiBot.v1.2.0");
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()))) {
            while ((url = in.readLine()) != null) {
                response.append(url);
            }
        }
        return response.toString();
    }
}
