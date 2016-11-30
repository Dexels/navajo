package com.dexels.navajo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Use the NavajoClientFactory for instantiating NavajoClients. The factory
 * keeps track of all instances
 */

public class NavajoClientFactory {
	private static ClientInterface myClient = null;
	private static ClientInterface defaultClient;

	private final static Logger logger = LoggerFactory.getLogger(NavajoClientFactory.class);


	/**
	 * Clear current client
	 */
	public static void resetClient() {
		myClient = null;
	}

	public static ClientInterface createClient() {
		return getClient();
	}

	/**
	 * Get the instantiated client (default, if not instatiated yet)
	 * 
	 * @return ClientInterface
	 */
	public synchronized static ClientInterface getClient() {
		if (myClient == null) {
			if (defaultClient == null) {
				logger.warn("No default client set. Missing impl? Cannot create client!", new Exception());
				return null;
			}
			ClientInterface client = null;
			try {
				client = (ClientInterface) defaultClient.getClass().newInstance();
			
			} catch (InstantiationException |IllegalAccessException ex) {
				logger.error("Error: ", ex);
			}
			
			if (client == null) {
				return null;
			}
			if (myClient == null) {
				myClient = client;
			}
			client.setUsername("");
			client.setPassword("");
			client.setServerUrl("");
		}

		return myClient;
	}

	/**
	 * Set tht current client
	 * 
	 * @param ci
	 *            ClientInterface
	 */
	public static void setCurrentClient(ClientInterface ci) {
		myClient = ci;
	}

	public static void setDefaultClient(ClientInterface client) {
		defaultClient = client;
		
	}

}
