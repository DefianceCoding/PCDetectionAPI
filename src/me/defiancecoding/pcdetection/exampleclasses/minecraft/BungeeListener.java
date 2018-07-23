package me.defiancecoding.pcdetection.exampleclasses.minecraft;

import me.defiancecoding.pcdetection.Pcdetection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class BungeeListener extends Plugin implements Listener {

    // We will set EventPriority here to monitor so no other plugins can mess around with the event running
    @EventHandler
    // We also will do playerPRElogin here as to stop people from bypassing the login check via proxy connection change upon logins
    public void checkUsersonLogin(PreLoginEvent e) {

        //Firstly we need to instance the PCDetection API and construct what we will use
        //Here we will set the constructor to null to fill in the data later on
        Pcdetection detection = new Pcdetection(null);
        //This just states to switch the URL to HTTPS:// instead of HTTP://
        detection.useSSL();
        //Time to set the APIKey that will be used for the lookups
        detection.set_api_key("APIKeyHere");
        //And then ask ourselves what all do we want to check, If a constructor isnt added, it is assumed disabled (default is always false)
        //All constructors are the variables from proxychecks API (&vpn=#, asn=# etc)
        detection.setUseVpn(true);

        //Now we want to get the player logging in and grab their IP into string form
        String IP = e.getConnection().getAddress().getHostString();

        //Now we test and try to open the connection to Proxycheck.io
        try {
            detection.parseResults(IP);
        } catch (ParseException | IOException ex) {
            ex.printStackTrace();
        }

        //and now we will notify any admins on with permission node "Notify.Admin" that a player just logged in with a proxy by looping through all players online
        //then ask if they have the correct permission node, this one is bungee, so were gonna get EVERY proxied player on all servers.
        for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
            if (players.hasPermission("Notify.Admin")) {
                if (detection.proxy.equalsIgnoreCase("yes")) {
                    players.sendMessage(new TextComponent("Player: " + e.getConnection().getName() + " Tried to login with a proxy/vpn " + detection.ip));
                }
            }
        }


    }

}
