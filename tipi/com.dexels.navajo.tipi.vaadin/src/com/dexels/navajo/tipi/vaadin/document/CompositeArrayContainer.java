package com.dexels.navajo.tipi.vaadin.document;

import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.vaadin.data.Property;

public class CompositeArrayContainer extends ArrayMessageBridge {

	private static final long serialVersionUID = -3613726223172237777L;
	private CompositeMessageBridge definitionMessage;

	public CompositeArrayContainer(Message m) {
		super(m);
	}
	
	public CompositeArrayContainer(Message m, List<String> visibleColumns, List<String> editableColumns, Map<String, Integer> columnSizes) {
		super(m,visibleColumns,editableColumns,columnSizes);
		definitionMessage = new CompositeMessageBridge(getExampleMessage(),editableColumns);
	}
	
	public String getPropertyAspect(String aspect) {
		Property itemProperty = definitionMessage.getItemProperty(aspect);
		if(itemProperty==null) {
			return null;
		}
		return (String) itemProperty.getValue();
	}

}
