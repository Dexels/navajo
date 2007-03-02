package com.dexels.navajo.client.impl;

import java.net.*;
import java.io.*;
import com.dexels.navajo.client.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BaseNavajoHandler
    extends URLStreamHandler {

  private NavajoUrl myNavajoUrl = null;
private boolean secure = false;

  public BaseNavajoHandler() {
  }

  protected void setSecure(boolean b) {
    System.err.println("Setting secure");
    secure = b;
  }

  protected URLConnection openConnection(URL u) throws java.io.IOException {
    parseURL(u,u.toExternalForm(),-1,-1);
    ClientInterface ci = NavajoClientFactory.getClientByName(myNavajoUrl.clientName);
     ci = NavajoClientFactory.getClientByName(myNavajoUrl.clientName);
    System.err.println("Creating url: ");
    String protocol;
    if (secure) {
      protocol="https://";
    } else {
      protocol="http://";
    }
    String urlstring = protocol+myNavajoUrl.server+":"+myNavajoUrl.port+"/"+myNavajoUrl.service;

    System.err.println("Created url: "+urlstring);
    URL uu = new URL(urlstring);
    ci.setUsername(myNavajoUrl.username);
    ci.setPassword(myNavajoUrl.password);
    ci.setServerUrl(myNavajoUrl.method);

    return new NavajoHttpUrlConnection(this,uu,ci,myNavajoUrl.method);

//    return null;
  }

  public void connect() {

  }

  protected int getDefaultPort() {
    return 80;
  }

  protected synchronized InetAddress getHostAddress(URL u) {
    BaseNavajoHandler h = new BaseNavajoHandler();
    h.parseURL(u, u.toExternalForm(), -1, -1);
    try {
      return InetAddress.getByName(h.getNavajoUrl().server);
    }
    catch (UnknownHostException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public NavajoUrl getNavajoUrl() {
    return myNavajoUrl;
  }

  protected boolean hostsEqual(URL u1, URL u2) {
    BaseNavajoHandler h = new BaseNavajoHandler();
    h.parseURL(u1, u1.toExternalForm(), -1, -1);
    BaseNavajoHandler h2 = new BaseNavajoHandler();
    h2.parseURL(u2, u2.toExternalForm(), -1, -1);
    return h.getHostAddress(u1).equals(h2.getHostAddress(u2));
  }

  //   navajo:/frank:aap@192.0.0.1/InitUpdateMember

  protected void parseURL(URL u, String spectotal, int start, int limit) {
    System.err.println("SPEK: " + spectotal);
    if (!spectotal.startsWith("navajo:/") && !spectotal.startsWith("navajos:/")) {
      throw new IllegalArgumentException("Bad spec: " + spectotal +
                                         " does not start with navajo:/ or navajos:/");
    }

    boolean secure = false;

    String[] stuff = spectotal.split(":/");
    if ("navajo".equals(stuff[0])) {
      System.err.println("NOT secure");
      secure = false;
    }
    if ("navajos".equals(stuff[0])) {
      System.err.println("Secure");
      secure = true;
    }

    String spec = spectotal.substring(stuff[0].length()+2);
    System.err.println("REST: "+spec);

//    String spec = spectotal.substring(8);

    if (spec.startsWith("direct")) {
      myNavajoUrl = createDirectUrl(spec);
    } else {
      createHttpUrl(spec);
    }

  }

  private NavajoUrl createDirectUrl(String spec) {
    String[] directParts = spec.split(":");
    if (directParts.length!=2) {
      throw new IllegalArgumentException("Bad spec: " + spec +
                                         " does not contain: ':'");
    }
    if (directParts[0].equals("direct")) {
      throw new IllegalArgumentException("Strange direct spec: " + spec);
    }

    NavajoUrl u = new NavajoUrl("localhost",-1,null,directParts[1],null,null,"direct");
    return u;
  }

  private NavajoUrl createHttpUrl(String spec) {
    String[] parts = spec.split("@");
     if (parts.length != 2) {
       throw new IllegalArgumentException("Bad spec: " + spec + " wrong # of @");
     }
     String id = parts[0];
     String rest = parts[1];
     String[] idparts = id.split(":");
     if (idparts.length != 2) {
       throw new IllegalArgumentException("Bad spec: " + id +
                                          " wrong # of : in identification");
     }
     String username = idparts[0];
     String password = idparts[1];

     int hostindex = rest.indexOf("/");
     if (hostindex < 0) {
       throw new IllegalArgumentException("Bad spec: " + rest + " no");
     }
     String host = rest.substring(0, hostindex);
     String servicepart = rest.substring(hostindex, rest.length());
//    String service = rest
     System.err.println("Host: " + host);
     System.err.println("Service: " + servicepart);
     String[] hostparts = host.split(":");
     String hostname = hostparts[0];
     if (hostparts.length > 2) {
       throw new IllegalArgumentException("Bad spec: " + id +
           " wrong # of : in identification: no port");
     }

     String[] serviceparts = servicepart.split(":");
     if (serviceparts.length != 2) {
       throw new IllegalArgumentException("Bad spec: " + id +
                                          " wrong # of : in services: " +
                                          servicepart);
     }
     String service = serviceparts[0];
     String method = serviceparts[1];

     int port = 80;
     if (hostparts.length == 2) {
       String portString = hostparts[1];
       port = Integer.parseInt(portString);
     }

     System.err.println("Navajo: Server: " + hostname + " port: " + port +
                        " service: " + service + " method: " + method +
                        " username: " + username + " password: " + password);

     NavajoUrl url = new NavajoUrl(hostname, port, service, method, username,
                                 password,"http");
   myNavajoUrl = url;
    return url;
  }

  protected boolean sameFile(URL u1, URL u2) {
    return u1.toExternalForm().equals(u2.toExternalForm());
  }

  protected String toExternalForm(URL u) {
    return "navajo:/" + myNavajoUrl.username + ":" + myNavajoUrl.password + "@" +
        myNavajoUrl.server + "/" + myNavajoUrl.service + ":" +
        myNavajoUrl.method;
  }

  public static void main(String[] args) {
    try {
//      System.setProperty("java.protocol.handler.pkgs","com.dexels.navajo");
      NavajoClientFactory.createDefaultClient();
      System.err.println("PROP: " +
                         System.getProperty("java.protocol.handler.pkgs"));

      URL u = new URL(
          "navajos:/frank:aap@192.0.0.1:3000/knvb/navajo-tester:InitUpdateMember");

//      URL u = new URL(
//         "navajo:/direct:InitUpdateMember");

      try {
        u.openConnection();
      }
      catch (IOException ex1) {
        ex1.printStackTrace();
      }
    }
    catch (MalformedURLException ex) {
      ex.printStackTrace();
    }

  }

  static class NavajoUrl {

    public String server = null;
    public int port;
    public String service = null;
    public String method = null;
    public String username = null;
    public String password = null;
    public String clientName = null;

    public NavajoUrl(String server, int port, String service, String method,
                     String username, String password, String clientName) {
      this.server = server;
      this.port = port;
      this.service = service;
      this.method = method;
      this.username = username;
      this.password = password;
      this.clientName = clientName;
    }

  }

}
