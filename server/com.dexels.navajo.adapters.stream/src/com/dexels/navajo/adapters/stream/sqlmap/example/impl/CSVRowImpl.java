package com.dexels.navajo.adapters.stream.sqlmap.example.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.adapters.stream.sqlmap.example.Row;

public class CSVRowImpl implements Row {

	private final Map<String,String> columns = new HashMap<>();
	private final List<String> columnIndexes = new ArrayList<>();
	
	public CSVRowImpl(List<String> columnNames, String[] data) {
		int i = 0;
		for (String element : data) {
			columnIndexes.add(element);
			columns.put(columnNames.get(i), element);
			i++;
		}
	}
	@Override
	public Object get(String columnName) {
		return columns.get(columnName);
	}

	@Override
	public Object get(int columnIndex) {
		return columnIndexes.get(columnIndex);
	}

}
