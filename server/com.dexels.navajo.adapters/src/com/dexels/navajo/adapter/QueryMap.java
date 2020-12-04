/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import com.dexels.navajo.adapter.sqlmap.RecordMap;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
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

  @Override
public void load(Access access) throws MappableException, UserException {
    super.load(access);
    outputDoc = access.getOutputDoc();
  }

  @Override
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
        for (int j = 0; j < columns.length; j++) {
          try {
            Object value = columns[j].getRecordValue();
            String type = (value != null ?
                           MappingUtils.determineNavajoType(value) : "unknown");
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