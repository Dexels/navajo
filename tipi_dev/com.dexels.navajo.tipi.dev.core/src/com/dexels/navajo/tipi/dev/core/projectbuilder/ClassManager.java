package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.dexels.navajo.tipi.dev.core.util.XMLElement;


public final class ClassManager {
	protected final Map<String, XMLElement> tipiClassDefMap = new HashMap<String, XMLElement>();
//	private final TipiContext myContext;
	protected final Map<String, List<String>> unresolvedExtensions = new HashMap<String, List<String>>();

	private final Map<String, XMLElement> interfaceMap = new HashMap<String, XMLElement>();

//	private final Map<String, FunctionDefinition> functionDefinitionMap = new HashMap<String, FunctionDefinition>();

	
	public ClassManager() {
	}

	public XMLElement getClassDef(String name) {
		return tipiClassDefMap.get(name);
	}
	
	public XMLElement getAssembledClassDef(String name)  {
		XMLElement classDef = getClassDef(name);
		XMLElement result = null;
		List<XMLElement> interfaces = getInterfacesForClassDef(classDef);
		if(interfaces==null) {
			result = classDef;
		} else {
			interfaces.add(classDef);
			result = assembleClassDefs(interfaces);
		}
		return result;
	}


	public Map<String, XMLElement> getClassMap() {
		return tipiClassDefMap;
	}

//	public Map<String, FunctionDefinition> getFunctionDefMap() {
//		return functionDefinitionMap;
//	}
	public void clearClassMap() {
		tipiClassDefMap.clear();
	}

	public final void addTipiClassDefinition(XMLElement xe) {
		String name = (String) xe.getAttribute("name");
		String clas = (String) xe.getAttribute("class");
		if(clas==null) {
				interfaceMap.put(name,xe);
		}
		String extending = (String) xe.getAttribute("implements");
		StringTokenizer st = null;
		List<String> isExtending = null;
		if(extending!=null) {
			st = new StringTokenizer(extending,",");
			isExtending = new LinkedList<String>();
			while(st.hasMoreTokens()) {
				isExtending.add(st.nextToken());
			}
		}
		if(isExtending!=null) {
			unresolvedExtensions.put(name, isExtending);
		}
		tipiClassDefMap.put(name, xe);
	}


//	public Class<?> getTipiClass(XMLElement xe) {
//		Class<?> cc = null;
//		String pack = (String) xe.getAttribute("package");
//		String clas = (String) xe.getAttribute("class");
//		String fullDef = pack + "." + clas;
//		try {
//			cc = Class.forName(fullDef, true, myContext.getClassLoader());
//		} catch (ClassNotFoundException ex) {
//			logger.info("Error loading class: " + fullDef);
//			ex.printStackTrace();
//		} catch (SecurityException ex) {
//			logger.info("Security Error loading class: " + fullDef);
//			ex.printStackTrace();
//
//		}
//		return cc;
//	}

	private List<XMLElement> getInterfacesForClassDef(XMLElement classDef)  {
		String extending = classDef.getStringAttribute("implements");
		if(extending!=null) {
			StringTokenizer st = new StringTokenizer(extending,",");
			LinkedList<XMLElement> isExtending = new LinkedList<XMLElement>();
			while(st.hasMoreTokens()) {
				String currentName = st.nextToken();
				XMLElement element = tipiClassDefMap.get(currentName);
				if(element==null) {
					throw new RuntimeException("Error: ClassDef: "+classDef.getStringAttribute("name")+" has an unknown super interface: "+currentName);
				}
				isExtending.add(element);
			}
			return isExtending;
		}
		return null;
	}
	
	private XMLElement assembleClassDefs(List<XMLElement> interfaces) {
		assert (interfaces!=null);
		assert (interfaces.size()>0);
		if(interfaces.size()==1) {
			// maybe copy?
			return interfaces.get(0);
		}
		ClassModel cl = new ClassModel();
		for (XMLElement element : interfaces) {
			cl.addDefinition(element);
		}
		return cl.buildResult();
	}
	


	public Set<String> getClassNameSet() {
		return tipiClassDefMap.keySet();
	}


}
