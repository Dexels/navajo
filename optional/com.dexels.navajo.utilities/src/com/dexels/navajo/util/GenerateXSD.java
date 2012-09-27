package com.dexels.navajo.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
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

	private String [] inherited = new String[]{"property", "include", "param", "methods", "map", "break", "debug", "field"};
	
	private void generateMethods(XMLElement choice, String adapterName) throws Exception {
		// Generate methods.
		Iterator<String> methods = MapMetaData.getInstance().getMapDefinition(adapterName).getMethodDefinitions().iterator();
		while ( methods.hasNext() ) {
			CaseSensitiveXMLElement x = new CaseSensitiveXMLElement("xs:element");
			String method = methods.next();
			x.setAttribute("name", adapterName + "." + method);
			choice.addChild(x);
			CaseSensitiveXMLElement typex = new CaseSensitiveXMLElement("xs:complexType");
			x.addChild(typex);
			// Add parameters.
			int count = 0;
			Iterator<String> params =  MapMetaData.getInstance().getMapDefinition(adapterName).getMethodDefinition(method).getParameters().iterator();
			while ( params.hasNext() ) {
				ParameterDefinition pd = MapMetaData.getInstance().getMapDefinition(adapterName).getMethodDefinition(method).getParameterDefinition(params.next());
				
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
	}
	
	private void generateSetters(XMLElement type, XMLElement choice, String adapterName, boolean addAsAttribute) throws Exception {
		// Generate setters.
		Iterator<String> setters = MapMetaData.getInstance().getMapDefinition(adapterName).getValueDefinitions().iterator();
		while ( setters.hasNext() ) {
			CaseSensitiveXMLElement x = new CaseSensitiveXMLElement("xs:element");
			ValueDefinition vd = MapMetaData.getInstance().getMapDefinition(adapterName).getValueDefinition(setters.next());
			x.setAttribute("type", ( vd.getDirection().equals("out") ? "MapType": "SetterType") );
			x.setAttribute("name", adapterName + "." + vd.getName());
			choice.addChild(x);

			if ( addAsAttribute ) {
				CaseSensitiveXMLElement xa = new CaseSensitiveXMLElement("xs:attribute");
				xa.setAttribute("name", vd.getName());
				xa.setAttribute("type", "xs:string");
				if ( vd.getRequired().equals("true") ) {
					xa.setAttribute("use", "required");
				}
				type.addChild(xa);
			}


		}
	}
	
	private CaseSensitiveXMLElement createSpecialMessageType(String specialMessageTypeName, String adapterName) throws Exception {
		CaseSensitiveXMLElement messageType = new CaseSensitiveXMLElement();
		messageType.setName("xs:complexType");
		messageType.setAttribute("name", specialMessageTypeName);
		CaseSensitiveXMLElement choice2 = new CaseSensitiveXMLElement("xs:choice");
		choice2.setAttribute("minOccurs", "0");
		choice2.setAttribute("maxOccurs", "unbounded");
		messageType.addChild(choice2);
		// Add inherited elements
		for (int i = 0; i < inherited.length; i++) {
			CaseSensitiveXMLElement x = new CaseSensitiveXMLElement("xs:element");
			x.setAttribute("ref", inherited[i]);
			choice2.addChild(x);
		}
		CaseSensitiveXMLElement m = new CaseSensitiveXMLElement("xs:element");
		m.setAttribute("name", "message");
		m.setAttribute("type", specialMessageTypeName);
		choice2.addChild(m);
		CaseSensitiveXMLElement attr = new CaseSensitiveXMLElement();
		attr.setName("xs:attribute");
		attr.setAttribute("use", "required");
		attr.setAttribute("name", "name");
		attr.setAttribute("type", "xs:string");
		messageType.addChild(attr);
		attr = new CaseSensitiveXMLElement();
		attr.setName("xs:attribute");
		attr.setAttribute("name", "type");
		attr.setAttribute("type", "xs:string");
		messageType.addChild(attr);
		attr = new CaseSensitiveXMLElement();
		attr.setName("xs:attribute");
		attr.setAttribute("name", "condition");
		attr.setAttribute("type", "xs:string");
		messageType.addChild(attr);
		attr = new CaseSensitiveXMLElement();
		attr.setName("xs:attribute");
		attr.setAttribute("name", "mode");
		attr.setAttribute("type", "xs:string");
		messageType.addChild(attr);
		attr = new CaseSensitiveXMLElement();
		attr.setName("xs:attribute");
		attr.setAttribute("name", "index");
		attr.setAttribute("type", "xs:string");
		messageType.addChild(attr);
		CaseSensitiveXMLElement insertedadapters = new CaseSensitiveXMLElement("adapters:insertedadapters");
		choice2.addChild(insertedadapters);
		
		generateSetters(messageType, choice2, adapterName, false);
		generateMethods(choice2, adapterName);
		
		return messageType;
	}
	
	private XMLElement createAdapterXSD(String adapterName, CaseSensitiveXMLElement parent) throws Exception {
		
		String specialMessageTypeName = adapterName + "MessageType";
		
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
		// Add Special message type reference.
		CaseSensitiveXMLElement m = new CaseSensitiveXMLElement("xs:element");
		m.setAttribute("name", "message");
		m.setAttribute("type", specialMessageTypeName);
		choice.addChild(m);
		
		CaseSensitiveXMLElement specialMessage = createSpecialMessageType(specialMessageTypeName, adapterName);
		parent.addChild(specialMessage);
		
		// Add adapters:insertedadapters
		CaseSensitiveXMLElement insertedadapters = new CaseSensitiveXMLElement("adapters:insertedadapters");
		choice.addChild(insertedadapters);
		
		generateSetters(type, choice, adapterName, true);
				
		CaseSensitiveXMLElement conditionAttr = new CaseSensitiveXMLElement("xs:attribute");
		conditionAttr.setAttribute("name", "condition");
		conditionAttr.setAttribute("type", "xs:string");
		type.addChild(conditionAttr);
		
		generateMethods(choice, adapterName);
				
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
		Set<String> maps = mmd.getMapDefinitions();
		
		Iterator<String> all = maps.iterator();
		while ( all.hasNext() ) {
			
			String mappie = all.next();
			if ( !mappie.equals("__empty__")) {
				XMLElement x = createAdapterXSD(mappie, xml);
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
				
				String mappie = all.next();
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
//		MapMetaData mmd = MapMetaData.getInstance();
		
		GenerateXSD xsd = new GenerateXSD();
		String result = xsd.generateXSD();
		
		BufferedWriter fw = new BufferedWriter(  new FileWriter("/home/arjen/@.xsd") );
		fw.write(result);
		fw.close();
		
		//xsd.createAdapterXSD("sqlquery", new CaseSensitiveXMLElement());

	}

}
