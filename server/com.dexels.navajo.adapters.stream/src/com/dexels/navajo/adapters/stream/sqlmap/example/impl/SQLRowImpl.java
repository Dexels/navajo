package com.dexels.navajo.adapters.stream.sqlmap.example.impl;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.adapters.stream.sqlmap.example.Row;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.Prop;

public class SQLRowImpl implements Row {

	@Override
	public Object get(String columnName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Row withValue(String name, Object value) {
		// TODO Auto-generated method stub
		return null;
	}


	public Msg toElement() {
		List<Prop> properties = new ArrayList<>();
//		int index = 0;
//		for (String name : columnNames) {
//			properties.add(Prop.create(name, get(index++)));
//		}
 		return Msg.createElement(properties);
	}
}
