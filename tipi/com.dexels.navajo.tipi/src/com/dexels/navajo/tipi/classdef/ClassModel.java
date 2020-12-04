/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.classdef;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;

public class ClassModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4733342095757720794L;

	private final Map<String, XMLElement> values = new HashMap<String, XMLElement>();

	private final Map<String, XMLElement> events = new HashMap<String, XMLElement>();
	private final Map<String, XMLElement> methods = new HashMap<String, XMLElement>();

	private final Map<String, StringBuffer> methodDescription = new HashMap<String, StringBuffer>();
	private final Map<String, StringBuffer> eventDescription = new HashMap<String, StringBuffer>();
	private final Map<String, StringBuffer> valueDescription = new HashMap<String, StringBuffer>();

	// current name of the component, changes as more definitions are added.
	// private final String name;

	private final Map<String, String> attributes = new HashMap<String, String>();

	// TODO Remove, no references to the originals should be needed
	private final Stack<XMLElement> definitionStack = new Stack<XMLElement>();
	private StringBuffer description = new StringBuffer();

	public ClassModel() {
		// this.name = name;
	}

	public void addDefinition(XMLElement input) {
		XMLElement element = input.copy();
		definitionStack.push(element);

		Iterator<String> ii = element.enumerateAttributeNames();
		while (ii.hasNext()) {
			String attribute = ii.next();
			// String old = attributes.get(attribute);
			String newVal = element.getStringAttribute(attribute);
			if (attribute.equals("name")) {
				attributes.put(attribute, newVal);
				// name = newVal;
			} else {
				// Handle conflicting attributes, I think replacing is the most
				// sensible
				// if(old!=null) {
				// newVal = old + ":"+newVal;
				// }
			}
			attributes.put(attribute, newVal);
		}
		List<XMLElement> list = element.getChildren();
		for (XMLElement child : list) {
			if (child.getName().equals("events")) {
				List<XMLElement> events = child.getChildren();
				for (XMLElement event : events) {
					addEvent(event);
				}
			}

			if (child.getName().equals("values")) {
				List<XMLElement> values = child.getChildren();
				for (XMLElement value : values) {
					addValue(value);
				}
			}

			if (child.getName().equals("methods")) {
				List<XMLElement> methods = child.getChildren();
				for (XMLElement method : methods) {
					addMethod(method);
				}
			}
			if (child.getName().equals("description")) {
				// remove the previous one
				description.delete(0, description.length());
				description.append(child.getContent().trim());
			}
		}
	}

	private void addMethod(XMLElement method) {
		String name = method.getStringAttribute("name");
		// XMLElement old = methods.get(name);

		XMLElement desc = method.getChildByTagName("description");
		if (desc != null) {
			StringBuffer sb = methodDescription.get(name);
			if (sb == null) {
				sb = new StringBuffer();
				methodDescription.put(name, sb);
			}
			sb.append(desc.getContent().trim());
			desc.getParent().removeChild(desc);
		}

		methods.put(name, method);
	}

	private void addValue(XMLElement value) {
		String name = value.getStringAttribute("name");
		XMLElement old = values.get(name);
		if (old != null) {
		}

		XMLElement desc = value.getChildByTagName("description");
		if (desc != null) {
			StringBuffer sb = valueDescription.get(name);
			if (sb == null) {
				sb = new StringBuffer();
				valueDescription.put(name, sb);
			}
			sb.append(desc.getContent().trim());
		}

		values.put(name, value);
	}

	private void addEvent(XMLElement event) {
		String name = event.getStringAttribute("name");
		// XMLElement old = events.get(name);

		XMLElement desc = event.getChildByTagName("description");

		if (desc != null) {
			StringBuffer sb = eventDescription.get(name);
			if (sb == null) {
				sb = new StringBuffer();
				eventDescription.put(name, sb);
			}
			sb.append(desc.getContent().trim());
			// desc.getParent().removeChild(desc);

		}

		events.put(name, event);
	}

	public XMLElement buildResult() {

		XMLElement result = new CaseSensitiveXMLElement();
		result.setName("tipiclass");
		if (description != null) {
			XMLElement desc = new CaseSensitiveXMLElement();
			desc.setName("description");
			desc.setContent(description.toString());
			result.addChild(desc);
		}

		XMLElement valuesElement = new CaseSensitiveXMLElement();
		valuesElement.setName("values");
		XMLElement methodsElement = new CaseSensitiveXMLElement();
		methodsElement.setName("methods");
		XMLElement eventsElement = new CaseSensitiveXMLElement();
		eventsElement.setName("events");

		result.addChild(valuesElement);
		result.addChild(methodsElement);
		result.addChild(eventsElement);

		Set<Entry<String, String>> s = attributes.entrySet();
		for (Entry<String, String> entry : s) {
			result.setAttribute(entry.getKey(), entry.getValue());
		}

		Set<Entry<String, XMLElement>> ls = values.entrySet();
		for (Entry<String, XMLElement> entry : ls) {
			XMLElement copy = entry.getValue().copy();
			valuesElement.addChild(copy);
			StringBuffer d = valueDescription.get(entry.getKey());
			if (d != null) {
				XMLElement desc = new CaseSensitiveXMLElement();
				desc.setName("description");
				desc.setContent(d.toString());
				copy.addChild(desc);
			}
		}

		Set<Entry<String, XMLElement>> eventL = events.entrySet();
		for (Entry<String, XMLElement> entry : eventL) {
			XMLElement copy = entry.getValue().copy();

			eventsElement.addChild(copy);
			StringBuffer d = eventDescription.get(entry.getKey());
			if (d != null) {
				XMLElement desc = new CaseSensitiveXMLElement();
				desc.setName("description");
				desc.setContent(d.toString());
				copy.addChild(desc);
			}

		}
		Set<Entry<String, XMLElement>> methodL = methods.entrySet();
		for (Entry<String, XMLElement> entry : methodL) {
			XMLElement copy = entry.getValue().copy();
			methodsElement.addChild(copy);
			StringBuffer d = methodDescription.get(entry.getKey());
			if (d != null) {
				XMLElement desc = new CaseSensitiveXMLElement();
				desc.setName("description");
				desc.setContent(d.toString());
				copy.addChild(desc);
			}
		}

		return result;
	}

}
