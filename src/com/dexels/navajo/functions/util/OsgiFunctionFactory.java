package com.dexels.navajo.functions.util;

import java.util.HashMap;
import java.util.Set;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

import dexels.NavajoBundleManager;

import navajo.ExtensionDefinition;
import org.osgi.framework.*;

public class OsgiFunctionFactory extends JarFunctionFactory {


	public FunctionInterface getInstance(final ClassLoader cl, final String functionName) throws TMLExpressionException  {
		FunctionInterface osgiResolution = (FunctionInterface) getComponent(functionName, "functionName", FunctionInterface.class);
		if (osgiResolution==null) {
			System.err.println("OSGi failed. Going old skool");
			return super.getInstance(cl, functionName);
		} else {
			return osgiResolution;

		}
	}
	
	
	public Object getComponent( final String name, String serviceKey, Class interfaceClass)  {
		System.err.println("GETTINH FUNCTION FROM OSHI!!!!: "+name);
		BundleContext context = NavajoBundleManager.getInstance().getBundleContext();
		try {
			ServiceReference[] refs = context.getServiceReferences(interfaceClass.getName(), "("+serviceKey+"="+name+")");
			if(refs==null) {
				System.err.println("Service resolution failed: Query: "+"("+serviceKey+"="+name+")"+" class: "+interfaceClass.getName());
			}
			return context.getService(refs[0]);
			
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public  Class<?> getAdapterClass(String adapterClassName, ClassLoader cl) {
			Class osgiResolution = (Class) getComponent(adapterClassName, "adapterClass", Class.class);
			if (osgiResolution==null) {
				System.err.println("OSGi failed. Going old skool");
				return super.getAdapterClass(adapterClassName, cl);
			} else {
				return osgiResolution;
			}
	}	
}
