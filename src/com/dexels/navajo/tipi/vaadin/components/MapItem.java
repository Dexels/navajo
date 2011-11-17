package com.dexels.navajo.tipi.vaadin.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class MapItem extends Object implements Item {

	private final Map<String,String> mymap = new HashMap<String, String>();
	@Override
	public Property getItemProperty(Object id) {
		
		return null;
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

}
