package com.dexels.navajo.client;

import java.net.URL;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NavajoClientFactory {
  private static ClientInterface myClient = null;

  private static final Map clientMap = new HashMap();

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
    return client;
  }

  public static void resetClient() {
    myClient=null;
  }

  public static ClientInterface createDefaultClient() {
    /** @todo Beware when refactoring */
    return createClient("com.dexels.navajo.client.NavajoClient",null);
  }

  public static ClientInterface createDirectClient(URL u) {
    return createClient("com.dexels.navajo.client.impl.DirectClientImpl",u);
  }

  public static ClientInterface getClient() {
    if (myClient==null) {
      return createDefaultClient();
    }

    return myClient;
  }

  public static void clearClientMap() {
    clientMap.clear();
  }

  public static ClientInterface getClientByName(String name) {
    ClientInterface ci = (ClientInterface)clientMap.get(name);
    return ci;
  }


}
