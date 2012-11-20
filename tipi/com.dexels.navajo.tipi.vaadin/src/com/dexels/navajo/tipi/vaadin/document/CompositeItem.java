package com.dexels.navajo.tipi.vaadin.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public abstract class CompositeItem implements Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4098744742563029137L;
	private final Map<String,Item> items = new HashMap<String,Item>();

	public CompositeItem() {
	}
	
	public void addItem(String name, Item it) {
		this.items.put(name,it);
	}
	
	
	@Override
	public Collection<?> getItemPropertyIds() {
		Collection<Object> results = new ArrayList<Object>();
		for (Entry<String,Item> entry : items.entrySet()) {
			Collection<String> itemIds = (Collection<String>) entry.getValue().getItemPropertyIds();
			for (String subItemId : itemIds) {
				results.add(entry.getKey()+"@"+subItemId);
			}
		}
		return results;
	}
	
	protected Item getItemById(String id) {
		return items.get(id);
	}

	@Override
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not add properties to message using VAADIN data model!");
	}

	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not remove properties from message using VAADIN data model!");
	}

}
