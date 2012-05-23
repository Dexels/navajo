package com.dexels.navajo.tipi.vaadin.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
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
	
	private final static Logger logger = LoggerFactory
			.getLogger(SelectionListBridge.class);
	
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
			logger.error("Error creating SelectionListBridge: ",e);
		}
	}
	
	public void select(String newValue) {
		src.setSelected(newValue);
	}

	
	@Override
	public Item getItem(Object itemId) {
		return selectionMap.get(itemId);
	}

	public Selection getSelection(String selectionName) {
		SelectionBridge sb = selectionMap.get(selectionName);
		return sb.getSource();
	}
	
	public SelectionBridge getSelectionBridge(String selectionName) {
		SelectionBridge sb = selectionMap.get(selectionName);
		return sb;
	}
	
	public SelectionBridge getSelectionBridge() {
		Selection ssss = src.getSelected();
		if(ssss!=null) {
			SelectionBridge sb = selectionMap.get(ssss.getName());
			return sb;
		}
		return null;
	}

	
	@Override
	public Collection<?> getContainerPropertyIds() {
		HashSet<Object> selected = new HashSet<Object>();
		selected.add(Selection.SELECTION_NAME);
		selected.add(Selection.SELECTION_SELECTED);
		selected.add(Selection.SELECTION_VALUE);
		return selected;
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

	public SelectionBridge getSelected() {
		for (Object n : selectionList) {
			SelectionBridge sb = selectionMap.get(n);
			if(sb.isSelected()) {
				return sb;
			}
		}
		return null;
	}
	public Collection<Object> getSelectedCollection() {
		List<Object> result = new ArrayList<Object>();
		for (Object n : selectionList) {
			SelectionBridge sb = selectionMap.get(n);
			if(sb.isSelected()) {
				result.add(n);
			}
		}
		return Collections.unmodifiableCollection(result);
	}
	@Override
	public Class<?> getType(Object propertyId) {
		
		return Selection.class;
	}

	@Override
	public int size() {
		return selectionMap.size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return selectionMap.containsKey(itemId);
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		String name = (String)itemId;
		Selection s = NavajoFactory.getInstance().createSelection(src.getRootDoc(), name, "unknown", false);
		src.addSelection(s);
		SelectionBridge sb = new SelectionBridge(src, s);
		selectionMap.put(name, sb);
//		throw new UnsupportedOperationException("Can not edit properties to container using VAADIN data model!");
		return sb;
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
	
	public com.dexels.navajo.document.Property getSource() {
		return src;
	}



}
