package com.dexels.navajo.mapping.compiler.meta.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

import javax.imageio.spi.ServiceRegistry;

import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.mapping.compiler.meta.MapDefinition;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.mapping.compiler.meta.MapMetaDataFactory;
import com.dexels.navajo.mapping.compiler.meta.MethodDefinition;
import com.dexels.navajo.mapping.compiler.meta.ValueDefinition;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;
import com.dexels.navajo.util.AuditLog;

/**
 * This class holds the metadata for adapters that can be used in new navasript 'style' scripts.
 * 
 * @author arjen
 *
 */
public class MapMetaDataImpl implements MapMetaData {

	protected final HashMap<String, MapDefinition> maps = new HashMap<String, MapDefinition>();
	
//	private String configPath = null;
	

	private final static Logger logger = LoggerFactory
			.getLogger(MapMetaDataImpl.class);
	
	public MapMetaDataImpl() {
		// Create empty MapDefinition.
		MapDefinition empty = new MapDefinition(this);
		empty.tagName = "__empty__";
		empty.objectName = "null";
//		this.configPath = configPath;
		maps.put("__empty__", empty);
	}
	
	
	private MapDefinition parseDef(XMLElement e) throws Exception {

		MapDefinition md = new MapDefinition(MapMetaDataFactory.getInstance());
		
		XMLElement c = e.getElementByTagName("tagname");
		if ( c != null) {
			md.tagName = c.getContent();
		} 
		XMLElement o = e.getElementByTagName("object");
		if ( o != null ) {
			md.objectName = o.getContent();
		}
		XMLElement d = e.getElementByTagName("description");
		if ( d != null ) {
			md.description = d.getContent();
		}
		XMLElement a = e.getElementByTagName("abstract");
		if ( a != null ) {
			md.abstractMap = a.getContent().equals("true");
		}
		XMLElement valuesTag = e.getElementByTagName("values");
		if ( valuesTag != null ) {
			Vector<XMLElement> ch = valuesTag.getChildren();
			for ( int i = 0; i < ch.size(); i++ ) {
				XMLElement v = ch.get(i);
				ValueDefinition vd = ValueDefinition.parseDef(v);
				md.setValueDefinition(vd.getName(), vd);
			}
		}
		XMLElement methodsTag = e.getElementByTagName("methods");
		if ( methodsTag != null ) {
			Vector<XMLElement> ch = methodsTag.getChildren();
			for ( int i = 0; i < ch.size(); i++ ) {
				XMLElement v = ch.get(i);
				MethodDefinition mdef = MethodDefinition.parseDef(v);
				md.setMethodDefinition(mdef.getName(), mdef);
			}
		}
		
		return md;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.compiler.meta.MapMetaData#readConfig()
	 */
	public synchronized void readConfig() throws Exception {

			ClassLoader myClassLoader = null;
			if ( DispatcherFactory.getInstance() != null ) {
				myClassLoader = DispatcherFactory.getInstance().getNavajoConfig().getClassloader();
			} else {
				myClassLoader = getClass().getClassLoader();
			}
			
			try {
				Iterator<?> iter = null;
				try {
					iter = ServiceRegistry.lookupProviders(Class.forName("navajo.ExtensionDefinition", true, myClassLoader), 
							                                        myClassLoader);
				} catch (Exception e) {
					logger.warn("Unable to lookup providers in lecagy service. Normal in OSGi.");
//					e.printStackTrace();
					return;
				}
				while(iter.hasNext()) {
					logger.info("Looping");
					ExtensionDefinition ed = (ExtensionDefinition) iter.next();
					logger.info("ExtendionDef: "+ed.toString());
					//System.err.println("FOUND POSSIBLE ADAPTER EXTENSION: " + ed);
					
					BufferedReader br = new BufferedReader(new InputStreamReader(ed.getDefinitionAsStream()));

					XMLElement config = new CaseSensitiveXMLElement();
					config.parseFromReader(br);
					br.close();
					
				
					if ( config.getName().equals("adapterdef")) {
						Vector<XMLElement> allmaps = config.getElementsByTagName("map");
						//System.err.println("Found " + allmaps.size() + " map definitions");
						for ( int i = 0; i < allmaps.size(); i++ ) {
							XMLElement map = allmaps.get(i);
							addMapDefinition(map);
						}
					}
					
				}
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.compiler.meta.MapMetaData#addMapDefinition(com.dexels.navajo.document.nanoimpl.XMLElement)
	 */
	@Override
	public void addMapDefinition(XMLElement map) throws Exception {
		MapDefinition md = parseDef(map);
		maps.put(md.tagName, md);
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.compiler.meta.MapMetaData#getMapDefinitions()
	 */
	@Override
	public Set<String> getMapDefinitions() {
		return maps.keySet();
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.compiler.meta.MapMetaData#getMapDefinition(java.lang.String)
	 */
	@Override
	public MapDefinition getMapDefinition(String name) throws Exception {
		if ( !maps.containsKey(name) ) {
			// Try to re-read config, maybe a new definition?
			readConfig();
		}
		return maps.get(name);
	}
	
	private void generateCode(XMLElement in, XMLElement out, String filename) throws Exception {
		maps.get("__empty__").generateCode(in, out, filename);
	}
	
	protected String getFileName(XMLElement e) {
		//System.err.println(">>>>>>>>> e = " + e.getFirstChild());
		return (String) e.getFirstChild().getAttribute("filename");
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.compiler.meta.MapMetaData#parse(java.lang.String)
	 */
	@Override
	public String parse(String fileName) throws Exception {
		File f = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(f));
		StringWriter sw = new StringWriter();
		parse(br, f.getName(),sw);
		return sw.toString();
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.compiler.meta.MapMetaData#parse(java.lang.String, java.io.InputStream)
	 */
	@Override
	public String parse(String scriptName, InputStream is) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringWriter sw = new StringWriter();
		parse(br, scriptName,sw);
		return sw.toString();
	}
	
	private void parse(Reader br, String scriptName, Writer sw) throws Exception {
		XMLElement in = new CaseSensitiveXMLElement();
		
		in.parseFromReader(br);
		br.close();
		parse(in,scriptName,sw);
	}		
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.compiler.meta.MapMetaData#parse(com.dexels.navajo.document.nanoimpl.XMLElement, java.lang.String, java.io.Writer)
	 */
	@Override
	public void parse(XMLElement in, String scriptName, Writer sw) throws Exception {
		
		
		// Remember tsl attributes.
		HashMap<String,String> tslAttributes = new HashMap<String, String>();
		Iterator<String> all = in.enumerateAttributeNames();
		while ( all.hasNext() ) {
			String name = all.next().toString();
			String value = in.getAttribute(name)+"";
			tslAttributes.put(name, value);
		}
		
		XMLElement result = new CaseSensitiveXMLElement();
		result.setName("tsl");
		
		generateCode(in, result,scriptName);

		// Reinsert tsl attributes.
		all = tslAttributes.keySet().iterator();
		while ( all.hasNext() ) {
			String name = all.next().toString();
			String value = tslAttributes.get(name);
			result.setAttribute(name, value);
		}
//		System.err.println("TSL COMPILATION:\n"+result);
		result.write(sw);
	}
	
	public boolean isMetaScript(String script, String scriptPath, String packagePath) {
		try {
			InputStreamReader isr =  new InputStreamReader( new FileInputStream(scriptPath + "/" + packagePath + "/" + script + ".xml") );
			XMLElement x = new CaseSensitiveXMLElement();
			x.parseFromReader(isr);
			isr.close();
			return ( x.getName().equals("navascript"));
		} catch (Exception e) {
			AuditLog.log("", "Something went wrong while in determination of metascript status of script: " + script + "(" + e.getMessage() + ")", Level.WARNING);
			//e.printStackTrace(System.err);
			return false;
		}
	}
	
	public static void main(String [] args) throws Exception {
		
		new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
		MapMetaData mmd = MapMetaDataFactory.getInstance();
		//System.err.println("is: " + mmd.isMetaScript("ProcessQueryMemberNewStyle", "/home/arjen/projecten/Navajo/", "."));
		
		String result = mmd.parse("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/scripts/InitTest.xml");
		
		FileWriter fw = new FileWriter("/home/arjen/@.xml");
	
		fw.write(result);
		fw.close();
		
	}
}
