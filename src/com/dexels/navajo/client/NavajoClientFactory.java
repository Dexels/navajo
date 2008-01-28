package com.dexels.navajo.client;

import java.util.*;

/**
 * Use the NavajoClientFactory for instantiating NavajoClients. The factory keeps track of all instances
 */

public class NavajoClientFactory {
  private static ClientInterface myClient = null;

  private static final Map<String,ClientInterface> clientMap = new HashMap<String,ClientInterface>();

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
      ex.printStackTrace();
    }
    catch (IllegalAccessException ex) {
      ex.printStackTrace();
    }
    catch (InstantiationException ex) {
      ex.printStackTrace();
    }
    if ( client == null ) {
    	return null;
    }
    try {
      client.init(rootPath, serverXmlRelativePath);
    }
    catch (ClientException ex1) {
      ex1.printStackTrace();
    }
    clientMap.put(client.getClientName(),myClient);
    if (myClient==null) {
      myClient = client;
    }
    client.setUsername("");
    client.setPassword("");
    client.setServerUrl("");
    return client;
  }
  
  public static ClientInterface createDefaultClientForServerlist(String[] servers) {
	     if (servers!=null && servers.length>0) {
	 		String serv = servers[0];
	 		if (serv.indexOf('/')==-1) {
	 			System.err.println("Socket implementation found!");
	 			NavajoClientFactory.resetClient();
	 			NavajoClientFactory.createSocketClient();
	 		} else {
	 			NavajoClientFactory.resetClient();
	 			NavajoClientFactory.createDefaultClient();
	 		}
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

  /**
   * Create a default client (queue)
   * @return ClientInterface
   */
  public synchronized static ClientInterface createDefaultClient() {
    /** @todo Beware when refactoring */
//    return createClient("com.dexels.navajo.client.NavajoClient",null);
    return createClient("com.dexels.navajo.client.queueimpl.ClientQueueImpl",null,null);
  }


  /**
   * Create a direct client. A direct client does not require a webserver and can be used in standalone applications
   * @param u URL
   * @return ClientInterface
   */
  public synchronized static ClientInterface createSocketClient() {
    return createClient("com.dexels.navajo.client.NavajoSocketClient",null,null);
  }

  
  /**
   * Create a direct client. A direct client does not require a webserver and can be used in standalone applications
   * @param u URL
   * @return ClientInterface
   */
  public synchronized static ClientInterface createDirectClient(String rootPath, String serverXmlRelativePath) {
    return createClient("com.dexels.navajo.client.impl.DirectClientImpl",rootPath, serverXmlRelativePath);
  }

//  /**
//   * Create a queue client
//   * @param u URL
//   * @return ClientInterface
//   */
//  public synchronized static ClientInterface createQueueClient(URL u) {
//    return createClient("com.dexels.navajo.client.queueimpl.ClientQueueImpl",u);
//  }

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
   * Clear client map
   */
  public static void clearClientMap() {
    clientMap.clear();
  }

  /**
   * Get client by name
   * @param name String
   * @return ClientInterface
   */
  public static ClientInterface getClientByName(String name) {
    ClientInterface ci = clientMap.get(name);
    return ci;
  }

  /**
   * Set tht current client
   * @param ci ClientInterface
   */
  public static void setCurrentClient(ClientInterface ci) {
    myClient = ci;
  }
  

}
