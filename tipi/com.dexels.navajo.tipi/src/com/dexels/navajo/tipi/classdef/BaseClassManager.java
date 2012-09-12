package com.dexels.navajo.tipi.classdef;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.XMLElement;

public abstract class BaseClassManager implements IClassManager {

	protected TipiContext myContext;
	private final Map<String, TipiTypeParser> parserInstanceMap = new HashMap<String, TipiTypeParser>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseClassManager.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.classdef.IClassManager#getTipiClass(com.dexels
	 * .navajo.tipi.tipixml.XMLElement)
	 */
	public BaseClassManager(TipiContext context) {
		this.myContext = context;
	}

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
			logger.error("Error loading class: " + fullDef,ex);
		} catch (SecurityException ex) {
			logger.error("Security Error loading class: " + fullDef,ex);
		}
		return cc;
	}

	private XMLElement assembleClassDefs(List<XMLElement> interfaces,
			String name) {
		assert (interfaces != null);
		assert (interfaces.size() > 0);
		if (interfaces.size() == 1) {
			// maybe copy?
			return interfaces.get(0);
		}
		ClassModel cl = new ClassModel(name);
		for (XMLElement element : interfaces) {
			cl.addDefinition(element);
		}
		return cl.buildResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.classdef.IClassManager#getAssembledClassDef(java
	 * .lang.String)
	 */
	@Override
	public XMLElement getAssembledClassDef(String name) throws TipiException {
		XMLElement classDef;
		try {
			classDef = getClassDef(name);
		} catch (ClassNotFoundException e) {
			throw new TipiException("Class loading problem: " + name, e);
		}
		if (classDef == null) {
			throw new TipiException(
					"Error loading classdef. Definition not found for: " + name);
		}

		Object classInstance = classDef.getObjectAttribute("classInstance");
		XMLElement result = null;
		List<XMLElement> interfaces = getInterfacesForClassDef(classDef);
		if (interfaces == null) {
			result = classDef;
		} else {
			interfaces.add(classDef);
			result = assembleClassDefs(interfaces, name);
			result.setObjectAttribute("classInstance", classInstance);
		}

		return result;
	}

	private List<XMLElement> getInterfacesForClassDef(XMLElement classDef)
			throws TipiException {
		String extending = classDef.getStringAttribute("implements");
		if (extending != null) {
			StringTokenizer st = new StringTokenizer(extending, ",");
			LinkedList<XMLElement> isExtending = new LinkedList<XMLElement>();
			while (st.hasMoreTokens()) {
				String currentName = st.nextToken();
				XMLElement element;
				try {
					element = getClassDef(currentName);
				} catch (ClassNotFoundException e) {
					throw new TipiException(
							"ClassLoadingError: ClassDef: "
									+ classDef.getStringAttribute("name")
									+ " has an unknown super interface: "
									+ currentName, e);
				}
				if (element == null) {
					throw new TipiException("Error: ClassDef: "
							+ classDef.getStringAttribute("name")
							+ " has an unknown super interface: " + currentName);
				}
				isExtending.add(element);
			}
			return isExtending;
		}
		return null;
	}

	public Object parse(TipiComponent source, String name, String expression,
			TipiEvent te) {
		TipiTypeParser ttp = getParser(name);
		if (ttp == null) {
			logger.warn("Unknown type: " + name);
			return null;
		}
		Object o = ttp.parse(source, expression, te);
		Class<?> c = ttp.getReturnType();
		if (o != null && !c.isInstance(o)) {
			throw new IllegalArgumentException(
					"Wrong type returned. Expected: " + c + "\nfound: "
							+ o.getClass() + "\nWas parsing expression: "
							+ expression + "\nUsing parser: " + name);
		}
		return o;
	}

	public TipiTypeParser getParser(String name) {
		TipiTypeParser ttp = parserInstanceMap.get(name);
		return ttp;
	}

	@Override
	public TipiTypeParser parseParser(XMLElement xe, ExtensionDefinition te) {
		String name = xe.getStringAttribute("name");
		String parserClass = xe.getStringAttribute("parser");
		String classType = xe.getStringAttribute("type");
		Class<TipiTypeParser> pClass = null;
		try {
			pClass = (Class<TipiTypeParser>) Class.forName(parserClass, true,
					myContext.getClassLoader());
		} catch (ClassNotFoundException ex) {
			System.err
					.println("Error loading class for parser: " + parserClass);
			return null;
		}
		TipiTypeParser ttp = null;
		try {
			ttp = pClass.newInstance();
		} catch (IllegalAccessException ex1) {
			logger.error("Error instantiating class for parser: "
					+ parserClass, ex1);
			return null;
		} catch (InstantiationException ex1) {
			logger.error("Error instantiating class for parser: "
					+ parserClass, ex1);
			return null;
		}
		try {
			Class<?> cc = Class.forName(classType, true,
					myContext.getClassLoader());
			ttp.setReturnType(cc);
		} catch (ClassNotFoundException ex) {
			logger.error("Error verifying return type class for parser: "
					+ classType, ex);
			return null;
		}
		parserInstanceMap.put(name, ttp);
		return ttp;
	}

	public boolean isValidType(String name) {
		return parserInstanceMap.containsKey(name);
	}

}
