package com.dexels.navajo.client;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Use the NavajoClientFactory for instantiating NavajoClients. The factory
 * keeps track of all instances
 */

public class NavajoClientFactory {
	private static ClientInterface myClient = null;
	private static ClientInterface defaultClient;

	private static final Logger logger = LoggerFactory.getLogger(NavajoClientFactory.class);

	private NavajoClientFactory() {
		// no instance
	}

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
	 * Get the instantiated client (default, if not instantiated yet)
	 * 
	 * @return ClientInterface
	 */
	public static synchronized ClientInterface getClient() {
		if (myClient == null) {
			if (defaultClient == null) {
				logger.warn("No default client set. Missing impl? Cannot create client!", new Exception());
				return null;
			}
			ClientInterface client = null;
			try {
				client = defaultClient.getClass().getDeclaredConstructor().newInstance();
			
			} catch (InstantiationException |IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
				logger.error("Error: ", ex);
			}
			
			if (client == null) {
				return null;
			}
			myClient = client;
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
