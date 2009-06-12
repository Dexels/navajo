package com.dexels.navajo.mapping.compiler.meta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

import javax.imageio.spi.ServiceRegistry;

import navajo.ExtensionDefinition;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
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
@SuppressWarnings("unchecked")
public class MapMetaData {

	protected HashMap<String, MapDefinition> maps = new HashMap<String, MapDefinition>();
	
	private static MapMetaData instance = null;
	private String configPath = null;
	
	private MapMetaData(String configPath) {
		// Create empty MapDefinition.
		MapDefinition empty = new MapDefinition(this);
		empty.tagName = "__empty__";
		empty.objectName = "null";
		this.configPath = configPath;
		maps.put("__empty__", empty);
	}
	
	private void readConfig() throws Exception {

		synchronized (instance) {

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
					//System.err.println("FOUND POSSIBLE ADAPTER EXTENSION: " + ed);
					
					BufferedReader br = new BufferedReader(new InputStreamReader(ed.getDefinitionAsStream()));

					XMLElement config = new CaseSensitiveXMLElement();
					config.parseFromReader(br);
					br.close();
					
				
					if ( config.getName().equals("adapterdef")) {
						Vector allmaps = config.getElementsByTagName("map");
						//System.err.println("Found " + allmaps.size() + " map definitions");
						for ( int i = 0; i < allmaps.size(); i++ ) {
							XMLElement map = (XMLElement) allmaps.get(i);
							MapDefinition md = MapDefinition.parseDef(map);
							maps.put(md.tagName, md);
						}
					}
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}
	
	public static MapMetaData getInstance() throws Exception {
		return getInstance("aap");
	}
	
	public static MapMetaData getInstance(String c) throws Exception {
		if ( instance != null ) {
			return instance;
		} else {
			instance = new MapMetaData(c);
            // Read map definitions from config file.
			instance.readConfig();
		}
		return instance;
	}
	
	public Set<String> getMapDefinitions() {
		return maps.keySet();
	}
	
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
	
	public String parse(String fileName) throws Exception {
		File f = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(f));
		XMLElement in = new CaseSensitiveXMLElement();
		
		
		in.parseFromReader(br);
		br.close();
		
		// Remember tsl attributes.
		HashMap<String,String> tslAttributes = new HashMap<String, String>();
		Iterator all = in.enumerateAttributeNames();
		while ( all.hasNext() ) {
			String name = all.next().toString();
			String value = in.getAttribute(name)+"";
			tslAttributes.put(name, value);
		}
		
		XMLElement result = new CaseSensitiveXMLElement();
		result.setName("tsl");
		
		generateCode(in, result, f.getName());

		// Reinsert tsl attributes.
		all = tslAttributes.keySet().iterator();
		while ( all.hasNext() ) {
			String name = all.next().toString();
			String value = tslAttributes.get(name);
			result.setAttribute(name, value);
		}
		
		StringWriter sw = new StringWriter();
		result.write(sw);

		return sw.toString();
	}
	
	public static boolean isMetaScript(String script, String scriptPath, String packagePath) {
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
		
		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
		MapMetaData mmd = MapMetaData.getInstance("/home/arjen/projecten/Navajo");
		//System.err.println("is: " + mmd.isMetaScript("ProcessQueryMemberNewStyle", "/home/arjen/projecten/Navajo/", "."));
		
		String result = mmd.parse("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/scripts/matchform/Test.xml");
		
		FileWriter fw = new FileWriter("/home/arjen/@.xml");
	
		fw.write(result);
		fw.close();
		
	}
}
