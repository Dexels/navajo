package com.dexels.navajo.adapter;

import javax.naming.Context;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import java.util.*;
import java.sql.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 *
 * $Id$
 *
 */

public class ResultSetMap implements Mappable  {

  public String columnName;
  public Object columnValue;
  public String type;
  protected HashMap values =  new HashMap();

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {

  }

  /**
   * Set the columnname
   */
  public void setColumnName(String name) {
    this.columnName = name;
  }

  public Object getColumnValue() throws UserException {
    return values.get(columnName);
  }

  public Object getColumnValue(String columnName) throws UserException {
    return values.get(columnName);
  }

  public String getType() {
    return "string";
  }

  public static void main(String args[]) {
      ResultSetMap rm = new ResultSetMap();
      rm.values.put("aap", null);
      System.out.println("aap = " + rm.values.get("aap"));
  }
}