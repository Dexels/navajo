package com.dexels.navajo.mapping.compiler.meta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.util.AuditLog;

import navajo.ExtensionDefinition;

/**
 * This class holds the metadata for adapters that can be used in new navasript 'style' scripts.
 * 
 * @author arjen
 *
 */
public class MapMetaData {

	protected final Map<String, MapDefinition> maps = new HashMap<>();
	
	private static MapMetaData instance = null;
	

	private static final Logger logger = LoggerFactory
			.getLogger(MapMetaData.class);
	
	private MapMetaData() {
		// Create empty MapDefinition.
		MapDefinition empty = new MapDefinition(this);
		empty.tagName = "__empty__";
		empty.objectName = "null";
		maps.put("__empty__", empty);
	}
	
	private void readConfig() throws ClassNotFoundException, KeywordException {

		synchronized (instance) {

			ClassLoader myClassLoader = null;
			if ( DispatcherFactory.getInstance() != null ) {
				myClassLoader = DispatcherFactory.getInstance().getNavajoConfig().getClassloader();
			} else {
				myClassLoader = getClass().getClassLoader();
			}
			Iterator<?> iter = null;
			try {
				ServiceLoader<ExtensionDefinition> loader = ServiceLoader.load(ExtensionDefinition.class,myClassLoader);
				iter = loader.iterator();
//				ServiceRegistry.lookupProviders(Class.forName("navajo.ExtensionDefinition", true, myClassLoader), 
//						                                        myClassLoader);
				while(iter.hasNext()) {
					ExtensionDefinition ed = (ExtensionDefinition) iter.next();
					
					BufferedReader br = new BufferedReader(new InputStreamReader(ed.getDefinitionAsStream(),StandardCharsets.UTF_8));

					XMLElement config = new CaseSensitiveXMLElement();
					config.parseFromReader(br);
					br.close();
					
				
					if ( config.getName().equals("adapterdef")) {
						Vector<XMLElement> allmaps = config.getElementsByTagName("map");
						for ( int i = 0; i < allmaps.size(); i++ ) {
							XMLElement map = allmaps.get(i);
							addMapDefinition(map);
						}
					}
					
				}
			} catch (IOException e) {
				logger.warn("Unable to lookup providers in lecagy service. Normal in OSGi.");
			}
		}
	}

	public MapDefinition addMapDefinition(XMLElement map) throws ClassNotFoundException, KeywordException {
		MapDefinition md = MapDefinition.parseDef(map);
		maps.put(md.tagName, md);
		return md;
	}
	
	public static synchronized MapMetaData getInstance() throws ClassNotFoundException, KeywordException {
		if ( instance != null ) {
			return instance;
		} else {
			instance = new MapMetaData();
            // Read map definitions from config file.
			instance.readConfig();
		}
		return instance;
	}
	
	public Set<String> getMapDefinitions() {
		return maps.keySet();
	}
	
	public MapDefinition getMapDefinition(String name) throws ClassNotFoundException, KeywordException {
		if ( !maps.containsKey(name) ) {
			// Try to re-read config, maybe a new definition?
			readConfig();
		}
		return maps.get(name);
	}
	
	private void generateCode(XMLElement in, XMLElement out, String filename) throws MetaCompileException, ClassNotFoundException  {
		maps.get("__empty__").generateCode(in, out, filename);
	}
	
	protected String getFileName(XMLElement e) {
		return (String) e.getFirstChild().getAttribute("filename");
	}

	public String parse(String fileName) throws IOException, MetaCompileException, ClassNotFoundException {
		File f = new File(fileName);
		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
			StringWriter sw = new StringWriter();
			parse(br, f.getName(),sw);
			return sw.toString();
				
		}
	}

	/**
	 * Parses this script from the screen, the script name is still necessary. Does not close the stream!
	 * @param scriptName
	 * @param is
	 * @return
	 * @throws MetaCompileException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws Exception
	 */
	public String parse(String scriptName, InputStream is) throws IOException, MetaCompileException, ClassNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));
		StringWriter sw = new StringWriter();
		parse(br, scriptName,sw);
		return sw.toString();
	}
	
	public void parse(Reader br, String scriptName, Writer sw) throws IOException, MetaCompileException, ClassNotFoundException {
		XMLElement in = new CaseSensitiveXMLElement();
		in.parseFromReader(br);
		br.close();
		parse(in,scriptName,sw);
	}		
	
	public void parse(XMLElement in, String scriptName, Writer sw) throws MetaCompileException, IOException, ClassNotFoundException {
		
		
		// Remember tsl attributes.
		Map<String,String> tslAttributes = new HashMap<>();
		Iterator<String> all = in.enumerateAttributeNames();
		while ( all.hasNext() ) {
			String name = all.next();
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
		result.write(sw);
	}
	
	public static boolean isMetaScript(String fullScriptPath) {
		try(InputStreamReader isr =  new InputStreamReader( new FileInputStream(fullScriptPath) , StandardCharsets.UTF_8)) {
			XMLElement x = new CaseSensitiveXMLElement();
			x.parseFromReader(isr);
			return ( x.getName().equals("navascript"));
		} catch (Exception e) {
			AuditLog.log("", "Something went wrong while in determination of metascript status of script: " + fullScriptPath + "(" + e.getMessage() + ")", Level.WARNING);
			return false;
		}
	}
}
