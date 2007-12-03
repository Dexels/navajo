package com.dexels.navajo.tipi.actions;

import java.io.*;
import java.util.*;

import org.w3c.dom.events.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public final class TipiDumpSchema extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			Message mm = NavajoFactory.getInstance().createMessage(n, "Class", Message.MSG_TYPE_ARRAY);
			n.addMessage(mm);
			Map m = myContext.getTipiClassDefMap();
			for (Iterator iter = m.keySet().iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				XMLElement def = (XMLElement) m.get(element);
				dumpDef(mm, element, def);
			}
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// <tipiaction name="setStorageInstanceId" class="TipiSetStorageInstanceId"
	// package="com.dexels.navajo.tipi.actions">
	// <param name="id" type="string" required="true"/>
	// </tipiaction>

	private void dumpDef(Message msg, String element, XMLElement def) {
		// TODO Auto-generated method stub
		// Message elt = NavajoFactory.getInstance().createMessage(n, "Class",
		// Message.MSG_TYPE_ARRAY_ELEMENT);
		if (def.getName().equals("tipiaction")) {
			System.err.println("Action: " + def.getStringAttribute("name"));

		}
	}

	public static void main(String[] args) throws Exception {
		Map<String, XMLElement> allComponents = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allActions = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allEvents = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allValues = new HashMap<String, XMLElement>();

		parseFile("src/com/dexels/navajo/tipi/classdef.xml", allComponents, allActions, allEvents, allValues);
		parseFile("src/com/dexels/navajo/tipi/actions/actiondef.xml", allComponents, allActions, allEvents, allValues);
		parseFile("../NavajoSwingTipi/src/com/dexels/navajo/tipi/components/swingimpl/swingclassdef.xml", allComponents, allActions,
				allEvents, allValues);

		// <xs:element name="tid">
		// <xs:complexType>
		// <xs:choice maxOccurs="unbounded">
		// <xs:element ref="tipi-include" />
		// <xs:element ref="definition" />
		// <xs:element ref="component" />
		// <xs:element ref="component-instance" />
		// <xs:element ref="d.window" />
		//
		// </xs:choice>
		// <xs:attribute name="errorhandler" type="xs:string"
		// use="required" />
		// </xs:complexType>
		// </xs:element>
		XMLElement root = new CaseSensitiveXMLElement();
		root.setName("xs:schema");
		root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
		root.setAttribute("elementFormDefault", "qualified");

		XMLElement bb = createBlockType();
		root.addChild(bb);
	
		XMLElement cc = createActions(allActions);
		root.addChild(cc);

		
		XMLElement rootTidElement = addTag("xs:element", root);
		rootTidElement.setAttribute("name", "tid");
		
		
		
		XMLElement cmpl = addTag("xs:complexType", rootTidElement);
		XMLElement choice = addTag("xs:choice", cmpl);
		choice.setAttribute("maxOccurs", "unbounded");

		
	
		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();

			XMLElement e = createTipiClassElement(current, false, allComponents.get(current), allEvents, allValues, allComponents,root);
			// System.err.println(">>>>>>>>>\n"+e);
			if (e != null) {
				root.addChild(e);
			}

		}
		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();

			XMLElement e = createTipiLayout(current, false, allComponents.get(current), allEvents, allValues, allComponents,root);
			// System.err.println(">>>>>>>>>\n"+e);
			if (e != null) {
				root.addChild(e);
			}

		}

		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();
			// XMLElement xx = addTag("xs:element", choice);
			// xx.setAttribute("ref", "c." + current);
			XMLElement e = createTipiClassElement(current, false, allComponents.get(current), allEvents, allValues, allComponents,root);
			// System.err.println(">>>>>>>>>\n"+e);
			if (e != null) {
				choice.addChild(e);
			}
		}

		FileWriter fw = new FileWriter("tipi.xsd");
		root.write(fw);
		fw.flush();
		fw.close();
		// XMLElement e =
		// createComponentElement("definition",false,allComponents.get(current),allEvents,allValues,allComponents);

		System.err.println("::: " + allEvents.keySet());
		System.err.println("::: " + allComponents.keySet());
		System.err.println("::: " + allActions.keySet());
		System.err.println("::: " + allValues.keySet());
	}

	// <xs:element name="component-instance">
	// <xs:complexType>
	// <xs:sequence minOccurs="0" maxOccurs="unbounded">
	// <xs:element ref="_event" minOccurs="0" />
	// <xs:element ref="component-instance" minOccurs="0" />
	// </xs:sequence>
	// <xs:attribute name="name" type="xs:string" />
	// <xs:attribute name="class">
	// <xs:attribute name="class">
	// <xs:simpleType>
	// <xs:restriction base="xs:NMTOKEN">
	// <xs:enumeration value="button" />
	// <xs:enumeration value="browser" />
	// <xs:enumeration value="copy" />
	// </xs:restriction>
	// </xs:simpleType>
	// </xs:attribute>

	private static XMLElement addTag(String tagName, XMLElement parent) {
		XMLElement result = new CaseSensitiveXMLElement();
		result.setName(tagName);
		parent.addChild(result);
		return result;
	}

	private static XMLElement createBlockType() {
		XMLElement block = new CaseSensitiveXMLElement();
		block.setName("xs:element");
		block.setAttribute("name", "block");
		XMLElement complexType = addTag("xs:complexType", block);
		XMLElement xxx = addTag("xs:attribute", complexType);
		xxx.setAttribute("name", "expression");
		xxx.setAttribute("type", "xs:string");
		xxx.setAttribute("use", "required");
	
		return block;
	}

	private static XMLElement createActions(Map<String, XMLElement> allActions) {
		XMLElement complexType = new CaseSensitiveXMLElement();
		complexType.setName("xs:complexType");
		complexType.setAttribute("name", "allActions");
		XMLElement choice = addTag("xs:choice",complexType);
		choice.setAttribute("maxOccurs", "unbounded");
		
		XMLElement bl = addTag("xs:element", choice);
		bl.setAttribute("ref","block");

		for (Iterator<String> itt = allActions.keySet().iterator(); itt.hasNext();) {
			String current = itt.next();
			XMLElement currentAction = allActions.get(current);
			System.err.println("Current: "+currentAction);
			XMLElement element = addTag("xs:element", choice);
			element.setAttribute("name",current);
			Vector children = currentAction.getChildren();
			if(children.size()>0) {
				XMLElement params = addTag("xs:complexType", element);
				for (int i = 0; i < children.size(); i++) {
					XMLElement currentParam = (XMLElement)children.get(i);
					XMLElement xsAttr = addTag("xs:attribute", params);
					xsAttr.setAttribute("name", currentParam.getStringAttribute("name"));
					if("true".equals(currentParam.getStringAttribute("required"))) {
						xsAttr.setAttribute("use", "required");
					}
				}
			}
//			<xs:element name="product">
//			  <xs:complexType>
//			    <xs:attribute name="prodid" type="xs:positiveInteger"/>
//			  </xs:complexType>
//			</xs:element>
			
		}	
		
	
		return complexType;
	}

	
	private static XMLElement createTipiComponent(String current, boolean isDefinition, XMLElement element,
			Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues, Map<String, XMLElement> allComponents,XMLElement root) {
		XMLElement result = new CaseSensitiveXMLElement();
		result.setName("xs:element");
		String type = element.getStringAttribute("type");

		if (type.equals("tipi") || type.equals("component")) {
			result.setAttribute("name", "c." + current);
		}

		if (type.equals("layout")) {
			result.setAttribute("name", "l." + current);
		}

		XMLElement compl = addTag("xs:complexType", result);
		
		
		// XMLElement ref1 = addTag("xs:element", seq);
		// ref1.setAttribute("ref", "component");
		// XMLElement ref2 = addTag("xs:element", seq);
		// ref2.setAttribute("ref", "component-instance");
		String childCount = element.getStringAttribute("childcount");
		//System.err.println("type: " + element.getStringAttribute("type"));
		boolean noChildren = false;
		XMLElement seq = null;

		if (childCount == null) {
		//	System.err.println("unknown: " + element.getStringAttribute("name"));
			// seq.setAttribute("maxOccurs","unbounded");
		} else {
			if ("*".equals(childCount)) {
			//	System.err.println("infinity: " + element.getStringAttribute("name"));
				seq = addTag("xs:choice", compl);
				seq.setAttribute("maxOccurs", "unbounded");
			} else {
				//System.err.println("Count: " + childCount + " - " + element.getStringAttribute("name"));

				if (Integer.parseInt(childCount) == 0) {
					noChildren = true;
				} else {
					seq = addTag("xs:choice", compl);
					seq.setAttribute("maxOccurs", "" + childCount);
				}
			}
		}
		// System.err.println("Childcount: "+childCount);
		if (!noChildren) {
			for (Iterator<String> itt = allComponents.keySet().iterator(); itt.hasNext();) {
				String e = itt.next();
				XMLElement currentComponent = allComponents.get(e);
				if ("tipi".equals(currentComponent.getStringAttribute("type"))
						|| "component".equals(currentComponent.getStringAttribute("type"))) {
					if (seq != null) {
						XMLElement reff = addTag("xs:element", seq);
						reff.setAttribute("ref", "c." + e);
					}
				}
			}
			if ("true".equals(element.getStringAttribute("layoutmanager"))) {
				for (Iterator<String> itt = allComponents.keySet().iterator(); itt.hasNext();) {
					String e = itt.next();
					XMLElement currentComponent = allComponents.get(e);
					if ("layout".equals(currentComponent.getStringAttribute("type"))) {
						if (seq != null) {
							XMLElement reff = addTag("xs:element", seq);
							reff.setAttribute("ref", "l." + e);
						}
					}
				}
			}
		}

		// Events:

		Vector ev = element.getChildren();

		for (Iterator iter = ev.iterator(); iter.hasNext();) {
			XMLElement cc = (XMLElement) iter.next();
			if (cc.getName().equals("events")) {
				Vector ccc = cc.getChildren();
				for (Iterator iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement eventElement = (XMLElement) iter2.next();
					if (eventElement.getName().equals("event")) {
						if (seq == null) {
							seq = addTag("xs:choice", compl);
							seq.setAttribute("maxOccurs", "unbounded");
							seq.setAttribute("minOccurs", "0");
						}
						XMLElement classAttr = addTag("xs:element", seq);
						classAttr.setAttribute("name", eventElement.getAttribute("name"));
						classAttr.setAttribute("type", "allActions");
					}
				}
			}
		}

		if ("component".equals(type) || "tipi".equals(type)) {
			XMLElement idAttr = addTag("xs:attribute", compl);
			idAttr.setAttribute("name", "id");
			idAttr.setAttribute("type", "xs:string");
			XMLElement contraintAttr = addTag("xs:attribute", compl);
			contraintAttr.setAttribute("name", "constraint");
			contraintAttr.setAttribute("type", "xs:string");
		}

		if ("tipi".equals(type)) {
			XMLElement serviceAttr = addTag("xs:attribute", compl);
			serviceAttr.setAttribute("name", "service");
			serviceAttr.setAttribute("type", "xs:string");
		}

		// Values:
		Vector c = element.getChildren();
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			XMLElement cc = (XMLElement) iter.next();
			if (cc.getName().equals("values")) {
				Vector ccc = cc.getChildren();
				for (Iterator iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement valueElement = (XMLElement) iter2.next();
					if (valueElement.getName().equals("value")) {
						if ("in".equals(valueElement.getStringAttribute("direction"))
								|| "inout".equals(valueElement.getStringAttribute("direction"))) {
							XMLElement classAttr = addTag("xs:attribute", compl);
							classAttr.setAttribute("name", valueElement.getAttribute("name"));

							// if(eventElement)
							if ("true".equals(valueElement.getAttribute("required"))) {
								classAttr.setAttribute("use", "required");
							}
							// select the type:
							String valueType = valueElement.getStringAttribute("type");

							if ("integer".equals(valueType)) {
								classAttr.setAttribute("type", "xs:integer");
							} else if ("date".equals(valueType)) {
								classAttr.setAttribute("type", "xs:date");
							} else if ("boolean".equals(valueType)) {
								classAttr.setAttribute("type", "xs:boolean");
							} else {
								classAttr.setAttribute("type", "xs:string");
							}
						}
					}
				}
			}
		}

		// for medium school tipi:
		// XMLElement classAttr = addTag("xs:attribute", compl);
		// classAttr.setAttribute("name", "class");
		// classAttr.setAttribute("use", "required");
		// XMLElement simpl = addTag("xs:simpleType", classAttr);
		// XMLElement restr = addTag("xs:restriction", simpl);
		// restr.setAttribute("base", "xs:NMTOKEN");
		// for (Iterator<String> itt = allComponents.keySet().iterator();
		// itt.hasNext();) {
		// String e = itt.next();
		// XMLElement reff = addTag("xs:enumeration", restr);
		// reff.setAttribute("value", e);
		// }
		// classAttr.setAttribute("type", "xs:string");
		// System.err.println("" + result);
		return result;

	}

	private static XMLElement createTipiClassElement(String current, boolean isDefinition, XMLElement element,
			Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues, Map<String, XMLElement> allComponents,XMLElement root) {
		// component / c.window etc /definition

		if ("tipi".equals(element.getStringAttribute("type")) || "component".equals(element.getStringAttribute("type"))) {
			return createTipiComponent(current, isDefinition, element, allEvents, allValues, allComponents,root);
		}

		return null;
	}

	private static XMLElement createTipiLayout(String current, boolean isDefinition, XMLElement element, Map<String, XMLElement> allEvents,
			Map<String, XMLElement> allValues, Map<String, XMLElement> allComponents,XMLElement root) {
		// component / c.window etc /definition

		if ("layout".equals(element.getStringAttribute("type"))) {
			return createTipiComponent(current, isDefinition, element, allEvents, allValues, allComponents,root);
		}

		return null;
	}

	private static void parseFile(String fileName, Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions,
			Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(fileName);
		XMLElement xe = new CaseSensitiveXMLElement();

		// fr = new
		// FileReader("../NavajoSwingTipi/src/com/dexels/navajo/tipi/swingimpl/swingclassdef.xml");
		xe.parseFromReader(fr);
		Vector c = xe.getChildren();
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			XMLElement element = (XMLElement) iter.next();
			if (element.getName().equals("tipiclass")) {
				allComponents.put(element.getStringAttribute("name"), element);
				processClass(element, allEvents, allValues);
				continue;
			}
			if (element.getName().equals("tipiaction")) {
				allActions.put(element.getStringAttribute("name"), element);
				// Process actions
				continue;
			}
			if (element.getName().equals("tipi-include")) {
				continue;
			}
			if (element.getName().equals("tipi-parser")) {
				continue;
			}
			System.err.println("Other: " + element.getName());
		}
		fr.close();
	}

	private static void processClass(XMLElement componentElement, Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues) {
		// System.err.println("Element: "+element.getName()+" :
		// "+element.getStringAttribute("class"));
		Vector c = componentElement.getChildren();
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			XMLElement cc = (XMLElement) iter.next();
			// System.err.println("Sub: "+cc.getName());
			if (cc.getName().equals("events")) {
				Vector ccc = cc.getChildren();
				for (Iterator iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement eventElement = (XMLElement) iter2.next();
					if (eventElement.getName().equals("event")) {
						allEvents.put(eventElement.getStringAttribute("name"), eventElement);
					}
				}
			}
			if (cc.getName().equals("values")) {
				Vector ccc = cc.getChildren();
				for (Iterator iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement eventElement = (XMLElement) iter2.next();
					if (eventElement.getName().equals("value")) {
						allValues.put(eventElement.getStringAttribute("name"), eventElement);
					}
				}
			}
		}
	}

}