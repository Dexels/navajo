package com.dexels.navajo.adapters.stream;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.adapter.sqlmap.SQLMapHelper;

public class SQLResult {
	
	private Map<String,Object> values = new HashMap<>();
	private List<String> order = new ArrayList<>();

	public SQLResult(ResultSet rs) throws Exception {
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
	
    private final void addValue(String name, Object o) {
        values.put(name, o);
        order.add(name);
      }
	
    public List<String> columnNames() {
    	return Collections.unmodifiableList(order);
    }
    public final Object columnValue(int index) {
    	return values.get(order.get(index));

    }
    public final Object columnValue(String name) {
    	return values.get(name);
	}

    
//	public final String getColumnName(final Integer index) throws SQLException {
//		try {
//			return parent.getColumnName(index);
//		} catch (UserException e) {
//			throw new SQLException(e);
//		}
//	}
//
//	public final Object getColumnValue(final Integer index) throws SQLException {
//		try {
//			return parent.getColumnValue(index);
//		} catch (UserException e) {
//			throw new SQLException(e);
//		}
//	}
//
//	public final Object getColumnValue(final String columnName) {
//		try {
//			return parent.getColumnValue(columnName);
//		} catch (UserException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public final String getType(final String columnName) throws SQLException {
//		try {
//			return parent.getType(columnName);
//		} catch (UserException e) {
//			throw new SQLException(e);
//		}
//
//	}
}
