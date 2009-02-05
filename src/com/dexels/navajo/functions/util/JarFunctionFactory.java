package com.dexels.navajo.functions.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.spi.ServiceRegistry;

import navajo.ExtensionDefinition;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.server.DispatcherFactory;

public class JarFunctionFactory extends FunctionFactoryInterface {

	private final void readDefinitionFile(HashMap<String, FunctionDefinition> fuds, ExtensionDefinition fd) {
		// Read config file.
		CaseSensitiveXMLElement xml = new CaseSensitiveXMLElement();
		try {
			InputStream fis = fd.getDefinitionAsStream();
			xml.parseFromStream(fis);
			fis.close();
			
			Vector<XMLElement> children = xml.getChildren();
			for (int i = 0; i < children.size(); i++) {
				// Get object, usage and description.
				XMLElement function = children.get(i);
				Vector<XMLElement> def = function.getChildren();
				String name = (String) function.getAttribute("name");
				String object = (String) function.getAttribute("class");
				String description = null;
				String inputParams = null;
				String resultParam = null;
				for (int j = 0; j < def.size(); j++) {
					
					if ( def.get(j).getName().equals("description")) {
						description =  def.get(j).getContent();
					}
					if ( def.get(j).getName().equals("input")) {
						inputParams =  def.get(j).getContent();
					}
					if ( def.get(j).getName().equals("result")) {
						resultParam =  def.get(j).getContent();
					}
				}
				if ( name != null ) {
					fuds.put(name, new FunctionDefinition(object, description, inputParams, resultParam));
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void init() {
		HashMap<String, FunctionDefinition> fuds = new HashMap<String, FunctionDefinition>();
		ClassLoader myClassLoader = null;
		if ( DispatcherFactory.getInstance() != null ) {
			myClassLoader = DispatcherFactory.getInstance().getNavajoConfig().getClassloader();
		} else {
			myClassLoader = getClass().getClassLoader();
		}
		
		
		try {
			Iterator iter = ServiceRegistry.lookupProviders(Class.forName("navajo.ExtensionDefinition", true, myClassLoader), 
					                                        myClassLoader);
			while(iter.hasNext()) {
				ExtensionDefinition ed = (ExtensionDefinition) iter.next();
				System.err.println(ed);
				readDefinitionFile(fuds, ed);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setConfig(fuds);
	}
	
	public static void main(String [] args) throws Exception {
		
		JarFunctionFactory jff = new JarFunctionFactory();
		jff.init();
	}

}
