package com.dexels.navajo.adapters.stream;

import com.dexels.immutable.api.ImmutableMessage;

public interface Row {
	public Object get(String columnName);
	public Object get(int columnIndex);
	public Row withValue(String name, Object value);
	public ImmutableMessage toElement();

}
