package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class NavajoAccess implements Mappable {

  private Access access;
  private NavajoConfig config;

  public String rpcName;
  public String rpcUser;

  public NavajoAccess() {
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    this.access = access;
    this.config = config;
  }

  public String getRpcName() {
    return access.rpcName;
  }

  public String getRpcUser() {
    return access.rpcUser;
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }
}