package com.dexels.navajo.mapping.compiler.meta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MapMetaData {

	protected HashMap<String, MapDefinition> maps = new HashMap<String, MapDefinition>();
	
	private static MapMetaData instance = null;
	
	private MapMetaData() {
		// Create empty MapDefinition.
		MapDefinition empty = new MapDefinition(this);
		empty.tagName = "__empty__";
		empty.objectName = "null";
		maps.put("__empty__", empty);
	}
	
	private void readConfig() throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("/home/arjen/projecten/Navajo/adapters.xml")));
			XMLElement config = new CaseSensitiveXMLElement();
			config.parseFromReader(br);
			br.close();
			Vector allmaps = config.getElementsByTagName("map");
			System.err.println("Found " + allmaps.size() + " map definitions");
			for ( int i = 0; i < allmaps.size(); i++ ) {
				XMLElement map = (XMLElement) allmaps.get(i);
				MapDefinition md = MapDefinition.parseDef(map);
			    maps.put(md.tagName, md);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public static MapMetaData getInstance() throws Exception {
		if ( instance != null ) {
			return instance;
		} else {
			instance = new MapMetaData();
            // Read map definitions from config file.
			instance.readConfig();
		}
		return instance;
	}
	
	public MapDefinition getMapDefinition(String name) {
		return maps.get(name);
	}
	
	private void generateCode(XMLElement in, XMLElement out, String filename) throws Exception {
		maps.get("__empty__").generateCode(in, out, filename);
	}
	
	protected String getFileName(XMLElement e) {
		System.err.println(">>>>>>>>> e = " + e.getFirstChild());
		return (String) e.getFirstChild().getAttribute("filename");
	}
	
	public void parse(String fileName) throws Exception {
		File f = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(f));
		XMLElement in = new CaseSensitiveXMLElement();
		in.parseFromReader(br);
		br.close();
		
		XMLElement result = new CaseSensitiveXMLElement();
		result.setName("tsl");
		generateCode(in, result, f.getName());

		StringWriter sw = new StringWriter();
		result.write(sw);

		System.err.println(sw.toString());
	}
	
	public static void main(String [] args) throws Exception {
		
		MapMetaData mmd = MapMetaData.getInstance();
		mmd.parse("/home/arjen/projecten/Navajo/navascript.xml");
		
	}
}
