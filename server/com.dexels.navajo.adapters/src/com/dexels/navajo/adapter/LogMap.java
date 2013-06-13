package com.dexels.navajo.adapter;


import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.util.AuditLog;

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


  public void load(Access access) throws MappableException, UserException {
  }

  /**
 * @param msg  
 */
public void setMessage(String msg) {
	  AuditLog.log("LogMap", message);
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {

  }
}
