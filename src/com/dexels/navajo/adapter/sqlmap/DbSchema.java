package com.dexels.navajo.adapter.sqlmap;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import java.util.ArrayList;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DbSchema implements Mappable {

  public String name;
  public boolean dummy = false;
  public DbTable [] tables;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DbTable [] getTables() {
    return tables;
  }

  public boolean getDummy() {
    return dummy;
  }

  protected void setTables(ArrayList l) {
    tables = new DbTable[l.size()];
    tables = (DbTable []) l.toArray(tables);
  }

}