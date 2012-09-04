package com.dexels.navajo.functions.util;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


public class OSGiFunctionFactoryFactory  {

	private final static Logger logger = LoggerFactory
			.getLogger(OSGiFunctionFactoryFactory.class);
	
	private OSGiFunctionFactoryFactory() {
		// no instances
	}
	
	public static FunctionInterface getFunctionInterface(final String functionName)  {
		FunctionInterface osgiResolution = (FunctionInterface) getComponent(functionName, "functionName", FunctionInterface.class);
		return osgiResolution;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getComponent( final String name, String serviceKey, Class interfaceClass)  {
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
}