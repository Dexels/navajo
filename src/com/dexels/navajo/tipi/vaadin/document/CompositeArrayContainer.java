package com.dexels.navajo.tipi.vaadin.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Message;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class CompositeArrayContainer extends ArrayMessageBridge {

	private static final long serialVersionUID = -3613726223172237777L;
	private CompositeMessageBridge definitionMessage;


	public CompositeArrayContainer(Message m, List<String> visibleColumns, List<String> editableColumns, Map<String, Integer> columnSizes) {
		super(m,visibleColumns,editableColumns,columnSizes);
		definitionMessage = new CompositeMessageBridge(getExampleMessage(),editableColumns);

		
//		definitionMessage = new CompositeMessageBridge(getExampleMessage());

//		for (String id : visibleColumns) {
//			addVisibleColumn(id+"@value");
//		}
	
	}
	

	
	public String getPropertyAspect(String aspect) {
		Property itemProperty = definitionMessage.getItemProperty(aspect);
		if(itemProperty==null) {
			return null;
		}
		return (String) itemProperty.getValue();
	}

	protected Item createItemFromMessage(Message message,List<String> editableColumns) {
		return new CompositeMessageBridge(message,editableColumns);
	}
	protected Item createItemFromMessage(Message message) {
		return new CompositeMessageBridge(message,null);
	}
	
	@Override
	public Collection<?> getContainerPropertyIds() {
		// If no column names have been defined, try do generate a list based on the example message
		
		List<Object> columnHeaders = new ArrayList<Object>();
		
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


}
