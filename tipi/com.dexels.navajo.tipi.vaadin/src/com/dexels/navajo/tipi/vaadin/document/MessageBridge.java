package com.dexels.navajo.tipi.vaadin.document;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class MessageBridge implements Item {

	private static final long serialVersionUID = 7739272635668740519L;
	private final Message src;
	
	private final Map<Object,ValuePropertyBridge> propertyMap = new HashMap<Object,ValuePropertyBridge>();
	
	public MessageBridge(Message src,List<String> editableColumns) {
		this.src = src;
		if(Message.MSG_TYPE_ARRAY.equals(src.getType())) {
			throw new UnsupportedOperationException("Can not bridge an array message to an item");
		}
		List<com.dexels.navajo.document.Property> properties = src.getAllProperties();
		for (com.dexels.navajo.document.Property property : properties) {
			System.err.println(">>>>>>>>>CHECKING EDITABLEEEEEEE: "+property.getName()+" list: "+editableColumns);
			boolean editable = editableColumns.contains(property.getName());
			ValuePropertyBridge pb = new ValuePropertyBridge(property,editable);
			propertyMap.put(property.getName(), pb);
		}
	}

	public int getIndex() {
		return src.getIndex();
	}
	
	@Override
	public Property getItemProperty(Object id) {
		return propertyMap.get(id);
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return propertyMap.keySet();
	}

	@Override
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not add properties to message using VAADIN data model!");
	}

	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not remove properties from message using VAADIN data model!");
	}

	public Message getSource() {
		return src;
	}

}
