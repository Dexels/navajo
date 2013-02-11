package com.dexels.navajo.functions.util;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.imageio.spi.ServiceRegistry;

import navajo.ExtensionDefinition;
import navajoexpression.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.expression.meta.MapMetaDataListenerFactory;

public class JarFunctionFactory extends FunctionFactoryInterface implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5200898919345188706L;
	private final static Logger logger = LoggerFactory
			.getLogger(JarFunctionFactory.class);
	
	
	@Override
	public final void readDefinitionFile(Map<String, FunctionDefinition> fuds, ExtensionDefinition fd) {
		// Read config file.
		CaseSensitiveXMLElement xml = new CaseSensitiveXMLElement();
			
		try {
			InputStream fis = fd.getDefinitionAsStream();
			xml.parseFromStream(fis);
			fis.close();
//			if (!( xml.getName().equals("functiondef") || xml.getName().equals("tid"))) {
//				return;
//			}
			
			
			Vector<XMLElement> children = xml.getChildren();
			for (int i = 0; i < children.size(); i++) {
				// Get object, usage and description.
				XMLElement element = children.get(i);
				
				if(element.getName().equals("function")) {
					parseFunction(fuds, element);
				}
				if(element.getName().equals("map")) {
					parseAdapters(fuds, fd, element);
				}

			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void parseAdapters(Map<String, FunctionDefinition> fuds, ExtensionDefinition fd, XMLElement element) {
		String name = element.getElementByTagName("tagname").getContent();
		String className = element.getElementByTagName("object").getContent();
		if(fd!=null) {
			FunctionDefinition functionDefinition = new FunctionDefinition(className, null, null, null);
			getAdapterConfig(fd).put(name, functionDefinition);
			try {
				MapMetaDataListenerFactory.getInstance().addMapDefinition(element);
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
//			if ( name != null ) {
//
//				functionDefinition.setXmlElement(element);
//				fuds.put(name, functionDefinition);
//				
//			}

		} else {
			throw new UnsupportedOperationException("Can not register adapter (pre-OSGi) without a ExtensionDefinition.");
		}
	}
	
	public void parseFunction(Map<String, FunctionDefinition> fuds,
			XMLElement element) {
		Vector<XMLElement> def = element.getChildren();
		String name = (String) element.getAttribute("name");
		String object = (String) element.getAttribute("class");
		String description = null;
		String inputParams = null;
		String resultParam = null;
		for (int j = 0; j < def.size(); j++) {
			// TODO Check tag name?
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
			FunctionDefinition functionDefinition = new FunctionDefinition(object, description, inputParams, resultParam);
			functionDefinition.setXmlElement(element);
			fuds.put(name, functionDefinition);
			
		}
	}
	
	@Override
	public void init() {
		Map<String, FunctionDefinition> fuds = getDefaultConfig();
		if(fuds==null) {
			fuds = new HashMap<String, FunctionDefinition>();
			setDefaultConfig(fuds);
		}
		ClassLoader myClassLoader = FunctionFactoryFactory.getLegacyClassLoader();
		
		if ( myClassLoader == null ) {
			myClassLoader = getClass().getClassLoader();
		}
		
		
		try {
			// TODO Detect OSGi mode
			Iterator<?> iter = ServiceRegistry.lookupProviders(Class.forName("navajo.ExtensionDefinition", true, myClassLoader),myClassLoader);
			while(iter.hasNext()) {
				ExtensionDefinition ed = (ExtensionDefinition) iter.next();
				readDefinitionFile(fuds, ed);
			}
		} catch (Throwable e) {
			logger.debug("ServiceLookup failed. Normal in OSGi environment",e);
			if(!Version.osgiActive()) {
				logger.error("But OSGi isn't active, so something is definitely wrong.",e);
			}
		}
	}
	
	public static void main(String [] args) throws Exception {
		
		JarFunctionFactory jff = new JarFunctionFactory();
		jff.init();
	}

}
