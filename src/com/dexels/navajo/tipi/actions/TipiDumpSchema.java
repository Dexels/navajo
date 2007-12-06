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
public final class TipiDumpSchema {

	public static void main(String[] args) throws Exception {
		Map<String, XMLElement> allComponents = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allActions = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allEvents = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allValues = new HashMap<String, XMLElement>();

		File baseTipiDir = new File("tipi");
		parseFile("start.xml", baseTipiDir, allComponents, allActions, allEvents, allValues);

		// parseStream(TipiDumpSchema.class.getClassLoader().getResourceAsStream("com/dexels/navajo/tipi/classdef.xml"),
		// allComponents, allActions, allEvents, allValues);
		// parseStream(TipiDumpSchema.class.getClassLoader().getResourceAsStream("com/dexels/navajo/tipi/components/swingimpl/swingclassdef.xml"),
		// allComponents, allActions, allEvents, allValues);
		// parseStream(TipiDumpSchema.class.getClassLoader().getResourceAsStream("com/dexels/navajo/tipi/actions/actiondef.xml"),
		// allComponents, allActions, allEvents, allValues);
		// parseStream(TipiDumpSchema.class.getClassLoader().getResourceAsStream("com/dexels/navajo/tipi/swing/substance/substanceclassdef.xml"),
		// allComponents, allActions, allEvents, allValues);

		//		
		// parseFile("src/com/dexels/navajo/tipi/classdef.xml", allComponents,
		// allActions, allEvents, allValues);
		// parseFile("src/com/dexels/navajo/tipi/actions/actiondef.xml",
		// allComponents, allActions, allEvents, allValues);
		// parseFile("../NavajoSwingTipi/src/com/dexels/navajo/tipi/components/swingimpl/swingclassdef.xml",
		// allComponents, allActions,
		// allEvents, allValues);
		// parseFile("../NavajoReportingTipi/src/com/dexels/navajo/tipi/components/ext/extclassdef.xml",
		// allComponents, allActions, allEvents,
		// allValues);
		// parseFile("../TipiSubstance/src/com/dexels/navajo/tipi/swing/substance/substanceclassdef.xml",
		// allComponents, allActions,
		// allEvents, allValues);
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
		choice.setAttribute("minOccurs", "0");

		appendTipiIncludeTag(choice);
		appendTipiConfigTag(choice);
		appendClientConfigTag(choice);

		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();

			XMLElement e = createTipiClassElement(current, false, allComponents.get(current), allComponents, root);
			// System.err.println(">>>>>>>>>\n"+e);
			if (e != null) {
				root.addChild(e);
			}

		}
		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();
			XMLElement e = createTipiLayout(current, false, allComponents.get(current), allComponents, root);
			if (e != null) {
				root.addChild(e);
			}
		}

		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();
			XMLElement e = createTipiClassElement(current, true, allComponents.get(current), allComponents, root);
			if (e != null) {
				choice.addChild(e);
			}
		}
		XMLElement tt = addTag("xs:attribute", cmpl);
		tt.setAttribute("name", "errorhandler");

		FileWriter fw = new FileWriter( new File(baseTipiDir,"tipi.xsd"));
		root.write(fw);
		fw.flush();
		fw.close();

	}

	private static void parseTipi(String fileName) throws FileNotFoundException {
		FileReader fr = new FileReader(fileName);

	}

	private static void appendTipiIncludeTag(XMLElement choice) {
		XMLElement tipiInclude = addTag("xs:element", choice);
		tipiInclude.setAttribute("name", "tipi-include");
		XMLElement cmplTipiInclude = addTag("xs:complexType", tipiInclude);
		XMLElement locAttr = addTag("xs:attribute", cmplTipiInclude);
		locAttr.setAttribute("name", "location");
		locAttr.setAttribute("use", "required");
		XMLElement lazyAttr = addTag("xs:attribute", cmplTipiInclude);
		lazyAttr.setAttribute("name", "lazy");
		XMLElement compAttr = addTag("xs:attribute", cmplTipiInclude);
		compAttr.setAttribute("name", "definition");
	}

	private static void appendClientConfigTag(XMLElement choice) {
		XMLElement tipiInclude = addTag("xs:element", choice);
		tipiInclude.setAttribute("name", "client-config");
		XMLElement cmplTipiInclude = addTag("xs:complexType", tipiInclude);
		XMLElement nameAttr = addTag("xs:attribute", cmplTipiInclude);
		nameAttr.setAttribute("name", "name");
		XMLElement locAttr = addTag("xs:attribute", cmplTipiInclude);
		locAttr.setAttribute("name", "impl");
		XMLElement lazyAttr = addTag("xs:attribute", cmplTipiInclude);
		lazyAttr.setAttribute("name", "config");
		XMLElement compAttr = addTag("xs:attribute", cmplTipiInclude);
		compAttr.setAttribute("name", "secure");
		XMLElement locale = addTag("xs:attribute", cmplTipiInclude);
		locale.setAttribute("name", "locale");
		XMLElement keyAttr = addTag("xs:attribute", cmplTipiInclude);
		keyAttr.setAttribute("name", "keystore");
		XMLElement storeAttr = addTag("xs:attribute", cmplTipiInclude);
		storeAttr.setAttribute("name", "storepass");
		XMLElement server = addTag("xs:attribute", cmplTipiInclude);
		server.setAttribute("name", "server");
		XMLElement username = addTag("xs:attribute", cmplTipiInclude);
		username.setAttribute("name", "username");
		XMLElement password = addTag("xs:attribute", cmplTipiInclude);
		password.setAttribute("name", "password");
	}

	private static void appendTipiConfigTag(XMLElement choice) {
		XMLElement tipiInclude = addTag("xs:element", choice);
		tipiInclude.setAttribute("name", "tipi-config");
		XMLElement cmplTipiInclude = addTag("xs:complexType", tipiInclude);
		XMLElement locAttr = addTag("xs:attribute", cmplTipiInclude);
		locAttr.setAttribute("name", "maxtoserver");
		XMLElement lazyAttr = addTag("xs:attribute", cmplTipiInclude);
		lazyAttr.setAttribute("name", "poolsize");
	}

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
		XMLElement complexContent = addTag("xs:complexContent", complexType);
		XMLElement extension = addTag("xs:extension", complexContent);
		extension.setAttribute("base", "allActions");
		XMLElement xxx = addTag("xs:attribute", extension);
		xxx.setAttribute("name", "expression");
		xxx.setAttribute("type", "xs:string");
		xxx.setAttribute("use", "required");

		return block;
	}

	private static XMLElement createActions(Map<String, XMLElement> allActions) {
		XMLElement complexType = new CaseSensitiveXMLElement();
		complexType.setName("xs:complexType");
		complexType.setAttribute("name", "allActions");
		XMLElement choice = addTag("xs:choice", complexType);
		choice.setAttribute("maxOccurs", "unbounded");
		choice.setAttribute("minOccurs", "0");

		XMLElement bl = addTag("xs:element", choice);
		bl.setAttribute("ref", "block");

		for (Iterator<String> itt = allActions.keySet().iterator(); itt.hasNext();) {
			String current = itt.next();
			XMLElement currentAction = allActions.get(current);
			System.err.println("Current: " + currentAction);
			XMLElement element = addTag("xs:element", choice);
			element.setAttribute("name", current);
			Vector children = currentAction.getChildren();
			XMLElement params = addTag("xs:complexType", element);
			if (children.size() > 0) {
				for (int i = 0; i < children.size(); i++) {
					XMLElement currentParam = (XMLElement) children.get(i);
					XMLElement xsAttr = addTag("xs:attribute", params);
					xsAttr.setAttribute("name", currentParam.getStringAttribute("name"));
					if ("true".equals(currentParam.getStringAttribute("required"))) {
						xsAttr.setAttribute("use", "required");
					}
				}
			}
			if (current.equals("performTipiMethod")) {
				XMLElement xsAttr = addTag("xs:anyAttribute", params);
				xsAttr.setAttribute("processContents", "skip");
				// special case, we can not know what other params may be
				// present
			}

		}

		return complexType;
	}

	private static XMLElement createTipiComponent(String current, boolean isDefinition, XMLElement element,
			Map<String, XMLElement> allComponents, XMLElement root) {
		XMLElement result = new CaseSensitiveXMLElement();
		result.setName("xs:element");
		String type = element.getStringAttribute("type");

		if (type.equals("tipi") || type.equals("component")) {
			result.setAttribute("name", (isDefinition ? "d." : "c.") + current);
		}

		if (type.equals("layout")) {
			result.setAttribute("name", "l." + current);
		}

		XMLElement compl = addTag("xs:complexType", result);
		String childCount = element.getStringAttribute("childcount");
		boolean noChildren = false;
		XMLElement seq = null;
		seq = addTag("xs:choice", compl);
		seq.setAttribute("maxOccurs", "unbounded");
		seq.setAttribute("minOccurs", "0");

		if (childCount == null || "*".equals(childCount) || "null".equals(childCount)) {
		} else {

			if (Integer.parseInt(childCount) == 0) {
				noChildren = true;
			}
		}
		if ("true".equals(element.getAttribute("customstructure"))) {
			XMLElement any = addTag("xs:any", seq);
			any.setAttribute("processContents", "lax");
			any.setAttribute("minOccurs", "0");
			any.setAttribute("maxOccurs", "unbounded");
		}
		if (!noChildren) {
			for (Iterator<String> itt = allComponents.keySet().iterator(); itt.hasNext();) {
				String e = itt.next();
				XMLElement currentComponent = allComponents.get(e);
				if ("tipi".equals(currentComponent.getStringAttribute("type"))
						|| "component".equals(currentComponent.getStringAttribute("type"))) {
					if (seq != null) {
						XMLElement reff = addTag("xs:element", seq);
						reff.setAttribute("ref", ("c.") + e);
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
			idAttr.setAttribute("name", isDefinition ? "name" : "id");
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

							if ("true".equals(valueElement.getAttribute("required"))) {
								classAttr.setAttribute("use", "required");
							}
							String valueType = valueElement.getStringAttribute("type");
							classAttr.setAttribute("type", "xs:string");
							// }
						}
					}
				}
			}
		}
		return result;

	}

	private static XMLElement createTipiClassElement(String current, boolean isDefinition, XMLElement element,
			Map<String, XMLElement> allComponents, XMLElement root) {
		// component / c.window etc /definition
		System.err.println("element: " + element.getName() + " current: " + current);
		if ("tipi".equals(element.getStringAttribute("type")) || "component".equals(element.getStringAttribute("type"))) {
			return createTipiComponent(current, isDefinition, element, allComponents, root);
		}

		return null;
	}

	private static XMLElement createTipiLayout(String current, boolean isDefinition, XMLElement element,
			Map<String, XMLElement> allComponents, XMLElement root) {
		// component / c.window etc /definition

		if ("layout".equals(element.getStringAttribute("type"))) {
			return createTipiComponent(current, isDefinition, element, allComponents, root);
		}

		return null;
	}

	private static void parseFile(String fileName, File includeBase,Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions,
			Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(includeBase,fileName));
			System.err.println("Parsing: " + fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Error opening file:  "+fileName);
			e.printStackTrace();
			return;
		}
		try {
			parseStream(fis, includeBase,allComponents, allActions, allEvents, allValues);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseStream(InputStream is, File includeBase, Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions,
			Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues) throws FileNotFoundException, IOException {

		if (is == null) {
			throw new FileNotFoundException("Location not found");
		}
		InputStreamReader fr = new InputStreamReader(is);
		XMLElement xe = new CaseSensitiveXMLElement();

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
				String location = element.getStringAttribute("location");
				System.err.println("Include: "+element);
				try {
					parseStream(TipiDumpClass.class.getClassLoader().getResourceAsStream(location),includeBase, allComponents, allActions, allEvents, allValues);
				} catch (Exception e) {
					System.err.println("Including: " + location + " failed!");
					parseFile(location, includeBase, allComponents, allActions, allEvents, allValues);
					
				}
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
		Vector c = componentElement.getChildren();
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			XMLElement cc = (XMLElement) iter.next();
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