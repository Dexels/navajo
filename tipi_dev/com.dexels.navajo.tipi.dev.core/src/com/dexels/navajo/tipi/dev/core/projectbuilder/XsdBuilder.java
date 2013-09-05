package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.dev.core.util.XMLElement;
import com.dexels.navajo.tipi.dev.core.util.XMLParseException;

public class XsdBuilder {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(XsdBuilder.class);
	
	private final Map<String, XMLElement> allComponents = new TreeMap<String, XMLElement>();
	private final Map<String, XMLElement> allActions = new TreeMap<String, XMLElement>();
	private final Map<String, XMLElement> allEvents = new TreeMap<String, XMLElement>();
	private final Map<String, XMLElement> allValues = new TreeMap<String, XMLElement>();
	private final Map<String, XMLElement> allTypes = new TreeMap<String, XMLElement>();
//	private final Map<String, XMLElement> allFunctions = new HashMap<String, XMLElement>();
	private final Map<String,List<XMLElement>> tipiParts = new TreeMap<String, List<XMLElement>>();

	private final ClassManager myClassManager = new ClassManager();

	public void build(String repository, String extensionRepository, String extensions,File baseDir) throws IOException {
		File xsd = new File(baseDir, "tipi/tipi.xsd");
		xsd.getParentFile().mkdirs();
		
		if(extensions==null || "".equals(extensions)) {
			
			throw new IllegalArgumentException("No extensions defined ");
		}
		StringTokenizer st = new StringTokenizer(extensions,",");
//		Map<String, List<String>> repDefinition = ClientActions.getExtensions( repository);
		VersionResolver vr = new VersionResolver();
		vr.load(extensionRepository);
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			logger.info("Processing token: "+token);
			Map<String,String> versionMap = null;
			
			try {
				versionMap = vr.resolveVersion(token);
			} catch (IllegalArgumentException e1) {
				throw new IOException("Extension not found: "+token);
			}
			String ext = versionMap.get("extension");
			String version = versionMap.get("version");
			try {
				appendExtension(ext,version,extensionRepository);
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
		processMap();
		try {
			createXSD(baseDir, allComponents, allActions);
			createMetadata(repository,baseDir, allComponents, allActions, allTypes);
			
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
	}




	public  XMLElement appendExtension(String project,String version, String repository) throws IOException {
		try {
			URL rep = new URL(repository);
			URL projectURL = new URL(rep,project+"/");
			URL versionURL = new URL(projectURL,version+"/");
			URL extensionURL = new URL(versionURL,"definition.xml");
			
			XMLElement result = ClientActions.getXMLElement(extensionURL);

			parseProjectDefinition(project,versionURL, result);

			return result;
		} catch (MalformedURLException e) {
			logger.error("Error: ",e);
		}
		return null;

	}
	private void createXSD(File baseDir, Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions) throws IOException {
		// logger.info("# of components: "+allComponents.size());
		XMLElement root = new CaseSensitiveXMLElement();
		root.setName("xs:schema");
		root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
		root.setAttribute("elementFormDefault", "qualified");

		XMLElement bb = createBlockType();
		root.addChild(bb);

		XMLElement cc = createActions(allActions, allComponents);
		root.addChild(cc);
		XMLElement rootTidElement = addTag("xs:element", root);
		rootTidElement.setAttribute("name", "tid");

		XMLElement cmpl = addTag("xs:complexType", rootTidElement);
		XMLElement choice = addTag("xs:choice", cmpl);
		choice.setAttribute("maxOccurs", "unbounded");
		choice.setAttribute("minOccurs", "0");

		appendTipiIncludeTag(choice);
		appendStorageTag(choice);
		appendTipiConfigTag(choice);
		appendClientConfigTag(choice);
		appendInlineTml(choice, root);

		logger.info("All components: "+allComponents.keySet());
		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();
			XMLElement e = createTipiClassElement(current, false, allComponents.get(current), allComponents);
			if (e != null) {
				root.addChild(e);
			}
		}
		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();
			XMLElement e = createTipiLayout(current, false, allComponents.get(current), allComponents);
			if (e != null) {
				root.addChild(e);
			}
		}

		for (Iterator<String> iter = allComponents.keySet().iterator(); iter.hasNext();) {
			String current = iter.next();
			XMLElement e = createTipiClassElement(current, true, allComponents.get(current), allComponents);

			if (e != null) {
				choice.addChild(e);
			} else {
			}
		}
		XMLElement tt = addTag("xs:attribute", cmpl);
		tt.setAttribute("name", "errorhandler");

		File xsd = new File(baseDir, "tipi/tipi.xsd");
		
		OutputStream os =new FileOutputStream(xsd);
		OutputStreamWriter fw = new OutputStreamWriter(os);
		// FileWriter fw = new FileWriter( new
		// File(baseTipiDir,"tipi.xsd"));
		root.write(fw);
		fw.flush();
		fw.close();
		os.close();
	}

	
	private void createMetadata(String repository, File baseDir, Map<String, XMLElement> allComponents2, Map<String, XMLElement> allActions2,
			Map<String, XMLElement> allTypes) throws IOException {
		XMLElement metadata = new CaseSensitiveXMLElement("metadata");
		XMLElement components = new CaseSensitiveXMLElement("components");
		XMLElement actions = new CaseSensitiveXMLElement("actions");
		XMLElement types = new CaseSensitiveXMLElement("types");
		metadata.addChild(components);
		metadata.addChild(actions);
		metadata.addChild(types);
//		VersionResolver vr = new VersionResolver(extensionRepository);
		String docPrefix = repository+"wiki/doku.php?id=tipidoc:";

		for (Entry<String,XMLElement> elt : allComponents2.entrySet()) {
			XMLElement c = new CaseSensitiveXMLElement("element");
			// skip abstract classes
		
			if(elt.getValue().getAttribute("class")!=null) {
				XMLElement entry = elt.getValue();
				c.setAttribute("name",elt.getKey());
				c.setAttribute("href",createDocLink(docPrefix,entry.getStringAttribute("extension"),elt.getKey(),"component"));
				components.addChild(c);
				
			}
		}
		for (Entry<String,XMLElement> elt : allActions2.entrySet()) {
			XMLElement c = new CaseSensitiveXMLElement("element");
			XMLElement entry = elt.getValue();
			c.setAttribute("name",elt.getKey());
			c.setAttribute("href",createDocLink(docPrefix,entry.getStringAttribute("extension"),elt.getKey(),"action"));
			actions.addChild(c);
		}
		for (Entry<String,XMLElement> elt : allTypes.entrySet()) {
			XMLElement c = new CaseSensitiveXMLElement("element");
			XMLElement entry = elt.getValue();
			c.setAttribute("name",elt.getKey());
			c.setAttribute("href",createDocLink(docPrefix,entry.getStringAttribute("extension"),elt.getKey(),"type"));
			types.addChild(c);
		}
		File settings = new File(baseDir,".tipiproject");
		settings.mkdirs();
		File metada = new File(settings,"tipi.metadata");
		FileWriter fw = new FileWriter(metada);
		metadata.write(fw);
		fw.flush();
		fw.close();
	}
	
