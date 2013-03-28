package com.dexels.navajo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use the NavajoClientFactory for instantiating NavajoClients. The factory keeps track of all instances
 */

public class NavajoClientFactory {
  private static ClientInterface myClient = null;

  
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoClientFactory.class);

  /**
   * Create a Client with a given class and configuration
   * @param className String
   * @param config URL
   * @return ClientInterface
   */
  public synchronized static ClientInterface createClient(String className, String rootPath, String serverXmlRelativePath) {
    ClientInterface client = null;
    try {
      Class<?> clientClass =  Class.forName(className);
      client = (ClientInterface) clientClass.newInstance();
    }
    catch (ClassNotFoundException ex) {
      logger.error("Error: ", ex);
    }
    catch (IllegalAccessException ex) {
    	logger.error("Error: ", ex);
    }
    catch (InstantiationException ex) {
    	logger.error("Error: ", ex);
    }
    if ( client == null ) {
    	return null;
    }
    if (myClient==null) {
      myClient = client;
    }
    client.setUsername("");
    client.setPassword("");
    client.setServerUrl("");
    return client;
  }
  
	public static ClientInterface createDefaultClientForServerlist(
			String[] servers) {
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
    myClient=null;
  }

  public static ClientInterface createClient() {
	  return new NavajoClient();
  }
  
  /**
   * Create a default client (queue)
   * @return ClientInterface
   */
  public synchronized static ClientInterface createDefaultClient() {
    /** @todo Beware when refactoring */
    return createClient("com.dexels.navajo.client.NavajoClient",null,null);
//    return createClient("com.dexels.navajo.client.queueimpl.ClientQueueImpl",null,null);
  }

  /**
   * Get the instantiated client (default, if not instatiated yet)
   * @return ClientInterface
   */
  public synchronized static ClientInterface getClient() {
    if (myClient==null) {
      return createDefaultClient();
    }

    return myClient;
  }

  /**
   * Set tht current client
   * @param ci ClientInterface
   */
  public static void setCurrentClient(ClientInterface ci) {
    myClient = ci;
  }
  

}
