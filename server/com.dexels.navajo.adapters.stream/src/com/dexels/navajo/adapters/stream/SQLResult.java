package com.dexels.navajo.adapters.stream;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.adapter.sqlmap.SQLMapHelper;
import com.dexels.navajo.script.api.UserException;

class SQLResult {
	
	private Map<String,Object> values = new HashMap<>();
	private Map<String,String> types = new HashMap<>();
	private List<String> order = new ArrayList<>();

	public SQLResult(ResultSet rs) throws SQLException, UserException {
		ResultSetMetaData meta = rs.getMetaData();
		int columns = meta.getColumnCount();
		
		for (int i = 1; i < (columns + 1); i++) {
			String param = meta.getColumnLabel(i);
			int type = meta.getColumnType(i);
			int scale = meta.getScale(i);
			Object value = null;
			value = SQLMapHelper.getColumnValue(rs, type, i);
			String typeString = SQLMapHelper.getSimplefiedType(type,scale);
			addValue(param.toUpperCase(), typeString, value);
		}
	}
	
    private final void addValue(String name, String type, Object o) {
        values.put(name, o);
        types.put(name, type);
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

    public ImmutableMessage toMessage() {
    		return ImmutableFactory.create(values, types);
    }
}
