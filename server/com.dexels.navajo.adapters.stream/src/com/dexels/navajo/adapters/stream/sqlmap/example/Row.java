package com.dexels.navajo.adapters.stream.sqlmap.example;

public interface Row {
	public Object get(String columnName);
	public Object get(int columnIndex);
}
