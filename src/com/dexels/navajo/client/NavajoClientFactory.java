package com.dexels.navajo.client;

import java.net.URL;

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


  public static ClientInterface createClient(String className, URL config) {
//    ClientInterface client = null;
    try {
      Class clientClass = Class.forName(className);
      myClient = (ClientInterface) clientClass.newInstance();
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
      myClient.init(config);
    }
    catch (ClientException ex1) {
      ex1.printStackTrace();
    }
    return myClient;
  }

  public static ClientInterface createDefaultClient() {
    /** @todo Beware when refactoring */
    return createClient("com.dexels.navajo.client.NavajoClient",null);
  }

  public static ClientInterface getClient() {
    if (myClient==null) {
      return createDefaultClient();
    }

    return myClient;
  }
}