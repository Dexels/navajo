package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.MappableTreeNode;
import com.dexels.navajo.document.*;

public final class AccessMap implements Mappable {

  public String userName;
  public String webService;
  public java.util.Date created;
  public Access myAccess;
  public MappableTreeNode currentMap;
  public int totaltime;
  public String ipAddress;
  public String host;
  public String accessId;
  public String requestNavajo = null;
  public String responseNavajo = null;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    this.myAccess = access;
  }

  public void store() throws MappableException, UserException {
  }
  public void kill() {
  }
  public String getAccessId() {
    return myAccess.accessID;
  }
  public final String getIpAddress() {
    return myAccess.ipAddress;
  }
  public final String getHost() {
    return myAccess.hostName;
  }
  public final String getUserName() {
    return myAccess.rpcUser;
  }
  public final String getWebService() {
    return myAccess.rpcName;
  }
  public final java.util.Date getCreated() {
    return myAccess.created;
  }
  public final int getTotaltime() {
    return (int) ( System.currentTimeMillis() - myAccess.created.getTime() );
  }
  public MappableTreeNode getCurrentMap() {
    return myAccess.getCompiledScript().currentMap;
  }
  public String getResponseNavajo() {
    if (responseNavajo == null) {
      Navajo in = myAccess.getCompiledScript().outDoc;
      java.io.StringWriter sw = new java.io.StringWriter();
      try {
        in.write(sw);
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
      responseNavajo = sw.toString();
    }
    return responseNavajo;
  }

  public String getRequestNavajo() {
    if (requestNavajo == null) {
      Navajo in = myAccess.getCompiledScript().inDoc;
      java.io.StringWriter sw = new java.io.StringWriter();
      try {
        in.write(sw);
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
      requestNavajo = sw.toString();
    }
    return requestNavajo;
  }
}