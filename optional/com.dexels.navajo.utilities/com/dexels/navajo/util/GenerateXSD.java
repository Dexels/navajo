package com.dexels.navajo.util;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.mapping.compiler.meta.ParameterDefinition;
import com.dexels.navajo.mapping.compiler.meta.ValueDefinition;

public class GenerateXSD {

	private String [] inherited = new String[]{"message", "property", "include", "param", "methods", "map", "break", "debug", "field"};
	
	private XMLElement createAdapterXSD(String adapterName) throws Exception {
		
		CaseSensitiveXMLElement basic = new CaseSensitiveXMLElement("xs:element");
		basic.setAttribute("name", "map." + adapterName);
		CaseSensitiveXMLElement type = new CaseSensitiveXMLElement("xs:complexType");
		basic.addChild(type);
		
		CaseSensitiveXMLElement choice = new CaseSensitiveXMLElement("xs:choice");
		choice.setAttribute("minOccurs", "0");
		choice.setAttribute("maxOccurs", "unbounded");
		type.addChild(choice);
		// Add inherited elements
		for (int i = 0; i < inherited.length; i++) {
			CaseSensitiveXMLElement x = new CaseSensitiveXMLElement("xs:element");
			x.setAttribute("ref", inherited[i]);
			choice.addChild(x);
		}
		// Add adapters:insertedadapters
		CaseSensitiveXMLElement insertedadapters = new CaseSensitiveXMLElement("adapters:insertedadapters");
		choice.addChild(insertedadapters);
		
		// Generate setters.
		Iterator setters = MapMetaData.getInstance().getMapDefinition(adapterName).getValueDefinitions().iterator();
		while ( setters.hasNext() ) {
			CaseSensitiveXMLElement x = new CaseSensitiveXMLElement("xs:element");
			ValueDefinition vd = (ValueDefinition) MapMetaData.getInstance().getMapDefinition(adapterName).getValueDefinition((String) setters.next());
			x.setAttribute("type", "SetterType");
			x.setAttribute("name", adapterName + "." + vd.getName());
			choice.addChild(x);
			
			CaseSensitiveXMLElement xa = new CaseSensitiveXMLElement("xs:attribute");
			xa.setAttribute("name", vd.getName());
			xa.setAttribute("type", "xs:string");
			if ( vd.getRequired().equals("true") ) {
				xa.setAttribute("use", "required");
			}
			type.addChild(xa);
		}
		
		CaseSensitiveXMLElement conditionAttr = new CaseSensitiveXMLElement("xs:attribute");
		conditionAttr.setAttribute("name", "condition");
		conditionAttr.setAttribute("type", "xs:string");
		type.addChild(conditionAttr);
		
		// Generate methods.
		Iterator methods = MapMetaData.getInstance().getMapDefinition(adapterName).getMethodDefinitions().iterator();
		while ( methods.hasNext() ) {
			CaseSensitiveXMLElement x = new CaseSensitiveXMLElement("xs:element");
			String method = (String) methods.next();
			x.setAttribute("name", adapterName + "." + method);
			choice.addChild(x);
			CaseSensitiveXMLElement typex = new CaseSensitiveXMLElement("xs:complexType");
			x.addChild(typex);
			// Add parameters.
			int count = 0;
			Iterator params =  MapMetaData.getInstance().getMapDefinition(adapterName).getMethodDefinition(method).getParameters().iterator();
			while ( params.hasNext() ) {
				ParameterDefinition pd = MapMetaData.getInstance().getMapDefinition(adapterName).getMethodDefinition(method).getParameterDefinition((String) params.next());
				
				if ( !pd.getRequired().equals("automatic") ) {
					CaseSensitiveXMLElement xp = new CaseSensitiveXMLElement("xs:attribute");
					xp.setAttribute("name", pd.getName());
					xp.setAttribute("type", "xs:string");
					if ( pd.getRequired().equals("true") ) {
						xp.setAttribute("use", "required");
					}
					typex.addChild(xp);
					count++;
				}
			}
			if ( count == 0 ) {
				x.removeChild(typex);
			}
		}
		
		return basic;
		
	}
	
	public void findTags(XMLElement xml, String tagName, ArrayList<XMLElement> tags) {
		
		Vector<XMLElement> v = xml.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement x = v.get(i);
			if ( x.getName().equals(tagName)) {
				tags.add(x);
			}
			findTags(x, tagName, tags);
		}
	}
	
	public String generateXSD() throws Exception {
		
		CaseSensitiveXMLElement xml = new CaseSensitiveXMLElement();

		InputStreamReader fis = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("basenavascript.xsd"));
		xml.parseFromReader(fis);
		fis.close();
		
		
		MapMetaData mmd = MapMetaData.getInstance();
		Set maps = mmd.getMapDefinitions();
		
		Iterator all = maps.iterator();
		while ( all.hasNext() ) {
			
			String mappie = (String) all.next();
			if ( !mappie.equals("__empty__")) {
				XMLElement x = createAdapterXSD(mappie);
				xml.addChild(x);
			}
			
		}
		
		
		ArrayList<XMLElement> tags = new ArrayList<XMLElement>();
		findTags(xml, "adapters:insertedadapters", tags);
		//System.err.println(tags.size());
	

		for (int i = 0; i < tags.size(); i++) {
			
			
			XMLElement x = tags.get(i);
			XMLElement parent = x.getParent();
			
			// <xs:element ref="map.sqlquery"/> 
			all = maps.iterator();
			while ( all.hasNext() ) {
				
				String mappie = (String) all.next();
				if ( !mappie.equals("__empty__")) {
					CaseSensitiveXMLElement replace = new CaseSensitiveXMLElement("xs:element");
					replace.setAttribute("ref", "map." + mappie);
					parent.addChild(replace);
				}
				
			}
			
			parent.removeChild(x);
			
		}
		
		StringWriter sb = new StringWriter();
		xml.write(sb);
		//System.err.println(sb.toString());
		return sb.toString();
	}
	
	public Binary getNavajoXSD() throws Exception {
		
		GenerateXSD xsd = new GenerateXSD();
		String result = xsd.generateXSD();
		Binary b = new Binary(result.getBytes());
		b.setMimeType("text/tml");
		return b;
		
	}
	
	public static void main(String [] args) throws Exception {
		
		//String baseXSD = "/home/arjen/projecten/Navajo/schemas/basenavascript.xsd"; // args[0];
		MapMetaData mmd = MapMetaData.getInstance("navajo");
		
		GenerateXSD xsd = new GenerateXSD();
		String result = xsd.generateXSD();
		System.out.println(result);
		
		//xsd.createAdapterXSD("sqlquery", new CaseSensitiveXMLElement());

	}

}
