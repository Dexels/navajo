package com.dexels.navajo.functions.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import navajo.Version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.version.ExtensionDefinition;

public class OsgiFunctionFactory extends JarFunctionFactory {

	private static final long serialVersionUID = 7044347052933946219L;
	private final static Logger logger = LoggerFactory
			.getLogger(OsgiFunctionFactory.class);

	public FunctionInterface getInstance(final ClassLoader cl, final String functionName) throws TMLExpressionException  {
		FunctionInterface osgiResolution = (FunctionInterface) getComponent(functionName, "functionName", FunctionInterface.class);

		if (osgiResolution==null) {
			System.err.println("OSGi failed. Going old skool");
			return super.getInstance(cl, functionName);
		} else {
			return osgiResolution;

		}
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
			e.printStackTrace();
		}
		return result;
	}
	
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
			e.printStackTrace();
		}
		return result;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getComponent( final String name, String serviceKey, Class interfaceClass)  {
		BundleContext context = navajo.Version.getDefaultBundleContext();
		try {
			ServiceReference[] refs = context.getServiceReferences(interfaceClass.getName(), "("+serviceKey+"="+name+")");
			if(refs==null) {
				logger.error("Service resolution failed: Query: "+"("+serviceKey+"="+name+")"+" class: "+interfaceClass.getName());
				return null;
			}
			return context.getService(refs[0]);
			
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public  Class<?> getAdapterClass(String adapterClassName, ClassLoader cl) throws ClassNotFoundException {
			Class osgiResolution = (Class) getComponent(adapterClassName, "adapterClass", Class.class);
			if (osgiResolution==null) {
				System.err.println("OSGi failed. Going old skool");
				return super.getAdapterClass(adapterClassName, cl);
			} else {
				return osgiResolution;
			}
	}	
	
	@Override
	public void parseFunction(Map<String, FunctionDefinition> fuds,
			ExtensionDefinition fd, XMLElement element) {
		super.parseFunction(fuds, fd, element);
		
//		Vector<XMLElement> def = element.getChildren();
//		String name = (String) element.getAttribute("name");
//		String object = (String) element.getAttribute("class");
//		String description = null;
//		String inputParams = null;
//		String resultParam = null;
//		for (int j = 0; j < def.size(); j++) {
//			// TODO Check tag name?
//			if ( def.get(j).getName().equals("description")) {
//				description =  def.get(j).getContent();
//			}
//			if ( def.get(j).getName().equals("input")) {
//				inputParams =  def.get(j).getContent();
//			}
//			if ( def.get(j).getName().equals("result")) {
//				resultParam =  def.get(j).getContent();
//			}
//		}
//		if ( name != null ) {
//			FunctionDefinition functionDefinition = new FunctionDefinition(object, description, inputParams, resultParam,fd);
//			functionDefinition.setXmlElement(element);
//			fuds.put(name, functionDefinition);
//			
//		}
	}
}
