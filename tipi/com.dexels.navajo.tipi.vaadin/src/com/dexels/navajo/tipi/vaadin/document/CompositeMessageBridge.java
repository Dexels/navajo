package com.dexels.navajo.tipi.vaadin.document;

import java.util.List;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Message;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

public class CompositeMessageBridge extends CompositeItem implements Item {

	private static final long serialVersionUID = -8870100156134809791L;
	//private List<String> editableList = null;
	private final Message src;
	
//	private final Map<Object,PropertyItem> propertyMap = new HashMap<Object,PropertyItem>();

	public CompositeMessageBridge(Message src) {
		this(src,null);
	}
	
	public CompositeMessageBridge(Message src, List<String> editableList) {
		super();
		//this.editableList = editableList;
		this.src = src;
		if(src==null) {
			return;
		}
		if(Message.MSG_TYPE_ARRAY.equals(src.getType())) {
			throw new UnsupportedOperationException("Can not bridge an array message to an item");
		}
		List<com.dexels.navajo.document.Property> properties = src.getAllProperties();
		for (com.dexels.navajo.document.Property property : properties) {
			if(editableList!=null) {
				boolean edit = editableList.contains(property.getName());
				PropertyItem pi = new PropertyItem(property,edit);
				addItem(property.getName(),pi);
			} else {
				PropertyItem pi = new PropertyItem(property,false);
				addItem(property.getName(),pi);
			}
		}
	}
	
	@Override
	public Property getItemProperty(Object id) {
		if("message".equals(id)) {
			ObjectProperty<Message> op = new ObjectProperty<Message>(src);
			return op;
		}
		StringTokenizer st = new StringTokenizer((String) id,"@");
		String propertyName = st.nextToken();
		String propertyAspect = st.nextToken();
		PropertyItem pi = (PropertyItem) getItemById(propertyName); 
		if(pi==null) {
			return null;
		}
		return pi.getItemProperty(propertyAspect);
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
