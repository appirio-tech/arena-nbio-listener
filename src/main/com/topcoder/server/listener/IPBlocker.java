package com.topcoder.server.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.topcoder.shared.util.concurrent.ConcurrentHashSet;
import com.topcoder.shared.util.logging.Logger;

public final class IPBlocker {

    private static final Logger cat = Logger.getLogger(IPBlocker.class);

    private final Map banExpiration = new ConcurrentHashMap();
    private final Collection ipsSet = new ConcurrentHashSet();
    private final boolean isAllowedSet;

    public IPBlocker(Collection ips, boolean isAllowedSet) {
        this.isAllowedSet = isAllowedSet;
        if (ips != null) {
            ipsSet.addAll(ips);
        }
        if (isAllowedSet) {
            try {
                String localIP = InetAddress.getLocalHost().getHostAddress();
                ipsSet.add(localIP);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        info("isAllowedSet=" + isAllowedSet + ", ips=" + ipsSet);
    }

    public void banIP(String ipAddress) {
        if (isAllowedSet) {
            throw new RuntimeException("you cannot ban an IP address if isAllowedSet==true");
        }
        ipsSet.add(ipAddress);
    }
    
    //rfairfax 6-11
    public void banIPwithExpiry(String ipAddress, long expiresAt)
    {
        if (isAllowedSet) {
            throw new RuntimeException("you cannot ban an IP address if isAllowedSet==true");
        }
        ipsSet.add(ipAddress);
        banExpiration.put(ipAddress, new Long(expiresAt));
    }

    public boolean isBlocked(String ipAddress) {
        if (isAllowedSet) {
            return !ipsSet.contains(ipAddress);
        } else {
            //check for expiration
            if(banExpiration.containsKey(ipAddress))
            {
                long time = ((Long)banExpiration.get(ipAddress)).longValue();
                
                if(time <= System.currentTimeMillis())
                {
                    //they're ok again
                    banExpiration.remove(ipAddress);
                    ipsSet.remove(ipAddress);
                }
            }
            return ipsSet.contains(ipAddress);
        }
    }

    private static void info(String message) {
        cat.info(message);
    }

}
