package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.MappableTreeNode;
import com.dexels.navajo.document.*;
import java.util.Stack;
import java.util.Iterator;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.mapping.MappingException;
import java.util.Set;

public final class AccessMap implements Mappable {

  public String userName;
  public String webService;
  public String requestId;
  public String clientToken;
  public java.util.Date created;
  public Access myAccess; // myAccess contains the 'wrapped' access object.
  public Access callingAccess; // callingAccess is for the webservice that uses the AccessMap to introspect.
  public MappableTreeNode currentMap;
  public String accessId;
  public String requestNavajo = null;
  public String responseNavajo = null;
  public boolean killed = false;
  public boolean waiting = false;
  public String waitingFor = null;
  public CompiledScript myScript = null;
  
  /* Private vars */
  private boolean showDetails = false;
  private boolean isAsync = false;
  
  public AccessMap() {
  }
  
  public AccessMap(Access a) {
	  this.myAccess = a;
	  this.myScript = a.getCompiledScript();
  }

  public CompiledScript getMyScript() {
	  return myScript;
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
    m = NavajoFactory.getInstance().createMessage(callingAccess.getOutputDoc(), name);
    if (parent != null) {
      parent.addMessage(m);
    } else {
    	callingAccess.getOutputDoc().addMessage(m);
    }
    return m;
  }

  private void addProperty(Message m, String name, Object value, String type, int length) throws NavajoException, MappingException {
    MappingUtils.setProperty(false, m, name, value, type, null, Property.DIR_OUT, "", length,
    		callingAccess.getOutputDoc(), callingAccess.getCompiledScript().inDoc, false);
  }

  private void showMapDetails(Message parent, MappableTreeNode m) throws NavajoException, MappingException, UserException {

    Mappable myMap = (Mappable) m.myObject;

    addProperty(parent, "Map", m.getMapName(), Property.STRING_PROPERTY, 50);
    addProperty(parent, "Totaltime", new Integer(m.getTotaltime()), Property.STRING_PROPERTY, 50);

    Class ccc = null;
	try {
		ccc = Class.forName("com.dexels.navajo.adapter.SPMap");
	} catch (ClassNotFoundException e) {
	}
    if (myMap instanceof com.dexels.navajo.adapter.SQLMap || (ccc!=null && (ccc.isInstance(myMap)))) {
      SQLMap mySQL = (SQLMap) myMap;
      Message parameters = getMessage(parent, "MapParameters");
      addProperty(parameters, "Datasource", mySQL.getDatasource(), Property.STRING_PROPERTY, 50);
      addProperty(parameters, "DatabaseProductName", mySQL.getDatabaseProductName(), Property.STRING_PROPERTY, 100);
      addProperty(parameters, "DatabaseVersion", mySQL.getDatabaseVersion(), Property.STRING_PROPERTY, 100);
      addProperty(parameters, "SessionId", mySQL.getDatabaseSessionId(), Property.STRING_PROPERTY, -1);
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
        addProperty(user, "Stacktrace", myAccess.getCompiledScript().getStackTrace(), Property.MEMO_PROPERTY, 4096);
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
	  if ( myScript != null ) {
		  myScript.kill();
	  }
  }

  public void setAccessId(String id) throws UserException {

    this.accessId = id;
    if (accessId == null) {
      throw new UserException(-1, "Set accessId first");
    }
    Set<Access> all = com.dexels.navajo.server.DispatcherFactory.getInstance().getAccessSet();
    Iterator<Access> iter = all.iterator();
    while (iter.hasNext()) {
      Access a = iter.next();
      if (a.accessID.equals(accessId)) {
        this.myAccess = a;
        showDetails = true;
      }
    }
    if (showDetails == false) { //Try async store
      myAccess = (Access) com.dexels.navajo.mapping.AsyncStore.getInstance().accessStore.get(id);
      if (myAccess != null) {
        showDetails = true;
        isAsync = true;
      }
    }
  }

  public boolean getWaiting() {
	  return ( myAccess.getWaitingForPreviousResponse() != null );
  }
  
  public String getWaitingFor() {
	  return myAccess.getWaitingForPreviousResponse();
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

		  Navajo in = myAccess.getOutputDoc().copy();
		  in.removeMessage("__globals__");
	      in.removeMessage("__parms__");
	      
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
      Navajo in = myAccess.getCompiledScript().inDoc.copy();
      in.removeMessage("__globals__");
      in.removeMessage("__parms__");
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

  public String getRequestId() {
	  if ( myAccess != null ) {
		  return myAccess.getInDoc().getHeader().getRequestId();
	  } else {
		  return null;
	  }
  }

  public String getClientToken() {
	  if ( myAccess != null ) {
		  return myAccess.getClientToken();
	  } else {
		  return null;
	  }
  }

  public void load(Access access) throws MappableException, UserException {
	  callingAccess = access;
  }

}
