package com.dexels.navajo.functions.util;

import java.util.HashMap;
import java.util.Set;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

import dexels.NavajoBundleManager;

import navajo.ExtensionDefinition;
import org.osgi.framework.*;

public class OsgiFunctionFactory extends JarFunctionFactory {



	public FunctionInterface getInstance(final ClassLoader cl, final String functionName) throws TMLExpressionException {
		System.err.println("GETTINH FUNCTION FROM OSHI!!!!: "+functionName);
		BundleContext context = NavajoBundleManager.getInstance().getBundleContext();
		try {
			ServiceReference[] refs = context.getServiceReferences(FunctionInterface.class.getName(), "(functionName="+functionName+")");
			FunctionInterface function = (FunctionInterface) context.getService(refs[0]);
			
			return function;
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
