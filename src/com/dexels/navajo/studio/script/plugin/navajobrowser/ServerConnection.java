package com.dexels.navajo.studio.script.plugin.navajobrowser;

import com.dexels.navajo.document.*;

import java.io.*;
import java.net.*;
import com.dexels.navajo.client.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ServerConnection {

  private Navajo myNavajo = null;

  private final String connectionName;
  private final String username;
  private final String password;
  private final String serverurl;
  private final Object keystore;
  private final String storepass;
  private final String config;

  private ClientInterface myClient = null;

  public ServerConnection(ClientInterface client, String connectionName, String username, String password, String serverurl, Object keystore, String storepass) {
      myClient = client;
     this.connectionName = connectionName;
    this.username = username;
    this.password = password;
    this.keystore = keystore;
    this.storepass = storepass;
    this.serverurl = serverurl;
    this.config = null;

    myClient.setUsername(username);
    myClient.setPassword(password);
    myClient.setServerUrl(serverurl);
    
    if (storepass != null && keystore != null && !"".equals(keystore)) {
         try {
            if (keystore instanceof URL) {
              NavajoClientFactory.getClient().setSecure(((URL)keystore).openStream(), storepass, true);

            } else {
              String keys = ""+keystore;
              NavajoClientFactory.getClient().setSecure(keys, storepass, true);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }
      }
     }




  public String getName() {
    return connectionName;
  }

  public void setCurrent() {
    System.err.println("SETTING TO CONNECTION: "+getName()+" client class: "+myClient.getClass());
//    ClientInterface sc = myClient;
    try {
      myClient.setServerUrl(serverurl);
      myClient.setUsername(username);
      myClient.setPassword(password);
    }
    catch (Throwable ex) {
      System.err.println("The client complained about setting something. Ignoring");
    }
    NavajoClientFactory.setCurrentClient(myClient);
    /** @todo Check for secure settings */
  }

  public Navajo doSimpleSend(Navajo input, String serviceName) throws ClientException {
    String u = myClient.getUsername();
    String p = myClient.getPassword();
    String s = myClient.getServerUrl();
    myClient.setServerUrl(serverurl);
myClient.setUsername(username);
myClient.setPassword(password);

    try {
      Navajo n = myClient.doSimpleSend(input,serviceName);
      return n;
  }
    finally {
      myClient.setServerUrl(s);
      myClient.setUsername(u);
      myClient.setPassword(p);
    }


  }

  public ServerConnection(String connectionName, String config) {
     this.connectionName = connectionName;
    this.config = config;
    this.username = null;
    this.password = null;
    this.serverurl = null;
    this.keystore = null;
    this.storepass= null;
  }

  private Navajo getScriptList() {
    if (myNavajo==null) {
      return refreshScriptList();
    }
    return myNavajo;
  }

  public Message getInitScripts() {
    return getScriptList().getMessage("InitScripts");
  }

  public Message getProcessScripts() {
    return getScriptList().getMessage("ProcessScripts");
  }

  public Navajo refreshScriptList() {
    try {

        Navajo n = myClient.doSimpleSend(NavajoFactory.getInstance().createNavajo(),
                                       "InitGetScriptList");
        n.write(System.err);
      n.getProperty("QueryScriptList/Username").setValue(username);
      n.getProperty("QueryScriptList/Password").setValue(password);
      n.getProperty("QueryScriptList/UseCVS").setValue("false");
      n.getProperty("QueryScriptList/SelectType").setSelected("Init");
      n.getProperty("QueryScriptList/SelectType").setSelected("Process");
      myNavajo = myClient.doSimpleSend(n, "ProcessGetScriptList"); // 10 sec.
      return myNavajo;
   }
   catch (Exception ex) {
     ex.printStackTrace();
     return null;
   }

  }

  public void setClient(ClientInterface ci) {
    myClient = ci;
  }

}
