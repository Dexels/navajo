package com.dexels.navajo.adapters.stream;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.adapter.sqlmap.SQLMapHelper;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

class SQLResult {
	
	private Map<String,Object> values = new HashMap<>();
	private Map<String,String> types = new HashMap<>();
	private List<String> order = new ArrayList<>();

	public SQLResult(ResultSet rs) throws Exception {
		ResultSetMetaData meta = rs.getMetaData();
		int columns = meta.getColumnCount();
		
		for (int i = 1; i < (columns + 1); i++) {
			String param = meta.getColumnLabel(i);
			int type = meta.getColumnType(i);
			Object value = null;
			value = SQLMapHelper.getColumnValue(rs, type, i);
			String typeString = SQLMapHelper.getType(i);
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

    public ReplicationMessage toMessage() {
    		// TODO replace with correct call
    		return ReplicationFactory.fromMap(null, values, types);
    }
}
