/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.xml;

import java.util.Map;

public class XMLEvent {
	
	private final XmlEventTypes type;
	private final String text;
	private final Map<String, String> attributes;
	
	public static enum XmlEventTypes {
		TEXT,START_ELEMENT,END_ELEMENT,START_DOCUMENT,END_DOCUMENT
	}
	
	public XMLEvent(XmlEventTypes type, String text, Map<String,String> attributes) {
		this.type = type;
		this.text = text;
		this.attributes = attributes;
	}

	public XmlEventTypes getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(type.toString());
		if(text!=null) {
			sb.append(" ");
			sb.append(text);
		}
		if(attributes!=null) {
			sb.append(" ");
			sb.append(attributes);
		}
		return sb.toString();

	}
	
}
