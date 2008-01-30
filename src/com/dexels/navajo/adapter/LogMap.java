package com.dexels.navajo.adapter;


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.document.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class LogMap implements Mappable {

  public String message;


  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void setMessage(String msg) {
	  AuditLog.log("LogMap", message);
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {

  }
}
