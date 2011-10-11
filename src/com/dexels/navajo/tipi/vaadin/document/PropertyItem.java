package com.dexels.navajo.tipi.vaadin.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Property;
import com.vaadin.data.Item;

public class PropertyItem implements Item {

	
	private static final long serialVersionUID = 1L;
	private final Property src;
	private final Map<String,com.vaadin.data.Property> aspectMap = new HashMap<String,com.vaadin.data.Property>();
	
	private final String[] aspects = new String[]{Property.PROPERTY_VALUE,Property.PROPERTY_TYPE,Property.PROPERTY_DESCRIPTION,Property.PROPERTY_CARDINALITY,Property.PROPERTY_DIRECTION,Property.PROPERTY_LENGTH};
	private final Collection<Object> aspectCollection = new ArrayList<Object>();
	
	private final com.vaadin.data.Property valueProperty;
	
	public PropertyItem(Property src, boolean editable) {
		this.src = src;
		for (String aspect : aspects) {
			PropertyAspectProperty asp = new PropertyAspectProperty(src,aspect);
			aspectMap.put(aspect, asp);
			aspectCollection.add(aspect);
		}
		if(Property.SELECTION_PROPERTY.equals(src.getType())) {
			this.valueProperty = new SelectedItemValuePropertyBridge(src,editable);
			aspectMap.put(Property.PROPERTY_VALUE, this.valueProperty);
		} else {
			// TODO: Support read-only forcing?
			this.valueProperty = new ValuePropertyBridge(src,editable);
			aspectMap.put(Property.PROPERTY_VALUE, this.valueProperty);
		}
	}

	@Override
	public com.vaadin.data.Property getItemProperty(Object id) {
		if(Property.PROPERTY_VALUE.equals(id)) {
			return this.valueProperty;
		}
		return aspectMap.get(id);
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return aspectCollection;
	}

	@Override
	public boolean addItemProperty(Object id, com.vaadin.data.Property property) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not add properties to Property item!");
	}

	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not remove properties from Property item!");
	}
	
	public Property getSource() {
		return src;
	}

}
