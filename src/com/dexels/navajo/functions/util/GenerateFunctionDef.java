package com.dexels.navajo.functions.util;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.parser.FunctionInterface;

public class GenerateFunctionDef {

	public static Class[] getClasses(String pckgname) throws ClassNotFoundException {
		ArrayList<Class> classes = new ArrayList<Class>();
//		Get a File object for the package
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			String path = pckgname.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			directory = new File(resource.getFile());
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname + " (" + directory
					+ ") does not appear to be a valid package");
		}
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					classes.add(Class.forName(pckgname + '.'
							+ files[i].substring(0, files[i].length() - 6)));
				}
			}
		} else {
			throw new ClassNotFoundException(pckgname
					+ " does not appear to be a valid package");
		}
		Class[] classesA = new Class[classes.size()];
		classes.toArray(classesA);
		return classesA;
	}


	/**
	 *  <function>
        <name>Abs</name>
        <object>com.dexels.navajo.functions.Abs</object>
        <description>Returns absolute value of a number</description>
        <usage>Abs(Float|Integer)</usage>
        </function>
	 */
	public static XMLElement generateFunctionDefinition(Class c) throws Exception{
		
		XMLElement def = new CaseSensitiveXMLElement("function");
		def.setAttribute("name", c.getSimpleName());
		def.setAttribute("class", c.getName());
		XMLElement description = new CaseSensitiveXMLElement("description");
		def.addChild(description);
		try {
			FunctionInterface fi = (FunctionInterface) c.newInstance();
			description.setContent(fi.remarks());
		} catch (Exception e)  {

		}
		XMLElement input = new CaseSensitiveXMLElement("input");
		def.addChild(input);
		try {
			FunctionInterface fi = (FunctionInterface) c.newInstance();
			Class [][] inputTypes = fi.getTypes();
			if ( inputTypes != null ) {
				NavajoFactory nf = NavajoFactory.getInstance();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < inputTypes.length; i++) {
					// list types.
					for (int j = 0; j < inputTypes[i].length; j++) {
						sb.append(nf.getNavajoType(inputTypes[i][j]));
						if ( j < inputTypes[i].length - 1) {
							sb.append("|");
						}
					}
					if ( i < inputTypes.length - 1 ) {
						sb.append(",");
					}
				}
				input.setContent(sb.toString());
			}
		} catch (Exception e)  {
			//e.printStackTrace(System.err);
		}
		
		XMLElement result = new CaseSensitiveXMLElement("result");
		def.addChild(result);
		try {
			FunctionInterface fi = (FunctionInterface) c.newInstance();
			Class [] returnTypes = fi.getReturnType();
			if ( returnTypes != null ) {
				NavajoFactory nf = NavajoFactory.getInstance();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < returnTypes.length; i++) {
					sb.append(nf.getNavajoType(returnTypes[i]));
					if ( i < returnTypes.length - 1) {
						sb.append("|");
					}
				}
				result.setContent(sb.toString());
			}
		} catch (Exception e)  {
			//e.printStackTrace(System.err);
		}
		
		
		
		
		return def;
	}

	public static void main(String [] args) throws Exception {
		
		CaseSensitiveXMLElement functions = new CaseSensitiveXMLElement("functiondef");
		
		Class [] all = getClasses("com.dexels.navajo.functions");
		for (int i = 0; i < all.length; i++) {
			System.err.println(all[i]);
			if (FunctionInterface.class.isAssignableFrom(all[i]) ) {
				XMLElement def = generateFunctionDefinition(all[i]);
				functions.addChild(def);
			}
		}
		StringWriter sw = new StringWriter();
		functions.write(sw);
		sw.close();
		System.err.println(sw.toString());
		
	}
}
