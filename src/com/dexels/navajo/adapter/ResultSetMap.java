package com.dexels.navajo.adapter;

import com.dexels.navajo.document.*;

import javax.naming.Context;
import com.dexels.navajo.server.Parameters;
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

public class ResultSetMap implements Mappable {

    public String columnName;
    public Object columnValue;
    public String type;
    public RecordMap [] records;

    private HashMap values = new HashMap();
    private ArrayList order = new ArrayList();

    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {}

    public void store() throws MappableException, UserException {}

    public void kill() {}

    protected void addValue(String name, Object o) {
      values.put(name, o);
      order.add(name);
    }

    /**
     * Set the columnname
     */
    public void setColumnName(String name) {
        this.columnName = name;
    }

    public RecordMap [] getRecords() throws UserException {
        if (records == null) {
          ArrayList list = new ArrayList();
          for (int i = 0; i < order.size(); i++) {
            String name = (String) order.get(i);
            RecordMap rm = new RecordMap();
            rm.recordValue = values.get(name);
            rm.recordName = name;
            list.add(rm);
          }
          records = new RecordMap[list.size()];
          records = (RecordMap []) list.toArray(records);
        }
        return records;
    }

    public Object getColumnValue() throws UserException {
        return values.get(columnName);
    }

    public String getColumnName(Integer index) throws UserException {
      if (index != null) {
        int inx = index.intValue();
        if (inx >= order.size())
          throw new UserException(-1, "Column index too large: " + inx + " > " + (order.size()+1));
        String name = (String) order.get(inx);
        return name;
      } else
        throw new UserException(-1, "Null value given in getColumnValue(Integer)");
    }

    public Object getColumnValue(Integer index) throws UserException {
      if (index != null) {
        int inx = index.intValue();
        if (inx >= order.size())
          throw new UserException(-1, "Column index too large: " + inx + " > " + (order.size()+1));
        String name = (String) order.get(inx);
        return values.get(name);
      } else
        throw new UserException(-1, "Null value given in getColumnValue(Integer)");
    }

    public Object getColumnValue(String columnName) throws UserException {
        if (!values.containsKey(columnName))
            throw new UserException(-1, "Column name: [" + columnName + "] does not exist in resultset, check your script");
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
