package me.defiancecoding.pcdetection.exampleclasses;

import me.defiancecoding.pcdetection.Pcdetection;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class GeneralJavaListener {

    public static void main(String[] args) throws IOException {

        //lets set a Test IP Here
        String IP = "8.8.8.8";

        //and then well start setting up our connection by referencing the ProxyCheck Java API
        Pcdetection pcDetection = new Pcdetection(null);
        pcDetection.useSSL();
        pcDetection.set_api_key("APIKeyHere");
        pcDetection.set_api_timeout(5000);
        pcDetection.setUseVpn(true);

        //now lets open the connection
        try {
            pcDetection.parseResults(IP);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }

        //Now the connection was established, lets grab things we need below... these are just examples and you can set this up how you want
        if (pcDetection.status.equalsIgnoreCase("ok")) {
            System.out.println("Status: " + pcDetection.status);
            System.out.println("IP is proxy? " + pcDetection.proxy);
            System.out.println("Provider: " + pcDetection.provider + " Type:" + pcDetection.type);
        }
        else if (pcDetection.status.equalsIgnoreCase("warning")){
            System.out.println("Status: " + pcDetection.status);
            System.out.println("WARNING!!! " + pcDetection.message);
            System.out.println("IP is proxy? " + pcDetection.proxy);
            System.out.println("Provider: " + pcDetection.provider + " Type:" + pcDetection.type);
        }
        else if (pcDetection.status.equalsIgnoreCase("denied") || pcDetection.status.equalsIgnoreCase("error")){
            System.out.println("Status: " + pcDetection.status);
            System.out.println("ERROR!!! " + pcDetection.message);
            System.out.println("IP is proxy? " + pcDetection.proxy);
            System.out.println("Provider: " + pcDetection.provider + " Type:" + pcDetection.type);
        }
    }


}
