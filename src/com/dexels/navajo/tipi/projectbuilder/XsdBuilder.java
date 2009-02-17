package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.ant.projectbuilder.TipiBuildXsd;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;
import com.dexels.navajo.tipi.util.XMLParseException;

public class XsdBuilder {
	private final Map<String, XMLElement> allComponents = new HashMap<String, XMLElement>();
	private final Map<String, XMLElement> allActions = new HashMap<String, XMLElement>();
	private final Map<String, XMLElement> allEvents = new HashMap<String, XMLElement>();
	private final Map<String, XMLElement> allValues = new HashMap<String, XMLElement>();
	private final Map<String, XMLElement> allTypes = new HashMap<String, XMLElement>();
//	private final Map<String, XMLElement> allFunctions = new HashMap<String, XMLElement>();
	private final Map<String,List<XMLElement>> tipiParts = new HashMap<String, List<XMLElement>>();

	public void build(String repository, String extensions) {
		File xsd = new File("tipi/tipi.xsd");
		xsd.getParentFile().mkdirs();
		
		if(extensions==null || "".equals(extensions)) {
			
			throw new BuildException("No extensions defined ");
		}
		StringTokenizer st = new StringTokenizer(extensions,",");
		while(st.hasMoreTokens()) {
			String ext = st.nextToken();
			try {
				appendExtension(ext,repository);
//				appendClassDefElement(xx);
//				
//				parseProjectDefinition(project,projectURL, result);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		System.err.println("eleements: "+tipiParts.keySet());
		try {
			processMap("aap");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.err.println("All: "+allComponents);
		try {
			createXSD(allComponents, allActions, allEvents, allValues);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public  XMLElement appendExtension(String project,String repository) throws IOException {
		try {
			URL rep = new URL(repository);
			URL projectURL = new URL(rep,project+"/");
			URL extensionURL = new URL(projectURL,"definition.xml");

			XMLElement result = ClientActions.getXMLElement(extensionURL);

			parseProjectDefinition(project,projectURL, result);

			return result;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	private void createXSD(Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions, Map<String, XMLElement> allEvents,
			Map<String, XMLElement> allValues) throws IOException {
		// System.err.println("# of components: "+allComponents.size());
		XMLElement root = new CaseSensitiveXMLElement();
		root.setName("xs:schema");
		root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
		root.setAttribute("elementFormDefault", "qualified");

		XMLElement bb = createBlockType();
		root.addChild(bb);

		XMLElement cc = createActions(allActions, allComponents);
		root.addChild(cc);
// These two, are they necessary?
		cc = createAllComponents(allComponents);
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
		appendInlineTml(choice, root);

		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();
			XMLElement e = createTipiClassElement(current, false, allComponents.get(current), allComponents, root);
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

		File xsd = new File("tipi/tipi.xsd");
		
		OutputStream os =new FileOutputStream(xsd);
		OutputStreamWriter fw = new OutputStreamWriter(os);
		// FileWriter fw = new FileWriter( new
		// File(baseTipiDir,"tipi.xsd"));
		root.write(fw);
		fw.flush();
		fw.close();
		os.close();
	}

	private void processMap(String extension) throws FileNotFoundException, IOException {

		List<XMLElement> compo = tipiParts.get("tipiclass");
		for (XMLElement element : compo) {
			allComponents.put(element.getStringAttribute("name"), element);
			element.setAttribute("extension", extension);
			processClass(element, allEvents, allValues);
		}
		List<XMLElement> actions = tipiParts.get("tipiaction");
		for (XMLElement element : actions) {
			allActions.put(element.getStringAttribute("name"), element);
			element.setAttribute("extension", extension);
		}

		List<XMLElement> parsers = tipiParts.get("tipi-parser");
		for (XMLElement element : parsers) {
			allTypes.put(element.getStringAttribute("name"), element);
			element.setAttribute("extension", extension);
		}
		
//		List<XMLElement> c = xe.getChildren();
//		for (Iterator<XMLElement> iter = c.iterator(); iter.hasNext();) {
//			XMLElement element = iter.next();
//			if (element.getName().equals("tipiclass")) {
//				allComponents.put(element.getStringAttribute("name"), element);
//				element.setAttribute("extension", extension);
//				processClass(element, allEvents, allValues);
//				continue;
//			}
//			if (element.getName().equals("tipiaction")) {
//				allActions.put(element.getStringAttribute("name"), element);
//				element.setAttribute("extension", extension);
//				// Process actions
//				continue;
//			}
//			if (element.getName().equals("tipi-include")) {
//				String location = element.getStringAttribute("location");
//				throw new RuntimeException("BAM! Cant do includes bro: "+location);
//			}
//			if (element.getName().equals("tipi-parser")) {
//				allTypes.put(element.getStringAttribute("name"), element);
//				element.setAttribute("extension", extension);
//				continue;
//			}
//
//			if (element.getName().equals("function")) {
//				allFunctions.put(element.getStringAttribute("name"), element);
//				element.setAttribute("extension", extension);
//				continue;
//			}
//		}
	}

	
	private static void processClass(XMLElement componentElement, Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues) {
		List<XMLElement> c = componentElement.getChildren();
		for (Iterator<XMLElement> iter = c.iterator(); iter.hasNext();) {
			XMLElement cc = iter.next();
			if (cc.getName().equals("events")) {
				List<XMLElement> ccc = cc.getChildren();
				for (Iterator<XMLElement> iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement eventElement = iter2.next();
					if (eventElement.getName().equals("event")) {
						allEvents.put(eventElement.getStringAttribute("name"), eventElement);
					}
				}
			}
			if (cc.getName().equals("values")) {
				List<XMLElement> ccc = cc.getChildren();
				for (Iterator<XMLElement> iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement eventElement = iter2.next();
					if (eventElement.getName().equals("value")) {
						allValues.put(eventElement.getStringAttribute("name"), eventElement);
					}
				}
			}
		}
	}
	
	
	
	public void parseProjectDefinition(String project, URL projectURL, XMLElement result) throws MalformedURLException, IOException {
		List<XMLElement> includes = result.getElementsByTagName("include");
		for (XMLElement element : includes) {
			String path = element.getStringAttribute("path");
			XMLElement xx = ClientActions.getXMLElement(new URL(projectURL,"includes/"+path));
			// beware of missing function.xml
			//System.err.println("ELEMENT: "+xx);
			if(xx!=null) {
				appendClassDefElement(xx);
			}
		}
	}
	
	private void appendClassDefElement(XMLElement xx) {
		List<XMLElement> cc = xx.getChildren();
		for (XMLElement element : cc) {
			List<XMLElement> elts = tipiParts.get(element.getName());
			if(elts==null) {
				elts = new LinkedList<XMLElement>();
				tipiParts.put(element.getName(), elts);
			}
			elts.add(element);
		}

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
		
		xxx = addTag("xs:attribute", extension);
		xxx.setAttribute("name", "condition");
		xxx.setAttribute("type", "xs:string");
		xxx.setAttribute("use", "required");

		return block;
	}

	private static XMLElement createAllComponents(Map<String, XMLElement> allComponents) {
		XMLElement complexType = new CaseSensitiveXMLElement();
		complexType.setName("xs:complexType");
		complexType.setAttribute("name", "allComponents");
		XMLElement choice = addTag("xs:choice", complexType);
		choice.setAttribute("maxOccurs", "unbounded");
		choice.setAttribute("minOccurs", "0");
		for (String current : allComponents.keySet()) {
			XMLElement bl = addTag("xs:element", choice);
			bl.setAttribute("ref", "c."+current);
		}
		return complexType;
	}
	
//	private static XMLElement createAllComponentDefinitions(Map<String, XMLElement> allComponents) {
//		XMLElement complexType = new CaseSensitiveXMLElement();
//		complexType.setName("xs:complexType");
//		complexType.setAttribute("name", "allComponentDefinitions");
//		XMLElement choice = addTag("xs:choice", complexType);
//		choice.setAttribute("maxOccurs", "unbounded");
//		choice.setAttribute("minOccurs", "0");
//		for (String current : allComponents.keySet()) {
//			XMLElement bl = addTag("xs:element", choice);
//			bl.setAttribute("ref", "d."+current);
//		}
//		return complexType;
//	}
	
	private static XMLElement createActions(Map<String, XMLElement> allActions, Map<String, XMLElement> allComponents) {
		XMLElement complexType = new CaseSensitiveXMLElement();
		complexType.setName("xs:complexType");
		complexType.setAttribute("name", "allActions");
//	      <xs:attribute  name="condition" type="xs:string"/>
		
//		XMLElement condition = addTag("xs:attribute", complexType);
//		condition.setAttribute("name", "condition");
//		condition.setAttribute("type", "xs:string");
//		
		
		XMLElement choice = addTag("xs:choice", complexType);
		choice.setAttribute("maxOccurs", "unbounded");
		choice.setAttribute("minOccurs", "0");

		XMLElement bl = addTag("xs:element", choice);
		bl.setAttribute("ref", "block");

		for (Iterator<String> itt = allActions.keySet().iterator(); itt.hasNext();) {
			String current = itt.next();
			XMLElement currentAction = allActions.get(current);
			XMLElement element = addTag("xs:element", choice);
			element.setAttribute("name", current);
			
					
			XMLElement params = addTag("xs:complexType", element);
			XMLElement xxx = addTag("xs:attribute", params);
			xxx.setAttribute("name", "condition");
			xxx.setAttribute("type", "xs:string");

			List<XMLElement> children = currentAction.getChildren();
			
			if (!children.isEmpty()) {
				for (int i = 0; i < children.size(); i++) {
					XMLElement currentParam = children.get(i);
					if(!currentParam.getName().equals("param")) {
						continue;
					}
					XMLElement xsAttr = addTag("xs:attribute", params);
					
					String currentName = currentParam.getStringAttribute("name");

					xsAttr.setAttribute("name", currentName);
					if ("true".equals(currentParam.getStringAttribute("required"))) {
						xsAttr.setAttribute("use", "required");
					} else {
						String currentType = currentParam.getStringAttribute("type");
						if (currentType != null && !"object".equals(currentType)) {
							xsAttr.setAttribute("default", "[" + currentType + "]");
						}
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
		for (Iterator<String> itt = allComponents.keySet().iterator(); itt.hasNext();) {
			String current = itt.next();
			XMLElement xx = allComponents.get(current);
			List<XMLElement> l = xx.getElementsByTagName("methods");
			if (l != null && !l.isEmpty()) {
				if (l.size() > 1) {
					throw new IllegalStateException("Only one methods tag allowed");
				}
				XMLElement first = l.get(0);
				List<XMLElement> method = first.getElementsByTagName("method");
				for (XMLElement currentMethod : method) {
					XMLElement element = addTag("xs:element", choice);
					element.setAttribute("name", current + "." + currentMethod.getStringAttribute("name"));
					List<XMLElement> children = currentMethod.getElementsByTagName("param");
					XMLElement params = addTag("xs:complexType", element);
					XMLElement pathPrm = addTag("xs:attribute", params);
					pathPrm.setAttribute("name", "path");
					pathPrm.setAttribute("use", "required");
//					pathPrm.setAttribute("default", "{component:/.}");

					if (!children.isEmpty()) {
						for (int i = 0; i < children.size(); i++) {
							XMLElement currentParam = children.get(i);
							XMLElement xsAttr = addTag("xs:attribute", params);
							String paramName = currentParam.getStringAttribute("name");
							if (paramName != null) {
								xsAttr.setAttribute("name", paramName);
							}

							if ("true".equals(currentParam.getStringAttribute("required"))) {
								xsAttr.setAttribute("use", "required");
							} else {
								String currentType = currentParam.getStringAttribute("type");
								if (currentType != null && !"object".equals(currentType)) {
									xsAttr.setAttribute("default", "[" + currentType + "]");
								}
							}

						}
					}
				}
			}
			List<XMLElement> values = xx.getElementsByTagName("values");
			if(values==null || values.isEmpty()) {
				// No values tag at all, abort this avenue
				continue;
			}
			XMLElement first = values.get(0);
			List<XMLElement> value = first.getElementsByTagName("value");
			if(value.isEmpty()) {
				// No values tag at all, abort this avenue
				continue;
			}
			boolean allOut = true;
			for (XMLElement valueElement : value) {
				String direction = valueElement.getStringAttribute("direction");
				if(!"out".equals(direction)) {
					allOut = false;
				}
			}
			if(allOut) {
				// only out properties, so this entire setting is not allowed
				continue;
			}
			XMLElement element = addTag("xs:element", choice);
			element.setAttribute("name", current + ".attribute");
			XMLElement params = addTag("xs:complexType", element);
			XMLElement pathPrm = addTag("xs:attribute", params);
			pathPrm.setAttribute("name", "path");
			pathPrm.setAttribute("use", "required");
//			pathPrm.setAttribute("default", "{component:/.}");

			for (XMLElement valueElement : value) {
				String direction = valueElement.getStringAttribute("direction");
				if("out".equals(direction)) {
					// Out property, so no setting allowed.
					continue;
				}
				XMLElement param = addTag("xs:attribute", params);
				param.setAttribute("name", valueElement.getStringAttribute("name"));
				param.setAttribute("use", "optional");
				param.setAttribute("default", "["+valueElement.getStringAttribute("name")+"]");
	
			}
			
		}
		for (Iterator<String> itt = allComponents.keySet().iterator(); itt.hasNext();) {
			String current = itt.next();
			XMLElement xx = allComponents.get(current);
			XMLElement element = addTag("xs:element", choice);
			element.setAttribute("name", current + ".instantiate");

			XMLElement params = addTag("xs:complexType", element);
			XMLElement pathPrm = addTag("xs:attribute", params);
			pathPrm.setAttribute("name", "location");
			pathPrm.setAttribute("use", "required");
//			pathPrm.setAttribute("default", "{component:/.}");
			pathPrm.setAttribute("type", "xs:string");

			pathPrm = addTag("xs:attribute", params);
			pathPrm.setAttribute("name", "name");
			pathPrm.setAttribute("use", "required");
//			pathPrm.setAttribute("default", "[definition name]");
			pathPrm.setAttribute("type", "xs:string");

			List<XMLElement> l = xx.getElementsByTagName("values");
			if (l != null && !l.isEmpty()) {
				if (l.size() > 1) {
					throw new IllegalStateException("Only one methods tag allowed");
				}
				XMLElement first = l.get(0);
				List<XMLElement> valueTag = first.getElementsByTagName("value");

				for (XMLElement currentMethod : valueTag) {
					// List<XMLElement> children = currentMethod.getChildren();
					String direction = currentMethod.getStringAttribute("direction");
					if ("out".equals(direction)) {
						// read only, so it can not be supplied on an
						// instantiate
						continue;
					}
					String type = currentMethod.getStringAttribute("type");
					XMLElement xsAttr = addTag("xs:attribute", params);
					xsAttr.setAttribute("name", currentMethod.getStringAttribute("name"));
					xsAttr.setAttribute("type", "xs:string");
					xsAttr.setAttribute("default", "[" + type + "]");
					xsAttr.setAttribute("name", currentMethod.getStringAttribute("name"));

					// if (!children.isEmpty()) {
					// for (int i = 0; i < children.size(); i++) {
					// XMLElement currentParam = children.get(i);
					// System.err.println("CCC: "+currentParam);
					// }
					// }
				}
			}
		}
		// <method name="showEditDialog">
		// <param name="title" type="string" value="'Edit'" />
		// </method>

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

		List<XMLElement> ev = element.getChildren();

		for (Iterator<XMLElement> iter = ev.iterator(); iter.hasNext();) {
			XMLElement cc = iter.next();
			if (cc.getName().equals("events")) {
				List<XMLElement> ccc = cc.getChildren();
				for (Iterator<XMLElement> iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement eventElement = iter2.next();
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

		// Old skool tags:
		if ("component".equals(type) || "tipi".equals(type)) {
			XMLElement idAttr = addTag("xs:attribute", compl);
			idAttr.setAttribute("name", isDefinition ? "name" : "id");
			idAttr.setAttribute("type", "xs:string");

			XMLElement contraintAttr = addTag("xs:attribute", compl);
			contraintAttr.setAttribute("name", "constraint");
			contraintAttr.setAttribute("type", "xs:string");
		}

		// Even older skool tags:
		if ("tipi".equals(type)) {
			XMLElement serviceAttr = addTag("xs:attribute", compl);
			serviceAttr.setAttribute("name", "service");
			serviceAttr.setAttribute("type", "xs:string");
		}

		// Values:
		List<XMLElement> c = element.getChildren();
		for (Iterator<XMLElement> iter = c.iterator(); iter.hasNext();) {
			XMLElement cc = iter.next();
			if (cc.getName().equals("values")) {
				List<XMLElement> ccc = cc.getChildren();
				for (Iterator<XMLElement> iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement valueElement = iter2.next();
					if (valueElement.getName().equals("value")) {
						if ("in".equals(valueElement.getStringAttribute("direction"))
								|| "inout".equals(valueElement.getStringAttribute("direction"))) {
							XMLElement classAttr = addTag("xs:attribute", compl);
							classAttr.setAttribute("name", valueElement.getAttribute("name"));

							if ("true".equals(valueElement.getAttribute("required"))) {
								classAttr.setAttribute("use", "required");
							} else {
								String valueType = valueElement.getStringAttribute("type");
								classAttr.setAttribute("default", "[" + valueType + "]");
							}


							// String valueType =
							// valueElement.getStringAttribute("type");
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
		// System.err.println("element: " + element.getName() + " current: " +
		// current);
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

	private static void appendInlineTml(XMLElement choice, XMLElement schema) {
		try {
			XMLElement tml = new CaseSensitiveXMLElement();
			tml.setName("xs:element");
			tml.setAttribute("ref", "tml");
			XMLElement tmlX = new CaseSensitiveXMLElement();
			InputStream in = TipiBuildXsd.class.getResourceAsStream("xtml.xsd");
			tmlX.parseFromReader(new InputStreamReader(in));
			in.close();

			List<XMLElement> ll = tmlX.getChildren();
			for (XMLElement element : ll) {
				schema.addChild(element);
			}

			choice.addChild(tml);

		} catch (XMLParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
