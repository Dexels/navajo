package com.dexels.navajo.adapters.stream.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.adapters.stream.Row;

public class CSVRowImpl implements Row {

	private final Map<String,String> columns = new HashMap<>();
	private final List<String> columnIndexes = new ArrayList<>();
	private final List<String> columnNames;
	public CSVRowImpl(List<String> columnNames, String[] data) {
		int i = 0;
		this.columnNames = columnNames;
		for (String element : data) {
			columnIndexes.add(element);
			columns.put(columnNames.get(i), element);
			i++;
		}
	}

	public CSVRowImpl(CSVRowImpl parent, String name, String value) {
		this.columnNames = parent.columnNames;
		this.columnIndexes.addAll(parent.columnIndexes);
		for (String element : parent.columnIndexes) {
			columns.put(element,(String)parent.get(element));
		}
		columnIndexes.add(name);
		columns.put(name, value);
	}

	
	@Override
	public Object get(String columnName) {
		return columns.get(columnName);
	}

	@Override
	public Object get(int columnIndex) {
		return columnIndexes.get(columnIndex);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> elt : columns.entrySet()) {
			sb.append("{");
			sb.append(elt.getKey());
			sb.append(":");
			sb.append(elt.getValue());
			sb.append("}");
			
		}
		return sb.toString();
	}
	@Override
	public Row withValue(String name, Object value) {
		return new CSVRowImpl(this,name,(String)value);
	}

	public ImmutableMessage toElement() {
		Map<String,Object> values = new HashMap<>();
		Map<String,String> types = new HashMap<>();
		for (String name : columnNames) {
			types.put(name, "string");
			values.put(name, get(name));
		}
 		return ImmutableFactory.create(values, types);
	}
}
