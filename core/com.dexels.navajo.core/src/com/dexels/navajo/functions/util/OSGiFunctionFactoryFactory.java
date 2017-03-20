package com.dexels.navajo.functions.util;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.parser.FunctionInterface;


public class OSGiFunctionFactoryFactory  {
    private static Map<String, FunctionDefinition> cache = new HashMap<>();
	private final static Logger logger = LoggerFactory.getLogger(OSGiFunctionFactoryFactory.class);
	
	private OSGiFunctionFactoryFactory() {
		// no instances
	}
	
	public static FunctionInterface getFunctionInterface(final String functionName)  {
	    
	    if (cache.containsKey(functionName)) {
            return cache.get(functionName).getFunctionInstance();
        }
		FunctionDefinition fd = (FunctionDefinition) getComponent(functionName,
				"functionName", FunctionDefinition.class);
		if(fd==null) {
			throw NavajoFactory.getInstance().createNavajoException("No such function: "+functionName);
		}
        
		cache.put(functionName, fd);
		FunctionInterface instance = fd.getFunctionInstance();
		return instance;
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
		logger.error("Service resolution failed: No references found for query: "+"("+serviceKey+"="+name+")"+" class: "+interfaceClass.getName());
		return null;
	}
}
