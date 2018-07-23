# PCDetectionAPI

PLEASE READ THE CONTACT INFORMATION SECTION

# REQUIREMENTS
json-simple-1.1.1 (https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple/1.1.1) 

# Endorsement
This Api was created for ProxyCheck and is open sourced to be used by any developers who wish to use it free of charge.
If someone charged you to use this API, Feel free to contact DefianceCoding in one of the methods in the #Contact Section below.

# USAGE/INTEGRATION
FOR EXAMPLE CLASSES, SEE THE EXAMPLE CLASS PACKAGE
Firstly to use anything within the API, make sure you have the requirements section added, then in your class of choice add this line of code to instance the api.
```java
        Pcdetection pcDetection = new Pcdetection(null);
```
Then youll want to make your options you want in your lookup.
```java
        pcDetection.useSSL();
        pcDetection.set_api_key("APIKeyHere");
        pcDetection.set_api_timeout(5000);
        pcDetection.setUseVpn(true);
        /*
        NOTE -- putting false is redundant as all options default to false
        just leave any constructors you will not set to true out of the code 
        They are in my example here to just give ideas whats there.
        */
        pcDetection.setUseAsn(false);
        pcDetection.setUseNode(false);
        pcDetection.setUseTime(true);
        pcDetection.setUseInf(true);
        pcDetection.setUsePort(false);
        pcDetection.setUseSeen(false);
        //this option is measured in an INT value for Days (no need to add "days")
        pcDetection.setUseDays(7);
        pcDetection.setTag("DefianceCoding's Proxycheck API");
```

Now with connection string fully built here, we can now work with it

```java
        try {
            pcDetection.parseResults(IP);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
```

And then use data from the api class itself.

```java 
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
```

If you are having issues with anything with this API, feel free to check the Contact section and I am more than willing to help out with any information or reguards you may have.

# Contact Information 
Email: DefianceCoding@gmail.com
Discord Support Server: https://discord.gg/9YvgdEC
Discord Name Direct: DefianceCoding#4765
Paypal Donation Link: paypal.me/DeFianceCoding
NOTE: Donations arent required and I don't mind giving out this API for free, this link is for those who truly feel they want to donate, It is in no way required and will continue updating things reguardless of donations!
