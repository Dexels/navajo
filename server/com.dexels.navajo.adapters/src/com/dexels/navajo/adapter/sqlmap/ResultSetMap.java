/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter.sqlmap;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;


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

@SuppressWarnings({"rawtypes", "unchecked"})
public class ResultSetMap implements Mappable {

    public String columnName;
    public Object columnValue;
    public String type;
    public RecordMap [] records;

    private HashMap values = new HashMap();
	private ArrayList order = new ArrayList();

    @Override
	public final void load(Access access) throws MappableException, UserException {}

    @Override
	public final void store() throws MappableException, UserException {}

    @Override
	public final void kill() {}
    
    public ResultSetMap() {
    	
    }

    public ResultSetMap(ResultSet rs) throws SQLException, UserException {
		ResultSetMetaData meta = rs.getMetaData();
		int columns = meta.getColumnCount();
		
		for (int i = 1; i < (columns + 1); i++) {
			String param = meta.getColumnLabel(i);
			int type = meta.getColumnType(i);

			Object value = null;
			value = SQLMapHelper.getColumnValue(rs, type, i);
			addValue(param.toUpperCase(), value);
		}

    	
    }

    
    public final void addValue(String name, Object o) {
      values.put(name, o);
      order.add(name);
    }

    /**
     * Set the columnname
     */
    public final void setColumnName(String name) {
        this.columnName = name;
    }

    public final RecordMap [] getRecords() {
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

    public final Object getColumnValue() {
        return values.get(columnName);
    }

    public final String getColumnName(final Integer index) throws UserException {
      if (index != null) {
        int inx = index.intValue();
        if (inx >= order.size())
          throw new UserException(-1, "Column index out of range: " + inx + " > " + (order.size()+1));
        String name = (String) order.get(inx);
        return name;
      } else
        throw new UserException(-1, "Null value given in getColumnValue(Integer)");
    }

    public final Object getColumnValue(final Integer index) throws UserException {
      if (index != null) {
        int inx = index.intValue();
        if (inx >= order.size()) {
          throw new UserException( -1,
                                  "Column index out of range: " + inx + " > " +
                                  (order.size() + 1));
        }
        String name = (String) order.get(inx);
        return values.get(name);
      } else
        throw new UserException(-1, "Null value given in getColumnValue(Integer)");
    }

    public final Object getColumnValue(final String columnName) throws UserException {
        String upperC = columnName.toUpperCase();
        if (!values.containsKey(upperC))
            throw new UserException(-1, "Column name: [" + columnName + "] does not exist in resultset, check your script");
        return values.get(upperC);
    }

    public final String getType(final String columnName) throws UserException {
        return MappingUtils.determineNavajoType(getColumnValue(columnName));
    }

	public String getColumnName() {
		return columnName;
	}
	
	public int getValuesSize() {
		return values.size();
	}

}
