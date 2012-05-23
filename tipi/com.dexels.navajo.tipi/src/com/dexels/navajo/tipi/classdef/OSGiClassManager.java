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

	public OSGiClassManager(BundleContext bc, TipiContext tc) {
		super(tc);
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
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, XMLElement> getClassMap() {
		throw new UnsupportedOperationException("getClassMap is not (yet?) implented in OSGi based class manager implementation");
	}

	@Override
	public Map<String, FunctionDefinition> getFunctionDefMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearClassMap() {
		// TODO Auto-generated method stub

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
			e.printStackTrace();
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
//		String fullDef = pack + "." + clas;
//		ExtensionDefinition ed = getExtension(name);
		Class<?> classInstance = (Class<?>) xe.getObjectAttribute("classInstance");
		if(classInstance==null) {
			System.err.println("Oh dear, containing class not found "+xe);
		}
		return classInstance;
		//		try {
//			if (b) {
//				ClassLoader cl = b.getBundleContext().ge .getClass().getClassLoader();
//				cc = Class.forName(fullDef, true, cl);
//				return cc;
//			}
//			System.err
//					.println("FALLBACK: Loading class without Extension definition");
//			
//			cc = Class.forName(fullDef, true, myContext.getClassLoader());
//		} catch (ClassNotFoundException ex) {
//			System.err.println("Error loading class: " + fullDef);
//			ex.printStackTrace();
//		} catch (SecurityException ex) {
//			System.err.println("Security Error loading class: " + fullDef);
//			ex.printStackTrace();
//
//		}
//		return cc;
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
			e.printStackTrace();
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
//		System.err.println("Ignoring inline parser!");
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
			e.printStackTrace();
		}
		return null;
	}

	public Object parse(TipiComponent source, String name, String expression,
			TipiEvent te) {
		TipiTypeParser ttp = getParser(name);

		

		if (ttp == null) {
			System.err.println("Unknown type: " + name);
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
}
