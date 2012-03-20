package com.dexels.navajo.adapter;

import com.dexels.navajo.adapter.sqlmap.RecordMap;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
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

  public void load(Access access) throws MappableException, UserException {
    super.load(access);
    outputDoc = access.getOutputDoc();
  }

  public void store() throws MappableException, UserException {

    // Construct Navajo message.
    try {
      Message recordSet = NavajoFactory.getInstance().createMessage(outputDoc,
          "RecordSet", Message.MSG_TYPE_ARRAY);
      try {
        outputDoc.addMessage(recordSet);
      }
      catch (NavajoException ex) {
        throw new UserException( -1, ex.getMessage(), ex);
      }

      ResultSetMap[] resultSet = getResultSet();
      for (int i = 0; i < resultSet.length; i++) {
        Message record = NavajoFactory.getInstance().createMessage(outputDoc,
            "RecordSet", Message.MSG_TYPE_ARRAY_ELEMENT);
        recordSet.addElement(record);
        RecordMap[] columns = resultSet[i].getRecords();
        //System.err.println("Processing row " + i);
        for (int j = 0; j < columns.length; j++) {
          try {
            Object value = columns[j].getRecordValue();
            String type = (value != null ?
                           MappingUtils.determineNavajoType(value) : "unknown");
            //String sValue = (value != null ? Util.toString(value, type) : "");
            Property prop = NavajoFactory.getInstance().createProperty(
                outputDoc,
                columns[j].recordName,
                type,
                null,
                0,
                "",
                Property.DIR_IN);
            prop.setAnyValue(value);
            record.addProperty(prop);
          }
          catch (Exception ex1) {
            throw new UserException( -1, ex1.getMessage(), ex1);
          }
        }
      }
    } finally {
      super.store();
    }
  }
}