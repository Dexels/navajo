package com.dexels.navajo.mapping.compiler.meta;

import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MapDefinition {

	public String tagName;
	public String objectName;
	public boolean abstractMap = false;
	
	private MapMetaData myMetaData = null;
	
	public MapDefinition(MapMetaData m) {
		this.myMetaData = m;
	}
	
	protected HashMap<String, ValueDefinition> values = new HashMap<String, ValueDefinition>();
	protected HashMap<String,MethodDefinition> methods = new HashMap<String, MethodDefinition>();
	
	public ValueDefinition getValueDefinition(String name) {
		return values.get(name);
	}
	
	public MethodDefinition getMethodDefinition(String name) {
		return methods.get(name);
	}
	
	/**
	 * in: <map:sqlquery datasource=\"sportlinkkernel\"/>
	 * out: <map object="com.dexels.navajo.adapter.SQLMap">
	 *          <field name="datasource">
	 *             <expression value="'sportlinkkernel'"/>
	 *          </field>
	 *      </map>
	 * @param in
	 * @param out
	 */
	public void generateCode(XMLElement in, XMLElement out) {
		
		XMLElement map = null;
		
		System.err.println("IN GENERATECODE FOR : " + in.getName() + ", tagname is: " + tagName );
		if ( in.getName().equals("map:"+tagName)  ) {
			map = new CaseSensitiveXMLElement();
			map.setName("map");
			map.setAttribute("object", objectName);
			// Parse attributes using ValueDefinition.
			Enumeration attributes = in.enumerateAttributeNames();
			while ( attributes.hasMoreElements() ) {
				String attribName = (String) attributes.nextElement();
				String attribValue = (String) in.getAttribute(attribName);
				System.err.println("Looking up: " + attribName);
				ValueDefinition vd = getValueDefinition(attribName);
				System.err.println("Found vd: " + vd);
				vd.generateCode(attribValue, map);
			}
			out.addChild(map);
		}
		
		// Parse children tags.
		Vector v = in.getChildren();
		for ( int i = 0; i < v.size(); i++ ) {
			XMLElement child = (XMLElement) v.get(i);
			if ( child.getName().equals(tagName + ":set") ) {
				String field = (String) child.getAttribute("field");
				String setterValue = (String) child.getAttribute("value");
				if ( setterValue == null ) {
					setterValue = child.getContent();
				}
				ValueDefinition vd = getValueDefinition(field);
				System.err.println("field: " + field + ", vd = " + vd);
				if ( vd != null ) {
					vd.generateCode(setterValue, ( map != null ? map : out ) );
				}
			} else if ( child.getName().startsWith(tagName + ":")) {
				// Could be a method or a map ref getter.
				String method = child.getName().substring(child.getName().indexOf(":") + 1);
				System.err.println("method: " + method);
				if ( getMethodDefinition(method) != null ) {
					MethodDefinition md = getMethodDefinition(method);
					md.generateCode(child, ( map != null ? map : out ) );
					System.err.println("Generated code for method");
				} else {
					// Maybe an out ValueDefinition, map ref stuff...
					ValueDefinition vd = getValueDefinition(method);
					if ( vd != null ) {
						XMLElement out2 = vd.generateCode(method, ( map != null ? map : out ) );
						generateCode(child, out2);
					}
				}
			} else if ( child.getName().equals("map:" + tagName ) ) {
				generateCode(child, ( map != null ? map : out ) );
			} else if ( child.getName().startsWith("map:" ) ) {
				System.err.println("UNKNOWN MAP ENCOUNTERED!: " + child.getName().substring(4));
				MapDefinition md = myMetaData.getMapDefinition(child.getName().substring(4));
				if ( md != null ) {
					md.generateCode(child, ( map != null ? map : out ) );
				}
			}
			else {
				// Copy it.
				XMLElement copy = new CaseSensitiveXMLElement();
				if ( map != null) { 
					map.addChild(copy);
				} else {
					out.addChild(copy);
				}
				copy.setName(child.getName());
				Enumeration allAttribs =  child.enumerateAttributeNames();
				while ( allAttribs.hasMoreElements() ) {
					String name = (String) allAttribs.nextElement();
					String value = (String) child.getAttribute(name);
					copy.setAttribute(name, value);
				}
				generateCode(child, copy);
			}
		}
		
	}
	
}
