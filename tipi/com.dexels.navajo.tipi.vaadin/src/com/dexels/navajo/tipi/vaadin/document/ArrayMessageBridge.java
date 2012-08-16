package com.dexels.navajo.tipi.vaadin.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Message;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class ArrayMessageBridge implements Container, Container.Sortable {

	private static final long serialVersionUID = -6228404246238997251L;
	private Message src;
	protected List<String> visibleColumns;
	protected List<String> editableColumns;
	
	private final Map<Object,Item> messageMap = new HashMap<Object,Item>();
	
	private final Map<Object, Property> containerProperties = new HashMap<Object, Property>();
	private final List<Message> sorted = new ArrayList<Message>();
	private final List<Integer> sortedIndexes = new ArrayList<Integer>();
	private Map<String, Integer> columnSizes;
	
	public ArrayMessageBridge(Message m, List<String> visibleColumns, List<String> editableColumns, Map<String, Integer> columnSizes) {
		initialize(m, visibleColumns, editableColumns,columnSizes);
	}

	public ArrayMessageBridge(Message m) {
		initialize(m);
	}

	public Integer getSizeForColumn(String name) {
		String[] split = name.split("@");
		String propertyName = split[0];
		if(columnSizes==null) {
			return null;
		}
		Integer integer = columnSizes.get(propertyName);
		return integer;
	}
	
	private void initialize(Message src) {
		this.src = src;
		if(src==null) {
			return;
		}
		if(!Message.MSG_TYPE_ARRAY.equals(src.getType())) {
			throw new UnsupportedOperationException("Can not bridge an non-array message to a container");
		}
		List<com.dexels.navajo.document.Message> messages = src.getAllMessages();
		for (Message message : messages) {
			sorted.add(message);
			sortedIndexes.add(message.getIndex());
		}
	}

	private void initialize(Message m, List<String> visibleColumns, List<String> editableColumns, Map<String, Integer> columnSizes) {
		this.visibleColumns = visibleColumns;		
		this.editableColumns = editableColumns;		
		this.columnSizes = columnSizes;
		initialize(m);
	}

	protected Item createItemFromMessage(Message message, List<String> editableColumns) {
		return new CompositeMessageBridge(message,editableColumns);
	}
	
	protected Item createItemFromMessage(Message message) {
		return new CompositeMessageBridge(message);
	}

	@Override
	public Item getItem(Object itemId) {
		Item item = messageMap.get(itemId);
		Integer id = (Integer) itemId;
		if(item==null) {
			Item pb = createItemFromMessage(src.getMessage(id),editableColumns);
			messageMap.put(id, pb);
			return pb;
		}
		return item;
	}

	protected void addVisibleColumn(String columnId) {
		visibleColumns.add(columnId);
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
		return sortedIndexes;
	}


	@Override
	public Class<?> getType(Object propertyId) {
		return Object.class;
	}

	@Override
	public int size() {
		return src.getArraySize();
	}

	@Override
	public boolean containsId(Object itemId) {
		Integer i = (Integer) itemId;
		if(i>=0 && i<src.getArraySize()) {
			return true;
		}
		return false;
	}

	// Untested from here on:
	
	
	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		if(containsId(itemId)) {
			throw new UnsupportedOperationException("Can not add to container: Item: "+itemId+" already present!");
		}
		Message itt = src.instantiateFromDefinition();
		itt.setIndex((Integer)itemId);
		Item messageBridge = createItemFromMessage(itt,editableColumns);
		messageMap.put(itt.getIndex(), messageBridge);
		return messageBridge;
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		Message itt = src.instantiateFromDefinition();
		Item messageBridge = createItemFromMessage(itt,editableColumns);
		messageMap.put(itt.getIndex(), messageBridge);
		return messageBridge;
	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		messageMap.remove(itemId);
		throw new UnsupportedOperationException("Cant do this just yet.");
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		Item mb = getItem(itemId);
		return mb.getItemProperty(propertyId);
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
	public Collection<?> getContainerPropertyIds() {
		Collection<Object> parentCollection = containerProperties.keySet();
		List<Object> columnHeaders = new ArrayList<Object>(parentCollection);
		if(visibleColumns!=null) {
			for (String column : visibleColumns) {

				StringTokenizer st = new StringTokenizer(column,"@");
				String propertyName = st.nextToken();

				columnHeaders.add(propertyName+"@value");
			}
			return columnHeaders;
		}
		return columnHeaders;
	}




//	@Override
//	public Collection<?> getContainerPropertyIds() {
//		System.err.println("QQQQQ: "+containerProperties);
//		if(visibleColumns!=null) {
//			return visibleColumns;
//		}
//		// If no column names have been defined, try do generate a list based on the example message
//		Message m = getExampleMessage();
//		
//		HashSet<Object> propertyIds = new HashSet<Object>();
//		if(m==null) {
//			return propertyIds;
//		}
//		List<com.dexels.navajo.document.Property> props = m.getAllProperties();
//		for (com.dexels.navajo.document.Property property : props) {
//			propertyIds.add(property.getName());
//		}
//		System.err.println("PPPPP: "+propertyIds);
//		return propertyIds;
//	}
	
	
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
		if(ii==null || itemId==null) {
			return false;
		}
		Integer itemInt = (Integer) itemId;
		return itemInt.intValue()==ii.intValue();
	}

	@Override
	public boolean isLastId(Object itemId) {
		Integer ii = sortedIndexes.get(sortedIndexes.size()-1);
		if(ii==null || itemId==null) {
			return false;
		}
		Integer itemInt = (Integer) itemId;
		return itemInt.intValue()==ii.intValue();
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
		for (Object b : propertyId) {
			System.err.println("Sort: "+b);
		}
		String first = (String) propertyId[0];
		final boolean firstIsAscending = ascending[0];
		String[] parts = first.split("@");
		final String propName = parts[0];
		
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
				if(v1 == null || v2 == null) {
					return 0;
				}
				if(v1 instanceof Comparable<?>) {
					Comparable<Object> c1 = (Comparable<Object>)v1;
					int compared = c1.compareTo(v2);
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