	// DOcumentation is unversioned for now
	
	private String createDocLink(String docPrefix,String extension, String name, String elementType) {
		String docPrefixWithExtension = docPrefix+extension.toLowerCase();

		if(elementType.equals("type")) {
			return docPrefixWithExtension + ":types:" + name;
			
		} else if(elementType.equals("component")) {
			return docPrefixWithExtension + ":components:" + ("component".equals(elementType)?"c.":"") + name;			
		} else if(elementType.equals("action")) {
		return docPrefixWithExtension + ":actions:" + ("component".equals(elementType)?"c.":"") + name;			
		}
		return "huh?";
	}




	private void processMap()  {

		List<XMLElement> compo = tipiParts.get("tipiclass");
		if(compo!=null) {
			for (XMLElement element : compo) {
				allComponents.put(element.getStringAttribute("name"), element);
		//		element.setAttribute("extension", extension);
				myClassManager.addTipiClassDefinition(element);
				processClass(element, allEvents, allValues);
			}
		}
		List<XMLElement> actions = tipiParts.get("tipiaction");
		if(actions!=null) {
			for (XMLElement element : actions) {
				allActions.put(element.getStringAttribute("name"), element);
		//		element.setAttribute("extension", extension);
			}
		}

		List<XMLElement> parsers = tipiParts.get("tipi-parser");
		if(parsers!=null) {
			for (XMLElement element : parsers) {
				allTypes.put(element.getStringAttribute("name"), element);
			//	element.setAttribute("extension", extension);
			}
		}

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
	
	
	
	public void parseProjectDefinition(String extension, URL versionURL, XMLElement definitionElement) throws MalformedURLException, IOException {
		List<XMLElement> includes = definitionElement.getAllElementsByTagName("include");
		for (XMLElement element : includes) {
			String path = element.getStringAttribute("path");
			XMLElement xx = ClientActions.getXMLElement(new URL(versionURL,"includes/"+path));
			// beware of missing function.xml
			//logger.info("ELEMENT: "+xx);
			if(xx!=null) {
				appendClassDefElement(extension,xx);
			}
		}
	}
	
	private void appendClassDefElement(String extension, XMLElement xx) {
		List<XMLElement> cc = xx.getChildren();
		for (XMLElement element : cc) {
			List<XMLElement> elts = tipiParts.get(element.getName());
			element.setAttribute("extension", extension);
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
		//xxx.setAttribute("use", "required");

		return block;
	}

	private XMLElement createActions(Map<String, XMLElement> allActions, Map<String, XMLElement> allComponents) {
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
			XMLElement element = addTag("xs:element", choice);
			element.setAttribute("name", current);
			
					
			XMLElement params = addTag("xs:complexType", element);
			List<XMLElement> events = currentAction.getElementsByTagName("events");
			if (events != null && !events.isEmpty()) {
				if (events.size() > 1) {
					throw new IllegalStateException("Only one events tag allowed");
				}
				XMLElement first = events.get(0);
				if (first.getChildren().size() > 0)
				{
					// Open the choice tag
					XMLElement eventChoice = addTag("xs:choice", params);
					eventChoice.setAttribute("maxOccurs", "unbounded");
					eventChoice.setAttribute("minOccurs", "0");
					for (XMLElement currentEvent : first.getChildren()) {
						if (currentEvent.getName().equals("event")) {
							XMLElement eventTag = addTag("xs:element", eventChoice);
							eventTag.setAttribute("name", currentEvent.getAttribute("name"));
							eventTag.setAttribute("type", "allActions");
						}
					}
				}
			}
			
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
			
			XMLElement xx = myClassManager.getAssembledClassDef(current);
			//XMLElement xx = allComponents.get(current);
			
			
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
					throw new IllegalStateException("Only one values tag allowed");
				}
				XMLElement first = l.get(0);
				List<XMLElement> valueTag = first.getElementsByTagName("value");

				for (XMLElement currentValue : valueTag) {
					// List<XMLElement> children = currentMethod.getChildren();
					String direction = currentValue.getStringAttribute("direction");
					if ("out".equals(direction)) {
						// read only, so it can not be supplied on an
						// instantiate
						continue;
					}
					String type = currentValue.getStringAttribute("type");
					XMLElement xsAttr = addTag("xs:attribute", params);
					xsAttr.setAttribute("name", currentValue.getStringAttribute("name"));
					xsAttr.setAttribute("type", "xs:string");
					xsAttr.setAttribute("default", "[" + type + "]");
					xsAttr.setAttribute("name", currentValue.getStringAttribute("name"));

					// if (!children.isEmpty()) {
					// for (int i = 0; i < children.size(); i++) {
					// XMLElement currentParam = children.get(i);
					// logger.info("CCC: "+currentParam);
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
			Map<String, XMLElement> allComponents) throws IOException {
		XMLElement result = new CaseSensitiveXMLElement();
		result.setName("xs:element");
//		logger.info("Element before: "+element);
		
		element = ComponentMerger.getAssembledClassDef (allComponents, element);
//		logger.info("Element after: "+element);
		String type = element.getStringAttribute("type");
		if (type.equals("tipi") || type.equals("component")|| type.equals("connector")) {
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
						|| "component".equals(currentComponent.getStringAttribute("type")) || "connector".equals(currentComponent.getStringAttribute("type"))) {
						XMLElement reff = addTag("xs:element", seq);
						reff.setAttribute("ref", ("c.") + e);
				}
			}
			if ("true".equals(element.getStringAttribute("layoutmanager"))) {
				for (Iterator<String> itt = allComponents.keySet().iterator(); itt.hasNext();) {
					String e = itt.next();
					XMLElement currentComponent = allComponents.get(e);
					if ("layout".equals(currentComponent.getStringAttribute("type"))) {
						XMLElement reff = addTag("xs:element", seq);
						reff.setAttribute("ref", "l." + e);
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

						XMLElement classAttr = addTag("xs:element", seq);
						classAttr.setAttribute("name", eventElement.getAttribute("name"));
						classAttr.setAttribute("type", "allActions");
					}
				}
			}
		}

		// Old skool tags:
		if ("component".equals(type) || "tipi".equals(type)|| "connector".equals(type)) {
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
			Map<String, XMLElement> allComponents) throws IOException {
		// component / c.window etc /definition
		// logger.info("element: " + element.getName() + " current: " +
		// current);
		if ("tipi".equals(element.getStringAttribute("type")) || "component".equals(element.getStringAttribute("type"))|| "connector".equals(element.getStringAttribute("type"))) {
			return createTipiComponent(current, isDefinition, element, allComponents);
		}

		return null;
	}

	private static XMLElement createTipiLayout(String current, boolean isDefinition, XMLElement element,
			Map<String, XMLElement> allComponents) throws IOException {
		// component / c.window etc /definition

		if ("layout".equals(element.getStringAttribute("type"))) {
			return createTipiComponent(current, isDefinition, element, allComponents);
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

	private static void appendStorageTag(XMLElement choice) {
		XMLElement tipiInclude = addTag("xs:element", choice);
		tipiInclude.setAttribute("name", "tipi-storage");
		XMLElement cmplTipiInclude = addTag("xs:complexType", tipiInclude);
		XMLElement locAttr = addTag("xs:attribute", cmplTipiInclude);
		locAttr.setAttribute("name", "type");
		locAttr.setAttribute("use", "required");
		XMLElement lazyAttr = addTag("xs:attribute", cmplTipiInclude);
		lazyAttr.setAttribute("name", "scriptPrefix");
//		XMLElement compAttr = addTag("xs:attribute", cmplTipiInclude);
//		compAttr.setAttribute("name", "definition");
	}

	
	private static void appendInlineTml(XMLElement choice, XMLElement schema) {
		try {
			XMLElement tml = new CaseSensitiveXMLElement();
			tml.setName("xs:element");
			tml.setAttribute("ref", "tml");
			XMLElement tmlX = new CaseSensitiveXMLElement();
			InputStream in = XsdBuilder.class.getResourceAsStream("xtml.xsd");
			tmlX.parseFromReader(new InputStreamReader(in));
			in.close();

			List<XMLElement> ll = tmlX.getChildren();
			for (XMLElement element : ll) {
				schema.addChild(element);
			}

			choice.addChild(tml);

		} catch (XMLParseException e) {
			logger.error("Error: ",e);
		} catch (IOException e) {
			logger.error("Error: ",e);
		}

	}
	
	public static void main(String[] args) throws IOException {

		// change this to the project dir of SportlinkClub and then run this main method to regenerate the Tipi XSD
		// the libraries on spiritus will be queried for the necessary information, so local files will be ignored. 
		final String baseDir = "C:\\Marte\\CVS\\SportlinkClub\\";
		
		File settings = new File(baseDir, "settings/tipi.properties");
		
		InputStream is = new java.io.FileInputStream(settings);
		PropertyResourceBundle pe = new PropertyResourceBundle(is);
		is.close();	
		String extensions = pe.getString("extensions").trim();
		String repository = pe.getString("repository").trim();

		String extensionRepository = repository+"Extensions/";

		XsdBuilder b = new XsdBuilder();
		try { 
			b.build(repository,extensionRepository, extensions, new File(baseDir));
			System.out.println("XSD rebuilt!");
		} catch (IOException e) {
			System.out.println("XSD generator error:" + e.getStackTrace());
		}
	}
}
