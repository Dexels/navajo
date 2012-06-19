package com.dexels.navajo.adapter;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.UserException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AsyncProxyMap extends AsyncMappable {

  public String method;
  public String username;
  public String password;

  private Navajo outDoc;
  private Navajo inDoc;
  private Access access;

  public void load(Access access) throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
    username = access.rpcUser;
    password = access.rpcPwd;
    this.access = access;
    outDoc = access.getInDoc().copy();
    killOnFinnish = true;
  }

  public void setMethod(String name) {
    System.out.println("AsyncProxyMap: in setMethod(), name = " + name);
    this.method = name;
  }

  public void kill() {
  }

  public void afterResponse() {
    System.out.println("AsyncProxyMap: in afterResponse()");
    access.setOutputDoc(inDoc);
  }

  public int getPercReady() {
    System.out.println("AsyncProxyMap: in getPercReady()");
    return 0;
  }

  public void beforeResponse(Access access) {
    access.setOutputDoc(inDoc);
    System.out.println("AsyncProxyMap: in beforeResponse()");
    System.out.println("INDOC = " + access.getOutputDoc());
  }

  public void store() throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
    System.out.println("AsyncProxyMap: in store()");
  }

  public void run() throws com.dexels.navajo.server.UserException {
	  
     Header h = outDoc.getHeader();
     if (h == null) {
          h = NavajoFactory.getInstance().createHeader(outDoc, method, username, password, -1);
          outDoc.addHeader(h);
     } else {
          h.setRPCName(method);
          h.setRPCPassword(password);
          h.setRPCUser(username);
    }
    // Clear request id.
    h.setRequestId(null);
    try {
      inDoc = DispatcherFactory.getInstance().handle(outDoc);
    } catch (Exception e) {
      e.printStackTrace();
      throw new UserException(-1, e.getMessage());
    } finally {
    	System.err.println("Setting set is finished.");
    	setIsFinished();
    }
    
  }

  public void afterRequest() throws UserException {
    System.out.println("AsyncProxyMap: in afterRequest()");
    if (method == null)
      throw new UserException(-1, "AsyncProxyMap: specify a method");
  }
}