package com.dexels.navajo.tipi.vaadin.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dexels.navajo.document.Selection;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

public class SelectionBridge implements Item {

	private static final long serialVersionUID = 7739272635668740519L;
	private final Selection src;
//	private final com.dexels.navajo.document.Property parent;
	private final ObjectProperty<String> name;
	private final ObjectProperty<String> value;
	private final ObjectProperty<Boolean> isSelected;
	
	public SelectionBridge(com.dexels.navajo.document.Property parent, Selection src) {
		this.src = src;
//		this.parent = parent;
		name = new ObjectProperty<String>(src.getName(),String.class,true);
		value = new ObjectProperty<String>(src.getValue(),String.class,true);
		isSelected = new ObjectProperty<Boolean>(new Boolean(src.isSelected()),Boolean.class,false);
	}

	
	public String toString() {
		return src.getName();
		
	}
	
	public boolean isSelectedBool() {
		return src.isSelected();
	}
	@Override
	public Property getItemProperty(Object id) {
		if(id==Selection.SELECTION_NAME) {
			return name;
		}
		if(id==Selection.SELECTION_VALUE) {
			return value;
		}
		if(id==Selection.SELECTION_SELECTED) {
			return isSelected;
		}

		return null;
	}

	public boolean isSelected() {
		return isSelected.getValue();
	}
	
	@Override
	public Collection<?> getItemPropertyIds() {
		List<String> res = new ArrayList<String>();
		res.add(Selection.SELECTION_NAME);
		res.add(Selection.SELECTION_VALUE);
		res.add(Selection.SELECTION_SELECTED);
		
		return res;
	}

	@Override
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not add properties to message using VAADIN data model!");
	}

	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not remove properties from message using VAADIN data model!");
	}

	public Selection getSource() {
		return src;
	}

}
