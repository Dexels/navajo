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
import com.dexels.navajo.mapping.CompiledScript;
import java.util.Stack;
import java.util.Iterator;
import java.util.HashSet;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.mapping.MappingException;

public final class AccessMap implements Mappable {

  public String userName;
  public String webService;
  public java.util.Date created;
  public Access myAccess;
  public MappableTreeNode currentMap;
  public String accessId;
  public String requestNavajo = null;
  public String responseNavajo = null;
  public boolean killed = false;

  private boolean showDetails = false;
  private Access thisAccess = null;
  private boolean isAsync = false;
  /**
   * TODO
   *
   * Show all different available stacks in CompiledScript.
   *
   */
  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    this.myAccess = access;
    thisAccess = access;
  }

  public void setKilled(boolean b) {
    if (myAccess.getCompiledScript().currentMap != null) {
      Mappable myMap = (Mappable) myAccess.getCompiledScript().currentMap.myObject;
      if (myMap != null && myMap instanceof com.dexels.navajo.adapter.SQLMap) {
        ((SQLMap) myMap).setKillConnection();
      }
    }
    myAccess.getCompiledScript().setKill(b);
  }

  private Message getMessage(Message parent, String name) throws NavajoException {
    Message m = null;
    m = NavajoFactory.getInstance().createMessage(thisAccess.getOutputDoc(), name);
    if (parent != null) {
      parent.addMessage(m);
    } else {
      thisAccess.getOutputDoc().addMessage(m);
    }
    return m;
  }

  private void addProperty(Message m, String name, Object value, String type, int length) throws NavajoException, MappingException {
    MappingUtils.setProperty(false, m, name, value, type, null, Property.DIR_OUT, "", length,
                             thisAccess.getOutputDoc(), thisAccess.getCompiledScript().inDoc, false);
  }

  private void showMapDetails(Message parent, MappableTreeNode m) throws NavajoException, MappingException, UserException {

    Mappable myMap = (Mappable) m.myObject;

    addProperty(parent, "Map", m.getMapName(), Property.STRING_PROPERTY, 50);
    addProperty(parent, "Totaltime", new Integer(m.getTotaltime()), Property.STRING_PROPERTY, 50);

    if (myMap instanceof com.dexels.navajo.adapter.SQLMap || myMap instanceof com.dexels.navajo.adapter.SPMap) {
      SQLMap mySQL = (SQLMap) myMap;
      Message parameters = getMessage(parent, "MapParameters");
      addProperty(parameters, "Datasource", mySQL.getDatasource(), Property.STRING_PROPERTY, 50);
      addProperty(parameters, "DatabaseProductName", mySQL.getDatabaseProductName(), Property.STRING_PROPERTY, 50);
      addProperty(parameters, "DatabaseVersion", mySQL.getDatabaseVersion(), Property.STRING_PROPERTY, 50);
      addProperty(parameters, "Query", mySQL.getQuery(), Property.STRING_PROPERTY, -1);
    }

    // Show parents.
    if (m.getParent() != null) {
      Message parentMap = getMessage(parent, "ParentMap");
      showMapDetails(parentMap, m.getParent());
    }
  }

  public void store() throws MappableException, UserException {

    if (showDetails) {
      try {
        Message user = getMessage(null, "User");
        addProperty(user, "Starttime", getCreated(), Property.DATE_PROPERTY, 10);
        addProperty(user, "Totaltime", new Integer(getTotaltime()), Property.INTEGER_PROPERTY, 10);
        addProperty(user, "ClientIP", getIpAddress(), Property.STRING_PROPERTY, 50);
        addProperty(user, "ClientHostname", getHost(), Property.STRING_PROPERTY, 50);
        addProperty(user, "User", myAccess.rpcUser, Property.STRING_PROPERTY, 50);
        addProperty(user, "Webservice", myAccess.rpcName, Property.STRING_PROPERTY, 50);
        addProperty(user, "AccessId", myAccess.accessID, Property.STRING_PROPERTY, 50);
        Message currentMap = getMessage(user, "CurrentMap");
        MappableTreeNode currentNode = getCurrentMap();
        if (currentNode != null) {
          showMapDetails(currentMap, currentNode);
        }
        Message requestNavajo = getMessage(user, "RequestNavajo");
        addProperty(requestNavajo, "Document", getRequestNavajo(), Property.STRING_PROPERTY, -1);
        Message responseNavajo = getMessage(user, "ResponseNavajo");
        addProperty(responseNavajo, "Document", getResponseNavajo(), Property.STRING_PROPERTY, -1);
        Message outMessagStack = getMessage(user, "OutMessageStack");
        addProperty(outMessagStack, "Stack", getOutMessageStack(), Property.STRING_PROPERTY, -1);
        Message mapStack = getMessage(user, "MapObjectStack");
        addProperty(mapStack, "Stack", getMapStack(), Property.STRING_PROPERTY, -1);
      } catch (Exception ne) {
        ne.printStackTrace(System.err);
      }
    }
  }

  public void kill() {
  }

  public void setAccessId(String id) throws UserException {

    System.err.println("in setAccessId("+id+")");
    this.accessId = id;
    if (accessId == null) {
      throw new UserException(-1, "Set accessId first");
    }
    HashSet all = com.dexels.navajo.server.Dispatcher.accessSet;
    Iterator iter = all.iterator();
    while (iter.hasNext()) {
      Access a = (Access) iter.next();
      if (a.accessID.equals(accessId)) {
        System.err.println("FOUND ACCESS OBJECT!!!");
        this.myAccess = a;
        showDetails = true;
      }
    }
    System.err.println("Did NOT FIND ACCESS OBJECT IN ACCESS MAP, TRYING ASYNC STORE...");
    if (showDetails == false) { //Try async store
      myAccess = (Access) com.dexels.navajo.mapping.AsyncStore.getInstance().accessStore.get(id);
      System.err.println("FOUND ACCESS IN ASYNCSTORE: " + myAccess);
      if (myAccess != null) {
        showDetails = true;
        isAsync = true;
      }
    }
  }

  public String getAccessId() {
    return myAccess.accessID;
  }
  public final String getIpAddress() {
    return myAccess.ipAddress;
  }
  public final String getHost() {
    if (myAccess != null) {
      return myAccess.hostName;
    } else {
      return null;
    }
  }
  public final String getUserName() {
    if (myAccess != null) {
      return myAccess.rpcUser;
    } else {
      return null;
    }
  }
  public final String getWebService() {
    if (myAccess != null) {
      return myAccess.rpcName;
    } else {
      return null;
    }
  }
  public final boolean getKilled() {
    if (myAccess != null && myAccess.getCompiledScript() != null) {
      return myAccess.getCompiledScript().getKill();
    } else {
      return false;
    }
 }

  public final java.util.Date getCreated() {
    return myAccess.created;
  }
  public final int getTotaltime() {
    return (int) ( System.currentTimeMillis() - myAccess.created.getTime() );
  }
  private MappableTreeNode getCurrentMap() {
    MappableTreeNode current = myAccess.getCompiledScript().currentMap;
    return current;
  }

  private String getResponseNavajo() {
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

  private String getRequestNavajo() {
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

  private String getOutMessageStack() {
    StringBuffer stackBuffer = new StringBuffer();
    Stack s = myAccess.getCompiledScript().outMsgStack;
    Iterator iter = s.iterator();
    while (iter.hasNext()) {
      Object o = iter.next();
      if (o != null) {
        stackBuffer.append(o + "\n");
      }
    }
    return stackBuffer.toString();
  }

  private String getMapStack() {
   StringBuffer stackBuffer = new StringBuffer();
   Stack s = myAccess.getCompiledScript().treeNodeStack;
   Iterator iter = s.iterator();
   while (iter.hasNext()) {
     MappableTreeNode o = (MappableTreeNode) iter.next();
     if (o != null && o.myObject != null) {
       stackBuffer.append(o.myObject.getClass().getName() + "\n");
     }
   }
   return stackBuffer.toString();
 }


}