package com.dexels.navajo.server;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class ClientInfo {

  public String ip;
  public String host;

  public ClientInfo(String ip, String host) {
    this.ip = ip;
    this.host = host;
  }

  public String getIP() {
    return this.ip;
  }

  public String getHost() {
    return this.host;
  }

}