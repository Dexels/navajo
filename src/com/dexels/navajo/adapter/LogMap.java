package com.dexels.navajo.adapter;


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.logger.*;

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

  private static NavajoLogger logger = NavajoConfig.getNavajoLogger(LogMap.class); //Logger.getLogger( LogMap.class );

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void setMessage(String msg) {
    System.out.println("In LogMap setMessage(): " + msg);
    logger.log(NavajoPriority.INFO, msg);
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {

  }
}
