package com.dexels.navajo.adapters.stream.sqlmap.example;

import com.dexels.navajo.document.stream.api.Msg;

public interface Row {
	public Object get(String columnName);
	public Object get(int columnIndex);
	public Row withValue(String name, Object value);
	public Msg toElement();

}
