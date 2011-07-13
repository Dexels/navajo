package com.dexels.navajo.tipi.vaadin.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Selection;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class SelectionListBridge implements Container {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8142853866066837145L;


	private final com.dexels.navajo.document.Property src;

	
	private final Map<Object,SelectionBridge> selectionMap = new HashMap<Object,SelectionBridge>();
	private final List<Object> selectionList = new ArrayList<Object>();
	
	public SelectionListBridge(com.dexels.navajo.document.Property src) {
		this.src = src;
		if(!com.dexels.navajo.document.Property.SELECTION_PROPERTY.equals(src.getType())) {
			throw new UnsupportedOperationException("Can not bridge non-selection property to container");
		}

		List<Selection> selections;
		try {
			selections = src.getAllSelections();
//			int i = 0;
			for (Selection selection : selections) {
				SelectionBridge pb = new SelectionBridge(src,selection);
				String name = selection.getName();
				selectionMap.put(name, pb);
				selectionList.add(name);
				//				i++;
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}
	@Override
	public Item getItem(Object itemId) {
		System.err.println("Getting item: "+itemId);
		return selectionMap.get(itemId);
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		return new HashSet<Object>();
	}

	@Override
	public Collection<?> getItemIds() {
		return selectionList;
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		SelectionBridge mb = selectionMap.get(itemId);
		return mb.getItemProperty(propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		
		return Selection.class;
	}

	@Override
	public int size() {
		System.err.println("Getting size: "+selectionMap);
		return selectionMap.size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return selectionMap.containsKey(itemId);
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not edit properties to container using VAADIN data model!");
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not edit properties to container using VAADIN data model!");

	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not edit properties to container using VAADIN data model!");

	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not add properties to container using VAADIN data model!");
	}

	@Override
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not remove properties from container using VAADIN data model!");
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not remove all messages. Didn't implement it. ");
	}

}
