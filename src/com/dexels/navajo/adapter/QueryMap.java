package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import java.sql.*;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.util.Util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class QueryMap extends SQLMap {

  public String table;

  Navajo outputDoc;

  public void load(Parameters parms, Navajo inMessage, Access access,
                   NavajoConfig config) throws MappableException, UserException {
    super.load(parms, inMessage, access, config);
    outputDoc = access.getOutputDoc();
  }

  public void store() throws MappableException, UserException {

    // Construct Navajo message.
    try {

      Message recordSet = NavajoFactory.getInstance().createMessage(outputDoc, "RecordSet", Message.MSG_TYPE_ARRAY);
      outputDoc.addMessage(recordSet);

      ResultSetMap [] resultSet = getResultSet();
      for (int i = 0; i < resultSet.length; i++) {
        Message record = NavajoFactory.getInstance().createMessage(outputDoc, "RecordSet", Message.MSG_TYPE_ARRAY_ELEMENT);
        recordSet.addElement(record);
        RecordMap [] columns = resultSet[i].getRecords();
        System.err.println("Processing row " + i);
        for (int j = 0; j < columns.length; j++) {
          Object value = columns[j].getRecordValue();
          String type = (value != null ? MappingUtils.determineNavajoType(value) : "unknown");
          String sValue = (value != null ? Util.toString(value, type) : "");
          Property prop = NavajoFactory.getInstance().createProperty(
              outputDoc,
              columns[j].recordName,
              type,
              sValue,
              10,
              "",
              Property.DIR_IN);
          record.addProperty(prop);
        }
      }

    } catch (Exception e) {
      e.printStackTrace(System.err);
    } finally {
      super.store();
    }
  }

  public void setTable(String table) throws UserException {
    this.table = table;
    String query = "SELECT * FROM " + table;
    super.setQuery(query);
  }

}