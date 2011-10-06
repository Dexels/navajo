package com.dexels.navajo.tipi.vaadin.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class ArrayMessageBridge implements Container, Container.Sortable {

	private static final long serialVersionUID = -6228404246238997251L;
	private final Message src;
	protected List<String> visibleColumns;
	
	private final Map<Object,Item> messageMap = new HashMap<Object,Item>();
	
	private final Map<Object, Property> containerProperties = new HashMap<Object, Property>();
	private final List<Message> sorted = new ArrayList<Message>();
	private final List<Integer> sortedIndexes = new ArrayList<Integer>();
	
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
			Integer integer = new Integer(i);
			messageMap.put(integer, pb);
			sorted.add(message);
			sortedIndexes.add(message.getIndex());
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

		System.err.println("IDLIST: "+sortedIndexes);
		return sortedIndexes;
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
		messageMap.remove(itemId);
		throw new UnsupportedOperationException("Cant do this just yet.");
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue)
			throws UnsupportedOperationException {
		Property p = new AdHocProperty(defaultValue, type);
		containerProperties.put(propertyId, p);
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

	@Override
	public Object nextItemId(Object itemId) {
		// TODO full scans = bad
		Integer id =(Integer)itemId;
		int index = sortedIndexes.indexOf(id);
		if(index>=0 && index < sortedIndexes.size() -1) {
			
			Integer ii = sortedIndexes.get(index+1);
			return ii;
		}
		
		return null;
	}

	@Override
	public Object prevItemId(Object itemId) {
		// TODO full scans = bad
		Integer id =(Integer)itemId;
		int index = sortedIndexes.indexOf(id);
		if(index>0 && index < sortedIndexes.size()) {
			Integer ii = sortedIndexes.get(index-1);
			return ii;
		}
		
		return null;
	}

	@Override
	public Object firstItemId() {
		if(sortedIndexes.isEmpty()) {
			return null;
		}
		return sortedIndexes.get(0);
	}

	@Override
	public Object lastItemId() {
		if(sortedIndexes.isEmpty()) {
			return null;
		}
		return sortedIndexes.get(sortedIndexes.size()-1);
	}

	@Override
	public boolean isFirstId(Object itemId) {
		Integer ii = sortedIndexes.get(0);
		return itemId==ii;
	}

	@Override
	public boolean isLastId(Object itemId) {
		Integer ii = sortedIndexes.get(sortedIndexes.size()-1);
		return itemId==ii;
	}

	@Override
	public Object addItemAfter(Object previousItemId)
			throws UnsupportedOperationException {
//		throw new UnsupportedOperationException("Not implemented");
		return null;
	}

	@Override
	public Item addItemAfter(Object previousItemId, Object newItemId)
			throws UnsupportedOperationException {
//		throw new UnsupportedOperationException("Not implemented");
		return null;
	}

	@Override
	public void sort(Object[] propertyId, boolean[] ascending) {
		System.err.println("SORTING========");
		for (Object b : propertyId) {
			System.err.println("Sort: "+b);
		}
		String first = (String) propertyId[0];
		final boolean firstIsAscending = ascending[0];
		String[] parts = first.split("@");
		final String propName = parts[0];
		System.err.println("Sorting propName: "+propName);
		
		Comparator<Message> comparator = new Comparator<Message>() {

			@SuppressWarnings("unchecked")
			@Override
			public int compare(Message o1, Message o2) {
				com.dexels.navajo.document.Property p1 = o1.getProperty(propName);
				com.dexels.navajo.document.Property p2 = o2.getProperty(propName);
				if(p1==null || p2==null) {
					return 0;
				}
				Object v1 = p1.getTypedValue();
				Object v2 = p2.getTypedValue();
				if(v1 instanceof Comparable<?>) {
					Comparable<Object> c1 = (Comparable<Object>)v1;
					int compared = c1.compareTo(v2);
					System.err.println("Actually compared: "+compared);
					if (firstIsAscending) {
						compared = -compared;
					}
					return compared;
				} else {
					System.err.println("Not comparable");
				}
				return 0;
			}
		};
		Collections.sort(sorted, comparator);
		sortedIndexes.clear();
		for (Message m : sorted) {
			sortedIndexes.add(m.getIndex());
		}
	}

	@Override
	public Collection<?> getSortableContainerPropertyIds() {
		// TODO maybe check for 'unsortable' types? (list, selection, binary)
		return getContainerPropertyIds();
	}

}
