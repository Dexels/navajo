
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;

import java.sql.Connection;
import org.dexels.grus.DbConnectionBroker;

public class Access implements java.io.Serializable {

  public int accessID;
  public int userID;
  public int serviceID;
  public String rpcName;
  public String rpcUser;
  public String userAgent;
  public String ipAddress;
  public String hostName;

  public Access(int accessID, int userID, int serviceID, String rpcUser,
                String rpcName, String userAgent, String ipAddress, String hostName) {

    this.accessID = accessID;
    this.userID = userID;
    this.serviceID = serviceID;
    this.rpcName = rpcName;
    this.rpcUser = rpcUser;
    this.userAgent = userAgent;
    this.hostName = hostName;
    this.ipAddress = ipAddress;

  }
}