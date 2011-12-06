package com.dexels.navajo.tipi.vaadin.components;

import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class MapItem extends Object implements Item {

	private static final long serialVersionUID = 3106826560149876082L;
//	private final Map<String,String> mymap = new HashMap<String, String>();
	@Override
	public Property getItemProperty(Object id) {
		
		return null;
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return null;
	}

	@Override
	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		return false;
	}

	@Override
	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		return false;
	}

}
