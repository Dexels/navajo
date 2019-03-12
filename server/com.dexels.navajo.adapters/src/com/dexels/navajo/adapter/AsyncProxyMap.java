package com.dexels.navajo.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.DispatcherFactory;

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

  private static final Logger logger = LoggerFactory
		.getLogger(AsyncProxyMap.class);


@Override
  public void load(Access access) throws UserException, MappableException {
    username = access.rpcUser;
    password = access.rpcPwd;
    this.access = access;
    outDoc = access.getInDoc().copy();
    killOnFinnish = true;
  }

  public void setMethod(String name) {
    logger.debug("AsyncProxyMap: in setMethod(), name = {}", name);
    this.method = name;
  }

  @Override
public void kill() {
  }

  @Override
public void afterResponse() {
    logger.debug("AsyncProxyMap: in afterResponse()");
    access.setOutputDoc(inDoc);
  }

  @Override
public int getPercReady() {
    logger.debug("AsyncProxyMap: in getPercReady()");
    return 0;
  }

  @Override
public void beforeResponse(Access access) {
    access.setOutputDoc(inDoc);
    logger.debug("AsyncProxyMap: in beforeResponse()");
    logger.debug("INDOC = {}", access.getOutputDoc());
  }

  @Override
  public void store() throws UserException, MappableException {
    logger.debug("AsyncProxyMap: in store()");
  }

  @Override
  public void run() throws UserException {
	  
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
      throw new UserException(-1, e.getMessage(),e);
    } finally {
    	logger.info("Setting set is finished.");
    	setIsFinished();
    }
    
  }

  @Override
public void afterRequest() throws UserException {
    logger.debug("AsyncProxyMap: in afterRequest()");
    if (method == null)
      throw new UserException(-1, "AsyncProxyMap: specify a method");
  }
}
