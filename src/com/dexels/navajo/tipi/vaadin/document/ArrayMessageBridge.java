package com.dexels.navajo.tipi.vaadin.document;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class ArrayMessageBridge implements Container {

	private static final long serialVersionUID = -6228404246238997251L;
	private final Message src;
	protected List<String> visibleColumns;
	
	private final Map<Object,Item> messageMap = new HashMap<Object,Item>();
	
	private final Map<Object, Property> containerProperties = new HashMap<Object, Property>();

	
	public ArrayMessageBridge(Message src) {
		this.src = src;
		if(src==null) {
			return;
		}
		if(!Message.MSG_TYPE_ARRAY.equals(src.getType())) {
			throw new UnsupportedOperationException("Can not bridge an non-array message to a container");
		}
		// TODO rewrite to laziness
		List<com.dexels.navajo.document.Message> messages = src.getAllMessages();
		int i = 0;
		for (Message message : messages) {
			Item pb = createItemFromMessage(message);
			System.err.println("Mapping message to: "+i);
			messageMap.put(new Integer(i), pb);
			i++;
		}
		this.visibleColumns = null;
	}

	public ArrayMessageBridge(Message m, List<String> visibleColumns) {
		this(m);
		this.visibleColumns = visibleColumns;		
	}
	
	
	protected Item createItemFromMessage(Message message) {
		return new MessageBridge(message);
	}
	@Override
	public Item getItem(Object itemId) {
		return messageMap.get(itemId);
	}

	protected void addVisibleColumn(String columnId) {
		visibleColumns.add(columnId);
	}
	
	@Override
	public Collection<?> getContainerPropertyIds() {
		if(visibleColumns!=null) {
			return visibleColumns;
		}
		// If no column names have been defined, try do generate a list based on the example message
		Message m = getExampleMessage();
		
		HashSet<Object> propertyIds = new HashSet<Object>();
		if(m==null) {
			return propertyIds;
		}
		List<com.dexels.navajo.document.Property> props = m.getAllProperties();
		for (com.dexels.navajo.document.Property property : props) {
			propertyIds.add(property.getName());
		}
		return propertyIds;
	}


	/**
	 * Get an example of what the message looks like. Ideally, a definition message. Otherwise, return the first.
	 * @return
	 */
	public Message getExampleMessage() {
		Message def = src.getDefinitionMessage();
		if(def!=null) {
			return def;
		}
		if(src.getArraySize()!=0) {
			return src.getMessage(0);
		}
		return null;
	}
	@Override
	public Collection<?> getItemIds() {
		return messageMap.keySet();
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		Item mb = messageMap.get(itemId);
		return mb.getItemProperty(propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		
		return Object.class;
	}

	@Override
	public int size() {
		return messageMap.size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return messageMap.containsKey(itemId);
	}

	// Untested from here on:
	
	
	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		if(containsId(itemId)) {
			throw new UnsupportedOperationException("Can not add to container: Item: "+itemId+" already present!");
		}
		Message itt = src.instantiateFromDefinition();
		itt.setIndex((Integer)itemId);
		Item messageBridge = createItemFromMessage(itt);
		messageMap.put(itt.getIndex(), messageBridge);
		return messageBridge;
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		Message itt = src.instantiateFromDefinition();
		Item messageBridge = createItemFromMessage(itt);
		messageMap.put(itt.getIndex(), messageBridge);
		return messageBridge;
	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
//		Item mb = messageMap.get(itemId);
		messageMap.remove(itemId);
//		Message mm = mb.getSource();
//		if(mm!=null) {
//			src.removeMessage(mm);
//			return true;
//		}
//		return false;
		throw new UnsupportedOperationException("Cant do this just yet.");
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue)
			throws UnsupportedOperationException {
		System.err.println("ADDING CONTAINER PROPERTY: "+propertyId+" type: "+type+" def: "+defaultValue);
		Property p = new AdHocProperty(defaultValue, type);
		containerProperties.put(propertyId, p);
//		throw new UnsupportedOperationException("Can not add properties to container using VAADIN data model!");
		return true;
	}

	@Override
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		Property res = containerProperties.remove(propertyId);
		return res!=null;
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can not remove all messages? Didn't implement it. ");
	}

}
