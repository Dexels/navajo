package com.dexels.navajo.functions.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import navajocore.Version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.expression.api.FunctionDefinition;
import com.dexels.navajo.expression.api.FunctionInterface;

public class OsgiFunctionFactory extends JarFunctionFactory {

	private static final long serialVersionUID = 7044347052933946219L;
	private static final Logger logger = LoggerFactory
			.getLogger(OsgiFunctionFactory.class);

	@Override
	public FunctionInterface getInstance(final ClassLoader cl, final String functionName)  {
		FunctionDefinition fd = (FunctionDefinition) getComponent(functionName, "functionName", FunctionDefinition.class);
		if(fd==null) {
			logger.debug("OSGi function resolution for function: {} failed, going old school.",functionName);
			return super.getInstance(cl, functionName);
		}
		FunctionInterface osgiResolution = fd.getFunctionInstance();
		if (osgiResolution==null) {
			logger.debug("OSGi function resolution for function: {} failed, going old school.",functionName);
			return super.getInstance(cl, functionName);
		}
		return osgiResolution;
	}
	
	
	@Override
	public void init() {
		Map<String, FunctionDefinition> fuds = getDefaultConfig();
		if(fuds==null) {
			fuds = new HashMap<>();
			setDefaultConfig(fuds);
		}
	}


	@SuppressWarnings("rawtypes")
	@Override
	public List<XMLElement> getAllFunctionElements(String interfaceClass, String propertyKey)  {
		List<XMLElement> result = new ArrayList<>();
		BundleContext context = Version.getDefaultBundleContext();
		try {
			ServiceReference[] refs = context.getServiceReferences(interfaceClass, null);
			if(refs==null) {
				logger.warn("Service enumeration failed class: {}", interfaceClass);
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
	//TODO not ok, seems to return function elements
	public List<XMLElement> getAllAdapterElements(String interfaceClass, String propertyKey)  {
		List<XMLElement> result = new ArrayList<>();
		BundleContext context = Version.getDefaultBundleContext();
		try {
			ServiceReference[] refs = context.getServiceReferences(interfaceClass, null);
			if(refs==null) {
				logger.warn("Service enumeration failed class: {}", interfaceClass);
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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getComponent( final String name, String serviceKey, Class interfaceClass)  {
		BundleContext context = navajocore.Version.getDefaultBundleContext();
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
				logger.info("OSGi failed. Going old skool");
				return super.getAdapterClass(adapterClassName, cl);
			} else {
				return osgiResolution;
			}
	}	
	
	@Override
	public FunctionDefinition parseFunction(Map<String, FunctionDefinition> fuds,
			 XMLElement element) {
		return super.parseFunction(fuds, element);
	}
}
