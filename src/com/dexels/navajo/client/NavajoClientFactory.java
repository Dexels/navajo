package com.dexels.navajo.client;

import java.net.URL;
import java.util.*;

/**
 * Use the NavajoClientFactory for instantiating NavajoClients. The factory keeps track of all instances
 */

public class NavajoClientFactory {
  private static ClientInterface myClient = null;

  private static final Map clientMap = new HashMap();

  /**
   * Create a Client with a given class and configuration
   * @param className String
   * @param config URL
   * @return ClientInterface
   */
  public static ClientInterface createClient(String className, URL config) {
    ClientInterface client = null;
    try {
      Class clientClass = Class.forName(className);
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
    try {
      client.init(config);
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
  public static ClientInterface createDefaultClient() {
    /** @todo Beware when refactoring */
//    return createClient("com.dexels.navajo.client.NavajoClient",null);
    return createClient("com.dexels.navajo.client.queueimpl.ClientQueueImpl",null);
  }

  /**
   * Create a direct client. A direct client does not require a webserver and can be used in standalone applications
   * @param u URL
   * @return ClientInterface
   */
  public static ClientInterface createDirectClient(URL u) {
    return createClient("com.dexels.navajo.client.impl.DirectClientImpl",u);
  }

  /**
   * Create a queue client
   * @param u URL
   * @return ClientInterface
   */
  public static ClientInterface createQueueClient(URL u) {
    return createClient("com.dexels.navajo.client.queueimpl.ClientQueueImpl",u);
  }

  /**
   * Get the instantiated client (default, if not instatiated yet)
   * @return ClientInterface
   */
  public static ClientInterface getClient() {
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
    ClientInterface ci = (ClientInterface)clientMap.get(name);
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
