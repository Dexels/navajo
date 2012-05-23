package com.dexels.navajo.tipi.extensionmanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.dexels.navajo.tipi.projectbuilder.ComponentMerger;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;


public class TipiCreateWikiDocumentation extends ExtensionClassdefProcessor {

	private final Map<String,String> typeExtension = new HashMap<String,String>();

	
	protected void parseParserMap(URL parserLink) throws IOException {
		InputStream openStream = parserLink.openStream();
		InputStreamReader isr = new InputStreamReader(openStream);
		XMLElement pp = new CaseSensitiveXMLElement();
		pp.parseFromReader(isr);
		openStream.close();
		isr.close();
		List<XMLElement> cc = pp.getChildren();
		for (XMLElement element : cc) {
			typeExtension.put(element.getStringAttribute("type"), element.getStringAttribute("extension"));
		}
	}	
	
	

	protected void filterExtensions(String extension, List<String> extensions, Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions,
			Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues, Map<String, XMLElement> allTypes,
			Map<String, XMLElement> allFunctions) {
		filterMapForExtension(extension,allComponents);
		filterMapForExtension(extension,allActions);
		filterMapForExtension(extension,allEvents);
		filterMapForExtension(extension,allValues);
		filterMapForExtension(extension,allTypes);
		filterMapForExtension(extension,allFunctions);
	}
	
	private void filterMapForExtension(String extension, Map<String, XMLElement> xmlMap) {
		Set<String> toBeRemoved = new HashSet<String>();
		for (Entry<String, XMLElement> e : xmlMap.entrySet()) {
			String extensionString = e.getValue().getStringAttribute("extension");
			if(!extension.equals(extensionString)) {
				toBeRemoved.add(extensionString);
			}

		}
		for (String string : toBeRemoved) {
			xmlMap.remove(string);
		}
	}


