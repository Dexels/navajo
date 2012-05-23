package com.dexels.navajo.tipi.classdef;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import navajo.ExtensionDefinition;

import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLElement;

public final class ClassManager extends BaseClassManager implements Serializable, IClassManager {
	
	private static final long serialVersionUID = 1L;
	private final Map<String, XMLElement> tipiClassDefMap = new HashMap<String, XMLElement>();
	private final Map<String, List<String>> unresolvedExtensions = new HashMap<String, List<String>>();
	private final Map<String, XMLElement> interfaceMap = new HashMap<String, XMLElement>();
	private final Map<String, FunctionDefinition> functionDefinitionMap = new HashMap<String, FunctionDefinition>();
	private final Map<String, ExtensionDefinition> extensionMapper = new HashMap<String, ExtensionDefinition>();

	public ClassManager(TipiContext context) {
		super(context);
		assert (context != null);
	}

	@Override
	public ExtensionDefinition getExtension(String extensionName) {
		return extensionMapper.get(extensionName);
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.classdef.IClassManager#getClassDef(java.lang.String)
	 */
	@Override
	public XMLElement getClassDef(String name) {
		XMLElement xmlElement = tipiClassDefMap.get(name);
		if (xmlElement == null) {
			System.err.println("Missing classdef: " + name);
			System.err.println("tipiClass: " + tipiClassDefMap.keySet());
		}
		return xmlElement;
	}


	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.classdef.IClassManager#getClassMap()
	 */
	@Override
	public Map<String, XMLElement> getClassMap() {
		return tipiClassDefMap;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.classdef.IClassManager#getFunctionDefMap()
	 */
	@Override
	public Map<String, FunctionDefinition> getFunctionDefMap() {
		return functionDefinitionMap;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.classdef.IClassManager#clearClassMap()
	 */
	@Override
	public void clearClassMap() {
		tipiClassDefMap.clear();
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.classdef.IClassManager#addTipiClassDefinition(com.dexels.navajo.tipi.tipixml.XMLElement, navajo.ExtensionDefinition)
	 */
	@Override
	public final void addTipiClassDefinition(XMLElement xe,
			ExtensionDefinition ed) {
		String name = (String) xe.getAttribute("name");
		String clas = (String) xe.getAttribute("class");
		if (clas == null) {
			interfaceMap.put(name, xe);
		}
		extensionMapper.put(name, ed);
		String extending = (String) xe.getAttribute("implements");
		StringTokenizer st = null;
		List<String> isExtending = null;
		if (extending != null) {
			st = new StringTokenizer(extending, ",");
			isExtending = new LinkedList<String>();
			while (st.hasMoreTokens()) {
				isExtending.add(st.nextToken());
			}
		}
		if (isExtending != null) {
			unresolvedExtensions.put(name, isExtending);
		}
		// TODO
		tipiClassDefMap.put(name, xe);
	}



 // doesn't use the super at all
	@Override
	public Class<?> getTipiClass(XMLElement xe) {
		Class<?> cc = null;
		String pack = (String) xe.getAttribute("package");
		String clas = (String) xe.getAttribute("class");
		String name = (String) xe.getAttribute("name");
		String fullDef = pack + "." + clas;
		ExtensionDefinition ed = getExtension(name);
		try {
			if (ed != null) {
				ClassLoader cl = ed.getClass().getClassLoader();
				cc = Class.forName(fullDef, true, cl);
				return cc;
			}
			System.err
					.println("FALLBACK: Loading class without Extension definition");
			
			cc = Class.forName(fullDef, true, myContext.getClassLoader());
		} catch (ClassNotFoundException ex) {
			System.err.println("Error loading class: " + fullDef);
			ex.printStackTrace();
		} catch (SecurityException ex) {
			System.err.println("Security Error loading class: " + fullDef);
			ex.printStackTrace();

		}
		return cc;
	}


	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.classdef.IClassManager#getClassNameSet()
	 */
	@Override
	public Set<String> getClassNameSet() {
		return tipiClassDefMap.keySet();
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.classdef.IClassManager#addFunctionDefinition(java.lang.String, com.dexels.navajo.functions.util.FunctionDefinition)
	 */
	@Override
	public void addFunctionDefinition(String name, FunctionDefinition fd) {
		functionDefinitionMap.put(name, fd);
	}

}
