package com.dexels.navajo.tipi.classdef;

import java.io.Serializable;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import navajo.ExtensionDefinition;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.ServiceRegistrationUtils;
import tipi.TipiExtension;

import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.XMLElement;

public class OSGiClassManager extends BaseClassManager implements IClassManager, Serializable {

	private static final long serialVersionUID = 6641134165918021831L;
	private transient final BundleContext myBundleContext;
	
	private final static Logger logger = LoggerFactory
			.getLogger(OSGiClassManager.class);
	
	public OSGiClassManager(BundleContext bc, TipiContext tc) {
		super(tc.getClassLoader());
		myBundleContext = bc;
	}
	@Override
	public XMLElement getClassDef(String name) throws ClassNotFoundException {
		try {
			Collection<ServiceReference<XMLElement>> aa = myBundleContext
					.getServiceReferences(XMLElement.class,
							"(&(type=tipiclass)(name=" + name + "))");
			if (aa.isEmpty()) {
				return null;
			}
			ServiceReference<XMLElement> xe = aa.iterator().next();
			XMLElement service = myBundleContext.getService(xe);
			Object classInstance = xe.getProperty("classInstance");
//			if(classInstance==null) {
//				throw new ClassNotFoundException("Class for component: "+name+" nto found!");
//			}
			service.setObjectAttribute("classInstance", classInstance);
			
			return service;

		} catch (InvalidSyntaxException e) {
			logger.error("Error: ",e);
		}
		return null;
	}

	@Override
	public Map<String, XMLElement> getClassMap() {
		throw new UnsupportedOperationException("getClassMap is not (yet?) implented in OSGi based class manager implementation");
	}

	@Override
	public Map<String, FunctionDefinition> getFunctionDefMap() {
		return null;
	}

	@Override
	public void clearClassMap() {

	}

	@Override
	public void addTipiClassDefinition(XMLElement xe, ExtensionDefinition ed) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if(!(ed instanceof TipiExtension)) {
			throw new UnsupportedOperationException("Can not append tipi element with non-tipi extension type");
		}
		ServiceRegistrationUtils.registerTipiElement(xe, (TipiExtension) ed, this.myBundleContext);
	}

	@Override
	public Set<String> getClassNameSet() {
		Set<String> result = new HashSet<String>();
		try {
			Collection<ServiceReference<XMLElement>> aa = myBundleContext
					.getServiceReferences(XMLElement.class,"(type=tipiclass)");
			for (ServiceReference<XMLElement> serviceReference : aa) {
				result.add((String) serviceReference.getProperty("name"));
			}

		} catch (InvalidSyntaxException e) {
			logger.error("Error: ",e);
			return null;
		}

		return result;
	}

	/**
	 * 
	 */
	public Class<?> getTipiClass(XMLElement xe) {
//		String pack = (String) xe.getAttribute("package");
		String clas = (String) xe.getAttribute("class");
//		String name = (String) xe.getAttribute("name");
		if(clas==null) {
			return null;
		}
		Class<?> classInstance = (Class<?>) xe.getObjectAttribute("classInstance");
		if(classInstance==null) {
			logger.warn("Oh dear, containing class not found "+xe);
		}
		return classInstance;
	}
	
	@Override
	public ExtensionDefinition getExtension(String extensionName) {
		try {
			Collection<ServiceReference<TipiExtension>> aa = myBundleContext
					.getServiceReferences(TipiExtension.class,"(&(type=tipiExtension)(extensionId="+extensionName+"))");
			if(aa.isEmpty()) {
				return null;
			}
			ServiceReference<TipiExtension> xe = aa.iterator().next();
			return myBundleContext.getService(xe);
			
		} catch (InvalidSyntaxException e) {
			logger.error("Error: ",e);
		}

		return null;
	}

	@Override
	public void addFunctionDefinition(String name, FunctionDefinition fd) {

	}
	@Override
	public TipiTypeParser parseParser(XMLElement xe, ExtensionDefinition te) {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		ServiceRegistrationUtils.parseParser(myBundleContext,props,xe,(TipiExtension) te);
		TipiTypeParser ttp = super.parseParser(xe,te);
		return ttp;
	}
	
	public TipiTypeParser getParser(String name) {
		TipiTypeParser ttp = super.getParser(name);
		if(ttp!=null) {
			return ttp;
		}
		Collection<ServiceReference<TipiTypeParser>> aa;
		try {
			aa = myBundleContext.getServiceReferences(TipiTypeParser.class,"(&(type=tipi-parser)(name="+name+"))");
			if(aa.isEmpty()) {
				return null;
			}
			ServiceReference<TipiTypeParser> xe = aa.iterator().next();
			ttp = myBundleContext.getService(xe);
			return ttp;
		} catch (InvalidSyntaxException e) {
			logger.error("Error: ",e);
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
	
	public boolean isValidType(String name) {
		boolean isV = super.isValidType(name);
		if(isV) {
			return isV;
		}
		Collection<ServiceReference<TipiTypeParser>> aa;
			try {
				aa = myBundleContext.getServiceReferences(TipiTypeParser.class,"(&(type=tipi-parser)(name="+name+"))");
				if(aa.isEmpty()) {
					return false;
				}
			} catch (InvalidSyntaxException e) {
				return false;
			}
			return true;
	}
	@Override
	public FunctionDefinition getFunction(String name) {
		logger.error("getFunction not yet implemented!");
		return null;
	}
}