	protected void processTipiContext(URL repository, String originalExtension, String version, List<String> extensions, Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions,
			Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues, Map<String, XMLElement> allTypes,
			Map<String, XMLElement> allFunctions) {
		
		String currentExtension = originalExtension.toLowerCase();
//		System.err.println("All: "+allComponents.keySet());
		try {
			parseParserMap(new URL(repository,"typemap.xml"));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 Map<String, XMLElement> allComponentsOfAllExtensions = new HashMap<String, XMLElement>(allComponents);
		
		filterExtensions(currentExtension, extensions, allComponents, allActions, allEvents, allValues, allTypes, allFunctions);

		Map<String, List<XMLElement>> extensionComponentMap = createExtensionMapFromList(allComponents);
		Map<String, List<XMLElement>> extensionActionMap = createExtensionMapFromList(allActions);
		Map<String, List<XMLElement>> extensionTypeMap = createExtensionMapFromList(allTypes);
		Map<String, List<XMLElement>> extensionFunctionMap = createExtensionMapFromList(allFunctions);
		
		try {
			for (String extension : extensionTypeMap.keySet()) {
				List<XMLElement> typeList = extensionTypeMap.get(extension);
				if(typeList!=null) {
					processTypes(currentExtension, typeList);
					processTypeHeaders(currentExtension, typeList);
				}
			}

			for (String extension : extensionComponentMap.keySet()) {
				processComponents(currentExtension, version, extensionComponentMap.get(extension), extensionActionMap.get(extension),allComponentsOfAllExtensions);
			}
			for (String extension : extensionActionMap.keySet()) {
				processActions(currentExtension,version, extensionActionMap.get(extension));
			}
			for (String extension : extensionFunctionMap.keySet()) {
				processFunctions(currentExtension,version, extensionFunctionMap.get(extension));
				processFunctionHeaders(currentExtension,version,extensionFunctionMap.get(extension));
			}
			
			mergeIndex(originalExtension, version, repository,allComponents,allActions,allFunctions,allTypes);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

	
	private void processFunctionHeaders(String extension,String version, List<XMLElement> list) throws IOException {
		OutputStream os = writeResource( extension + "/functions/list.txt");
		OutputStreamWriter osw = new OutputStreamWriter(os);
		Collections.sort(list, new Comparator<XMLElement>() {

			public int compare(XMLElement o1, XMLElement o2) {
				String s1 = o1.getStringAttribute("name");
				String s2 = o2.getStringAttribute("name");
				return s1.compareTo(s2);
			}
		});
		osw.write("=== Types ===\n");
			for (XMLElement e : list) {
				osw.write("  * Function: [[.:"+e.getStringAttribute("name")+"|"+e.getStringAttribute("name")+"]]\n");
			}
		osw.flush();
		os.flush();
		os.close();
	}

	private void processFunctions(String extension, String version, List<XMLElement> list) throws IOException {
		Collections.sort(list, new Comparator<XMLElement>() {
			public int compare(XMLElement o1, XMLElement o2) {
				String s1 = o1.getStringAttribute("name");
				String s2 = o2.getStringAttribute("name");
				return s1.compareTo(s2);
			}
		});
	    System.err.println("Writing function definitions: ("+extension+")"+list.size());
		for (XMLElement element : list) {
			String resourceName = extension + "/functions/"+element.getStringAttribute("name")+".txt";
			OutputStream os = writeResource(resourceName.toLowerCase());
			OutputStreamWriter osw = new OutputStreamWriter(os);
			typeExtension.put(element.getStringAttribute("name"),extension);
			
			osw.write("==== Type: "+element.getStringAttribute("name")+" ====\n");
			osw.write("  * Java type: "+element.getStringAttribute("class")+"\n");
			
			appendDescriptorTag(osw,"   ", element.getChildByTagName("description"));

			osw.write("\n");

//			XMLElement description = element.getChildByTagName("description");
//			String desc = description==null?"":description.getContent();
//			XMLElement input = element.getChildByTagName("input");
//			String inp = input==null?"":input.getContent();
//			XMLElement result = element.getChildByTagName("result");
//			String res = result==null?"":result.getContent();


			osw.flush();
			os.flush();
			os.close();
			
		}
	}



	/**
	 * Todo: all* is not used now. We need to filter out empty columns
	 * @param extension
	 * @param version
	 * @param repository
	 * @param allComponents
	 * @param allActions
	 * @param allFunctions
	 * @param allTypes
	 * @throws IOException
	 */

	public void mergeIndex(String extension, String version, URL repository, Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions, Map<String, XMLElement> allFunctions, Map<String, XMLElement> allTypes) throws IOException {
//		System.err.println("Merging from repository: "+repository+" extension: "+extension);
		File extensionFile = new File(getDistributionDir(),"extensions.xml");
		OutputStream os = writeResource("tipi.txt");
		OutputStreamWriter osw = new OutputStreamWriter(os);
		osw.write("==== Tipi Extensions ====\n");
		osw.write("^ Extension ^ Version ^ Components ^ Actions ^ Functions ^ Types ^ JavaDoc ^\n");
	//	osw.write("Extension:"+extension+" version: "+version+"\n");

		System.err.println("Output: "+getOutputDir().getAbsolutePath());
		XMLElement exten = new CaseSensitiveXMLElement();
		FileReader fr = new FileReader(extensionFile);
		exten.parseFromReader(fr);
		fr.close();
		 
		List<XMLElement> zz = exten.getChildren();
		
		 Collections.sort(zz, new Comparator<XMLElement>(){
			public int compare(XMLElement o1, XMLElement o2) {
				return o1.getStringAttribute("name").compareTo(o2.getStringAttribute("name"));
			}});

		Map<String,XMLElement> filtered = new TreeMap<String,XMLElement>();
		for (XMLElement element : zz) {
			String id = element.getStringAttribute("name");
			filtered.put(id, element);
			//osw.write(" [[tipidoc:" + id + ":details|"+id+"]]\n");
		}
		
		
		
		for (XMLElement element : filtered.values()) {
			String id = element.getStringAttribute("name");
			String currentVersion = element.getStringAttribute("version");
			String components = "[[tipidoc:" + id + ":componentlist|Components]]";
			String actions = "[[tipidoc:" + id + ":actionlist|Actions]]";
			String functions = "[[tipidoc:" + id + ":functions:list|Functions]]";
			String types = "[[tipidoc:" + id + ":types:list|Types]]";
			String javadoc ="[[this>../Extensions/"+id+"/"+currentVersion+"/javadoc|JavaDoc]]";
			osw.write("| [[tipidoc:" + id + ":details|" + id + "]] |  version: " + currentVersion + " | " + components + " | " + actions + " | " + functions + " | " + types +" | " + javadoc +  "  |\n");
			
		}
//		System.err.println("Extensiojn lost"+extension);
//		for (String ext : extension) {
			createExtensionDetails(repository, new File(getOutputDir().getParentFile(),"definition.xml"),extension,version);
			
//		}
//		for (TipiExtension e : extension) {
//			List<XMLElement> incls = extMap.get(e.getId());
//			if (incls == null || incls.size() == 0) {
//				continue;
//			}
//			osw.write("| [[tipidoc:" + e.getId() + ":details|"+e.getId()+"]]| [[tipidoc:" + e.getId() + ":componentlist|Components]] | [[tipidoc:" + e.getId() + ":actionlist|Actions]] | [[tipidoc:" + e.getId() + ":functions:list|Functions]] | [[tipidoc:" + e.getId() + ":types:list|Types]] |\n");
////			osw.write(" [[tipidoc:" + e.getId() + ":details|"+e.getId()+"]]\n");
//			createExtensionDetails(e);
//		}
		osw.flush();
		os.flush();
		os.close();
	}

	
	
	@SuppressWarnings("deprecation")
	private void createExtensionDetails(URL repository, File inputFile, String extension,String version) throws IOException {

		OutputStream os = writeResource(extension + "/details.txt");
		OutputStreamWriter osw = new OutputStreamWriter(os);
		osw.write("==== Tipi extension: " + extension + " ====\n");

		XMLExtension te = new XMLExtension();
		te.loadXML(inputFile);
		// TODO DISABLED FOR NOW		
		osw.write("=== " + te.getDescription().trim() + " ===\n");
		if (te.getDeploymentDescriptor() != null) {
			osw.write("Main repository deployment: [[http://www.navajo.nl/Tipi/" + te.getProjectName() + "/"
					+ te.getDeploymentDescriptor() + "|" + "http://www.navajo.nl/Tipi/" + te.getProjectName() + "/"
					+ te.getDeploymentDescriptor() + "]]\n\n");
		} else {
			osw.write("Main repository deployment: [not applicable]\n\n");
		}
		if (te.requiresMainImplementation() != null) {
			osw.write("Needs main implementation: [[tipidoc:" + te.requiresMainImplementation() + ":details|"
					+ te.requiresMainImplementation() + "]]\n");
		} else {
			osw.write("This extension can be used in any frontend configuration\n\n");
		}
		if (te.getDependingProjectUrls() != null) {
			osw.write("=== Open source dependencies: ===\n");
			for (String ex : te.getDependingProjectUrls()) {
				osw.write("[[" + ex + "|" + ex + "]]\n\n");
			}
		}
		osw.write("\n-----\n");


	// TODO DISABLED FOR NOW		

		if (te.getRequiredExtensions() != null) {

			for (String ex : te.getRequiredExtensions()) {
				osw.write("Needs: [[tipidoc:" + ex + ":details|" + ex + "]]\n");
			}
		}
//		osw.write("^ Main jars: ^ Id: ^\n");
//		if (te.getMainJars() != null) {
//			for (String ex : te.getMainJars()) {
//				osw.write("| Main jar required: |" + ex + "|\n");
//			}
//		}
//		osw.write("\n----\n");
		URL jnlp = new URL(getDeployRepository()+extension+"/"+version+"/"+extension+".jnlp");
		
		osw.write("Deployment descriptor: [[" + jnlp.toString() + "| "+extension+".jnlp ]]\n\nClick to preload extension into Webstart cache.\n\n");
		

		osw.write("^ Signed: ^ Unsigned: ^\n");

		if (te.getJars() != null) {

			for (String ex : te.getJars()) {
				
				URL u = new URL(getDeployRepository()+extension+"/"+version+"/lib/"+ex);
				URL uns = new URL(getDeployRepository()+extension+"/"+version+"/unsigned/"+ex);
				osw.write("| [[" + u.toString() + "|"+ex+"]] | [[" + uns.toString() + "|"+ex+"]] | \n");
			}
		}
		osw.write("\n\n");

		osw.write("Components: [[componentlist|Components]]\n\n");
		osw.write("Actions: [[actionlist|Actions]]\n\n");
		osw.write("Types: [[typelist|Types]]\n\n");
		osw.write("Functions: [[functionlist|Functions]]\n\n");
// 		osw.write("Actions: [[tipidoc:" + te.getId() + ":actionlist|Actions: " + te.getId() + "]]\n\n");
//		osw.write("Other: Not yet implemented\n\n");

		osw.flush();
		os.flush();
		os.close();
	}

//
//	private void createExtensionDetails(String extension) throws IOException {
//		
//
//	}
	
	
	private void processTypeHeaders(String extension, List<XMLElement> list) throws IOException {
		OutputStream os = writeResource(extension + "/types/list.txt");
		OutputStreamWriter osw = new OutputStreamWriter(os);
		Collections.sort(list, new Comparator<XMLElement>() {

			public int compare(XMLElement o1, XMLElement o2) {
				String s1 = o1.getStringAttribute("name");
				String s2 = o2.getStringAttribute("name");
				return s1.compareTo(s2);
			}
		});
		 //<tipi-parser name="component" type="com.dexels.navajo.tipi.TipiComponent" parser="com.dexels.navajo.tipi.components.core.parsers.ComponentParser" />
		  
		osw.write("==== Types ====\n[[..:..:tipi|(Back to extensions)]]\n");
			for (XMLElement e : list) {
				osw.write("  * Type: [[.:"+e.getStringAttribute("name")+"|"+e.getStringAttribute("name")+"]]\n");
			}
		
		osw.flush();
		os.flush();
		os.close();
	}

	private void processTypes(String extension, List<XMLElement> list) throws IOException {
		for (XMLElement element : list) {
			OutputStream os = writeResource(extension + "/types/"+element.getStringAttribute("name")+".txt");
			OutputStreamWriter osw = new OutputStreamWriter(os);
			 //<tipi-parser name="component" type="com.dexels.navajo.tipi.TipiComponent" parser="com.dexels.navajo.tipi.components.core.parsers.ComponentParser" />
			typeExtension.put(element.getStringAttribute("name"),extension);
			osw.write("=== Type: "+element.getStringAttribute("name")+" ===\n[[list|(Back to type list)]]\n");
			osw.write("  * Java type: "+element.getStringAttribute("type")+"\n");
			osw.write("  * Java parser: "+element.getStringAttribute("parser")+"\n");
			
			appendDescriptorTag(osw,"   ", element.getChildByTagName("description"));

		
			osw.flush();
			os.flush();
			os.close();
			
		}
	}
	private void processComponents(String extension, String version,  List<XMLElement> componentsOfThisExtension, List<XMLElement> allActions,  Map<String,XMLElement> allComponents) throws IOException {
		if(allActions==null) {
			allActions = new ArrayList<XMLElement>();
		}
		OutputStream os = writeResource(extension + "/componentlist.txt");
		OutputStreamWriter osw = new OutputStreamWriter(os);
		Collections.sort(componentsOfThisExtension, new Comparator<XMLElement>() {

			public int compare(XMLElement o1, XMLElement o2) {
				String s1 = o1.getStringAttribute("name");
				String s2 = o2.getStringAttribute("name");
				return s1.compareTo(s2);
			}
		});
		Collections.sort(allActions, new Comparator<XMLElement>() {

			public int compare(XMLElement o1, XMLElement o2) {
				String s1 = o1.getStringAttribute("name");
				String s2 = o2.getStringAttribute("name");
				return s1.compareTo(s2);
			}
		});
		
//		System.err.println("# of Components from this extension: "+componentsOfThisExtension.size()+" total: "+allComponents.size());
//		Map<String,XMLElement> allComponentMap = new HashMap<String, XMLElement>();
//		for (XMLElement element : componentsOfThisExtension) {
//			allComponentMap.put(element.getStringAttribute("name"), element);
//		}
		
		List<XMLElement> realComponents = new ArrayList<XMLElement>();
		List<XMLElement> connectorComponents = new ArrayList<XMLElement>();
		List<XMLElement> layoutComponents = new ArrayList<XMLElement>();
		List<XMLElement> otherComponents = new ArrayList<XMLElement>();

		for (XMLElement e : componentsOfThisExtension) {
			String type = e.getStringAttribute("type");
			if(type==null) {
				System.err.println("DOumentation found abstract class. IGnoring for now!");
				continue;
			}
			if (type.equals("component") || type.equals("tipi") ) {
				realComponents.add(e);
			} else if (type.equals("connector")) {
				connectorComponents.add(e);
			} else if (type.equals("layout")) {
				layoutComponents.add(e);
			} else {
				otherComponents.add(e);
			}
		}
//		System.err.println("Real: "+realComponents.size()+" connector: "+connectorComponents.size());
		if (realComponents.size() > 0) {
			osw.write("==== Components ====\n[[..tipi|(Back to extensions)]]\n");
			for (XMLElement e : realComponents) {
				XMLElement eee = ComponentMerger.getAssembledClassDef(allComponents, e, e.getStringAttribute("name"));
				writeComponentHeader(extension,eee, osw);
				writeComponent(extension,allComponents, eee);
			}
		}
		if (connectorComponents.size() > 0) {
			osw.write("==== Connectors ====\n[[..tipi|(Back to extensions)]]\n");
			for (XMLElement e : connectorComponents) {
				writeComponentHeader(extension,e, osw);
				writeComponent(extension,allComponents, e);
			}
		}

		if (layoutComponents.size() > 0) {
			osw.write("==== Layout ====\n[[..tipi|(Back to extensions)]]\n");
			for (XMLElement e : layoutComponents) {
				writeComponentHeader(extension,e, osw);
				writeComponent(extension,allComponents, e);
			}
		}
		if (otherComponents.size() > 0) {
			osw.write("==== Other ====\n[[..tipi|(Back to extensions)]]\n");
			for (XMLElement e : otherComponents) {
				writeComponentHeader(extension,e, osw);
				writeComponent(extension,allComponents, e);
			}
		}
	
		
		osw.flush();
		os.flush();
		os.close();
	//	String name = extension + "/c." + element.getStringAttribute("name") + ".txt";
		
		os = writeResource(extension + "/actionlist.txt");
		osw = new OutputStreamWriter(os);
		if (allActions.size() > 0) {
			osw.write("=== Actions ===\n");
			for (XMLElement e : allActions) {

				writeActionHeader(extension,e, osw);
//				writeAction(extension,e);
			}
		}
		osw.flush();
		os.flush();
		os.close();

	}

	private void writeComponent(String extensionId,Map<String,XMLElement> allComponents, XMLElement element) throws IOException {
		String name = extensionId + "/components/c." + element.getStringAttribute("name") + ".txt";
		OutputStream os2 = writeResource(name);
		OutputStreamWriter osw2 = new OutputStreamWriter(os2);
		writeComponent(element,allComponents, osw2);
		osw2.flush();
		os2.flush();
		os2.close();
		
		String classdefName = extensionId + "/components/classdef" + element.getStringAttribute("name") + ".txt";
		os2 = writeResource(classdefName);
		osw2 = new OutputStreamWriter(os2);
		osw2.write("<code xml>\n");
		osw2.write(element.toString());
		osw2.write("</code>\n");
		osw2.flush();
		os2.flush();
		os2.close();
		
	}

	private void processActions(String extension, String version, List<XMLElement> allActions) throws IOException {
		
		OutputStream os = writeResource(extension + "/actionlist.txt");
		OutputStreamWriter osw = new OutputStreamWriter(os);
		if (allActions.size() > 0) {
			osw.write("==== Actions ====\n[[..tipi|(Back to extensions)]]\n");
			for (XMLElement e : allActions) {

				writeActionHeader(extension,e, osw);
//				writeAction(extension,e);
			}
		}
		osw.flush();
		os.flush();
		os.close();

		for (XMLElement e : allActions) {
			writeAction(extension,version,e);
		}
	}

	private Map<String, List<XMLElement>> createExtensionMapFromList(Map<String, XMLElement> allElements) {
		Map<String, List<XMLElement>> result = new TreeMap<String, List<XMLElement>>();
		for (XMLElement x : allElements.values()) {
			String ext = x.getStringAttribute("extension");
			if(ext==null) {
				System.err.println("::::: "+x);
			}
			List<XMLElement> v = result.get(ext);
			if (v == null) {
				v = new ArrayList<XMLElement>();
				result.put(ext, v);
			}
			v.add(x);
		}
		return result;
	}

	private void writeAction(String extension, String version, XMLElement component) throws IOException {
		OutputStream os = writeResource(extension + "/actions/"+component.getStringAttribute("name").toLowerCase()+".txt");

		OutputStreamWriter w = new OutputStreamWriter(os);


		w.write("==== " + component.getStringAttribute("name") + " ====\n [[..actionlist|(Back to action list)]]\n");
		
		List<XMLElement> ll = component.getElementsByTagName("description");
		appendDescriptorTags(w, ll);
		List<XMLElement> values = component.getElementsByTagName("param");

		if (values.size() > 0) {
			w.write("== Parameters ==\n");
		}

		for (XMLElement element : values) {
			// <value direction="in" name="y" type="integer" value="0" />
			// w.write("<div class='value'>");
			String name = element.getStringAttribute("name");
			String type = element.getStringAttribute("type");
			String required = element.getStringAttribute("required");
//			w.write("  * " + type + " : " + name + "\n");
			String req;
			if ("true".equals(required)) {
				req = "**(required)**";
			} else {
				req = "";

			}
			w.write("    *" +getDataTypeLink(type)+ " : " + name +" "+req+ "\n");

			appendDescriptorTag(w,"   ", element.getChildByTagName("description"));
		}
//		w.write("------\n\n[[tipiremarks:"+extension+":" + component.getStringAttribute("name") + "|Remarks]]\n");
		writeRemarksLink(w, extension, component.getStringAttribute("name"));
		w.flush();
		os.flush();
		os.close();

	}

	private void writeRemarksLink(Writer w, String extension, String name) throws IOException {
		w.write("\n------\n\n[[tipiremarks:"+extension+":" + name + "|Remarks & demo code for "+name+"]]\n");

	}
	
	private void writeComponentHeader(String extension,XMLElement component, Writer w) throws IOException {
		if ("true".equals(component.getAttribute("deprecated"))) {
			w.write("  * <del>Component: [[tipidoc:"+extension+":components:c." + component.getStringAttribute("name") + "|"+ component.getStringAttribute("name") + "]]</del>\n");
		} else {
			w.write("  * Component: [[tipidoc:"+extension+":components:c." + component.getStringAttribute("name") + "|"+ component.getStringAttribute("name") + "]]\n");
		}

	}
	private void writeActionHeader(String extension,XMLElement component, Writer w) throws IOException {
		w.write("  * Action: [[tipidoc:"+extension+":actions:" + component.getStringAttribute("name") + "|"+ component.getStringAttribute("name") + "]]\n");

	}
	private void writeComponent(XMLElement component,Map<String,XMLElement> allComponents, Writer w) throws IOException {
		
		
		ComponentMerger.getAssembledClassDef(allComponents, component, component.getStringAttribute("name"));
		w.write("==== Component: " + component.getStringAttribute("name") + " (type: " + component.getStringAttribute("type")
				+ ") ====\n [[..componentlist|(Back to componentlist)]]\n");
		List<XMLElement> ll = component.getElementsByTagName("description");
		appendDescriptorTags(w, ll);

		List<XMLElement> values = component.getAllElementsByTagName("value");

		if (values.size() > 0) {
			w.write("=== Values === \n");
		}

		for (XMLElement element : values) {
			// <value direction="in" name="y" type="integer" value="0" />
			// w.write("<div class='value'>");
			String name = element.getStringAttribute("name");
			String type = element.getStringAttribute("type");
			String direction = element.getStringAttribute("direction");
			if(type==null) {
				throw new IllegalArgumentException("Null type in value tag: "+element+" in component name: "+component.getStringAttribute("name"));
			}
			boolean writable = ("in".equals(direction)) || ("inout".equals(direction));
			if (writable) {
		//		w.write("  *[[..:"+getExtensionOfDataType(type)+":types:" + type + " : " + name + " (writable) \n");
				w.write("  *" +getDataTypeLink(type)+ " : " + name + " (writable) ");

			} else {
			//	w.write("  *" + type + " : " + name + "\n");
				w.write("  *" +getDataTypeLink(type)+ " : " + name + ": ");
			}
			w.write("\n");
			List<XMLElement> descrTags = element.getElementsByTagName("description");
		//	if(descrTag==null) {
			//}
			for (XMLElement descrTag : descrTags) {
				appendDescriptorTag(w, "    *",descrTag);
			}
			if(type.equals("selection")) {
				List<XMLElement> lst = element.getElementsByTagName("option");
				for (XMLElement option : lst) {
					w.write("      *" +option.getStringAttribute("value")+ " : (" + option.getStringAttribute("description") + ")\n");
					
				}
				
			}
			// w.write("<div class='value'>");
			//
			// appendAttribute("name",w,element);
			//			
			// appendAttribute("value",w,element);
			// appendAttribute("direction",w,element);
			// appendAttribute("type",w,element);
			// w.write("</div>\n");
		}

		List<XMLElement> methods = component.getAllElementsByTagName("method");
		// <method name="enableTab">
		// <param name="tabname" type="string" />
		// <param name="value" type="boolean" />
		// </method>
		if (methods.size() > 0) {

			w.write("=== Methods ===\n");
		}
		for (XMLElement element : methods) {
			// <value direction="in" name="y" type="integer" value="0" />
			w.write("== Method: " +component.getStringAttribute("name")+"."+element.getStringAttribute("name") + " ==\n");
			appendDescriptorTag(w, "    *",element.getChildByTagName("description"));
			// appendAttribute("name",w,element);
			List<XMLElement> methodParams = element.getElementsByTagName("param");
			if (methodParams.size() > 0) {
//				w.write("<div class='methodparam'>\n");
				for (XMLElement param : methodParams) {
					String name = param.getStringAttribute("name");
					String type = param.getStringAttribute("type");
					w.write("    *" +getDataTypeLink(type)+ " : " + name + "\n");

				}
			}
		}

		// <event name="onLoad">
		// <param name="service" type="string" />
		// <param name="navajo" type="navajo"/>
		// </event>

		List<XMLElement> events = component.getAllElementsByTagName("event");
		if (events.size() > 0) {
			w.write("=== Events ===\n");
		}

		for (XMLElement element : events) {
			// <value direction="in" name="y" type="integer" value="0" />
			w.write("== Event: " + element.getStringAttribute("name") + " ==\n");
			XMLElement descrTag = element.getChildByTagName("description");
			//System.err.println("Descr Tag: "+descr);
			appendDescriptorTag(w,"    *", descrTag);
			List<XMLElement> methodParams = element.getElementsByTagName("param");
			for (XMLElement param : methodParams) {
				String name = param.getStringAttribute("name");
				String type = param.getStringAttribute("type");
				w.write("      *" +getDataTypeLink(type)+ " : " + name + "\n");
			}
		}
//		w.write("\n------\n[[tipiremarks:"+component.getStringAttribute("extension")+":" + component.getStringAttribute("name") + "|Remarks]]\n");
		writeRemarksLink(w, component.getStringAttribute("extension"), component.getStringAttribute("name"));
		w.write("[[classdef"+component.getStringAttribute("name")+"|XML Definition]]\n");
	
	}
	
	private String getDataTypeLink(String type) {
		return "[[tipidoc:"+getExtensionOfDataType(type)+":types:"+type+"|" + type + "]]";
	}

	private String getExtensionOfDataType(String datatype) {
		//System.err.println("Type: "+datatype+" map: "+typeExtension);
		return typeExtension.get(datatype);
	}
	
	private void appendDescriptorTags(Writer w, List<XMLElement> descriptionTags) throws IOException {
		if (descriptionTags == null) {
			return;
		}
		for (XMLElement element : descriptionTags) {
			appendDescriptorTag(w,"  *", element);
		}
	}

	private void appendDescriptorTag(Writer w, String prefix,XMLElement element) throws IOException {

	}




	// private void appendAttribute(String cls, Writer w, XMLElement element)
	// throws IOException {
	// String stringAttribute = element.getStringAttribute(cls);
	// if (stringAttribute == null) {
	// return;
	// }
	// w.write("<div class='" + cls + "'>" + stringAttribute + "</div>\n");
	//
	// }

}
