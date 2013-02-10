package com.dexels.navajo.functions.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import navajoexpression.Version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.parser.FunctionInterface;

public class OsgiFunctionFactory extends JarFunctionFactory {

	private static final long serialVersionUID = 7044347052933946219L;
	private final static Logger logger = LoggerFactory
			.getLogger(OsgiFunctionFactory.class);

	@SuppressWarnings("unchecked")
	public FunctionInterface getInstance(final ClassLoader cl, final String functionName)  {
		Class<? extends FunctionInterface> osgiResolutionClass = (Class<? extends FunctionInterface>) getComponent(functionName, "functionName", Class.class);

		if (osgiResolutionClass==null) {
			logger.debug("OSGi failed. Going old skool");
			return super.getInstance(cl, functionName);
		} else {
			FunctionInterface osgiResolution;
			try {
				osgiResolution = osgiResolutionClass.newInstance();
				return osgiResolution;
			} catch (InstantiationException e) {
				logger.debug("OSGi failed (InstantiationException). Going old skool",e);
				return super.getInstance(cl, functionName);
			} catch (IllegalAccessException e) {
				logger.debug("OSGi failed (IllegalAccessException). Going old skool",e);
				return super.getInstance(cl, functionName);
			}

		}
	}
	
	
	@Override
	public void init() {
		Map<String, FunctionDefinition> fuds = getDefaultConfig();
		if(fuds==null) {
			fuds = new HashMap<String, FunctionDefinition>();
			setDefaultConfig(fuds);
		}
//		super.init();
	}


	@Override
	public List<XMLElement> getAllFunctionElements(String interfaceClass, String propertyKey)  {
		List<XMLElement> result = new ArrayList<XMLElement>();
		BundleContext context = Version.getDefaultBundleContext();
		try {
			ServiceReference[] refs = context.getServiceReferences(interfaceClass, null);
			if(refs==null) {
				System.err.println("Service enumeration failed class: "+interfaceClass);
				return null;
			}
			for (ServiceReference serviceReference : refs) {
				Object o = serviceReference.getProperty(propertyKey);
				FunctionDefinition fd = (FunctionDefinition)o;
				XMLElement xe = fd.getXmlElement();
				if(xe!=null) {
					result.add(xe);
				}
			}
			
		} catch (InvalidSyntaxException e) {
			logger.error("Error: ", e);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<XMLElement> getAllAdapterElements(String interfaceClass, String propertyKey)  {
		List<XMLElement> result = new ArrayList<XMLElement>();
		BundleContext context = Version.getDefaultBundleContext();
		try {
			ServiceReference[] refs = context.getServiceReferences(interfaceClass, null);
			if(refs==null) {
				System.err.println("Service enumeration failed class: "+interfaceClass);
				return null;
			}
			for (ServiceReference serviceReference : refs) {
				Object o = serviceReference.getProperty(propertyKey);
				FunctionDefinition fd = (FunctionDefinition)o;
				XMLElement xe = fd.getXmlElement();
				if(xe!=null) {
					result.add(xe);
				}
			}
		} catch (InvalidSyntaxException e) {
			logger.error("Error: ", e);
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public Object getComponent( final String name, String serviceKey, Class interfaceClass)  {
		BundleContext context = navajoexpression.Version.getDefaultBundleContext();
		try {
			ServiceReference[] refs = context.getServiceReferences(interfaceClass.getName(), "("+serviceKey+"="+name+")");
			if(refs==null) {
				logger.error("Service resolution failed: Query: "+"("+serviceKey+"="+name+")"+" class: "+interfaceClass.getName());
				return null;
			}
			return context.getService(refs[0]);
			
		} catch (InvalidSyntaxException e) {
			logger.error("Error: ", e);
		}
		return null;
	}
	
	@Override
	public  Class<?> getAdapterClass(String adapterClassName, ClassLoader cl) throws ClassNotFoundException {
			Class<?> osgiResolution = (Class<?>) getComponent(adapterClassName, "adapterClass", Class.class);
			if (osgiResolution==null) {
				System.err.println("OSGi failed. Going old skool");
				return super.getAdapterClass(adapterClassName, cl);
			} else {
				return osgiResolution;
			}
	}	
	
	@Override
	public void parseFunction(Map<String, FunctionDefinition> fuds,
			 XMLElement element) {
		super.parseFunction(fuds, element);
	}
}
