/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
