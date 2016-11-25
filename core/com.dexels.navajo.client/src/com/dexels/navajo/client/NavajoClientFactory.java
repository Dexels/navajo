package com.dexels.navajo.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use the NavajoClientFactory for instantiating NavajoClients. The factory
 * keeps track of all instances
 */

public class NavajoClientFactory {
    private static ClientInterface defaultClient = null;
    private static ClientInterface myClient = null;
    private final static Logger logger = LoggerFactory.getLogger(NavajoClientFactory.class);

  
    

    public static void setDefaultClient(ClientInterface ci) {
        defaultClient = ci;
        defaultClient.setUsername("");
        defaultClient.setPassword("");
        defaultClient.setServerUrl("");
    }
    
    static void clearDefaultClient(ClientInterface ci) {
        defaultClient = null;
    }
    
    

    public static ClientInterface createDefaultClientForServerlist(String[] servers) {
        if (servers != null && servers.length > 0) {
            NavajoClientFactory.resetClient();
            NavajoClientFactory.createDefaultClient();
        }
        ClientInterface ci = getClient();
        ci.setServers(servers);
        return ci;
    }

    /**
     * Clear current client
     */
    public static void resetClient() {
        myClient = null;
    }

    public static ClientInterface createClient() {
        if (defaultClient == null) {
            logger.error("No default client set. Missing impl?");
            return null;
        }
        
        try {
            return defaultClient.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Unable to create client!", e);
        }
        return null;
    }

    /**
     * Create a default client (queue)
     * 
     * @return ClientInterface
     */
    public synchronized static ClientInterface createDefaultClient() {
        return createClient();
    }

    /**
     * Get the instantiated client (default, if not instatiated yet)
     * 
     * @return ClientInterface
     */
    public synchronized static ClientInterface getClient() {
        if (myClient == null) {
            myClient = createDefaultClient();
        }

        return myClient;
    }



}
