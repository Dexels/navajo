package com.dexels.navajo.mapping.compiler.meta;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MapDefinition {

	public String tagName;
	public String objectName;
	public String description;
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
	
	public static MapDefinition parseDef(XMLElement e) throws Exception {

		MapDefinition md = new MapDefinition(MapMetaData.getInstance());
		
		XMLElement c = e.getElementByTagName("tagname");
		if ( c != null) {
			md.tagName = c.getContent();
		} 
		XMLElement o = e.getElementByTagName("object");
		if ( o != null ) {
			md.objectName = o.getContent();
		}
		XMLElement d = e.getElementByTagName("description");
		if ( o != null ) {
			md.description = o.getContent();
		}
		XMLElement valuesTag = e.getElementByTagName("values");
		if ( valuesTag != null ) {
			Vector ch = valuesTag.getChildren();
			for ( int i = 0; i < ch.size(); i++ ) {
				XMLElement v = (XMLElement) ch.get(i);
				ValueDefinition vd = ValueDefinition.parseDef(v);
				md.values.put(vd.getName(), vd);
			}
		}
		XMLElement methodsTag = e.getElementByTagName("methods");
		if ( methodsTag != null ) {
			Vector ch = methodsTag.getChildren();
			for ( int i = 0; i < ch.size(); i++ ) {
				XMLElement v = (XMLElement) ch.get(i);
				MethodDefinition mdef = MethodDefinition.parseDef(v);
				md.methods.put(mdef.getName(), mdef);
			}
		}
		
		return md;
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
	public void generateCode(XMLElement in, XMLElement out, String filename) throws Exception {
		
		XMLElement map = null;
		
		System.err.println("IN GENERATECODE FOR : " + in.getName() + ", tagname is: " + tagName );
		if ( in.getName().equals("map:"+tagName)  ) {
			map = new CaseSensitiveXMLElement();
			map.setName("map");
			map.setAttribute("object", objectName);
			String condition = (String) in.getAttribute("condition");
			if (  condition != null && !condition.equals("") ) {
				map.setAttribute("condition", condition);
			}
			// Parse attributes using ValueDefinition.
			Enumeration attributes = in.enumerateAttributeNames();
			while ( attributes.hasMoreElements() ) {
				String attribName = (String) attributes.nextElement();
				String attribValue = (String) in.getAttribute(attribName);
				System.err.println("Looking up: " + attribName);
				ValueDefinition vd = getValueDefinition(attribName);
				System.err.println("Found vd: " + vd);
				if ( vd != null ) {
					vd.generateCode(attribValue, null, map, true, filename);
				} else if ( !attribName.equals("condition") ){
					throw new UnknownMapInitializationParameterException("map:"+tagName, attribName, in.getLineNr(), filename);
				}
			}
			out.addChild(map);
		}
		
		// Parse children tags.
		Vector v = in.getChildren();
		for ( int i = 0; i < v.size(); i++ ) {
			XMLElement child = (XMLElement) v.get(i);
			if ( child.getName().equals(tagName + ":set") ) {
				
				if ( child.getChildren().size() > 0 ) {
					throw new MetaCompileException(filename, child.getLineNr(), "Illegal children tags defined for tag <" + child.getName() + "/>");
				}
				
				String field = (String) child.getAttribute("field");
				String setterValue = (String) child.getAttribute("value");
				String condition = (String) child.getAttribute("condition");
				if ( setterValue == null ) {
					setterValue = child.getContent();
				}
				ValueDefinition vd = getValueDefinition(field);
				System.err.println("field: " + field + ", vd = " + vd);
				if ( vd != null ) {
					vd.generateCode(setterValue, condition, ( map != null ? map : out ), true, filename );
				} else {
					throw new UnknownValueException(child.getName(), field, child.getLineNr(), filename);
				}
			} else if ( child.getName().startsWith(tagName + ":")) {
				// Could be a method or a map ref getter.
				String method = child.getName().substring(child.getName().indexOf(":") + 1);
				System.err.println("method: " + method);
				String filter = (String) child.getAttribute("filter");
				if ( getMethodDefinition(method) != null ) {
					MethodDefinition md = getMethodDefinition(method);
					md.generateCode(child, ( map != null ? map : out ), filename );
					System.err.println("Generated code for method");
				} else {
					// Maybe an out ValueDefinition, map ref stuff...
					ValueDefinition vd = getValueDefinition(method);
					if ( vd != null ) {
						if (  ! ( child.getParent().getName().equals("message") || child.getParent().getName().equals("property") ) ) {
							throw new MetaCompileException(filename, child.getLineNr(), "Illegal tag <" + child.getName() + "/> encountered");
						}
						XMLElement out2 = vd.generateCode(method, filter, ( map != null ? map : out ), true, filename );
						generateCode(child, out2, filename);
					} else {
						System.err.println("Parent of " + child.getName() + " IS " + child.getParent().getName());
						throw new UnknownMethodException(child.getName(), ((XMLElement) v.get(i)).getLineNr(), filename);
					}
				}
			} else if ( child.getName().equals("map:" + tagName ) ) {
				generateCode(child, ( map != null ? map : out ), filename );
			} else if ( child.getName().startsWith("map:" ) ) {
				MapDefinition md = myMetaData.getMapDefinition(child.getName().substring(4));
				if ( md != null ) {
					md.generateCode(child, ( map != null ? map : out ), filename );
				} else {
					throw new UnknownAdapterException(child.getName(), child.getLineNr(), filename);
				}
			} else if (!( child.getName().equals("message") || 
					    child.getName().equals("property") ||
					    child.getName().equals("field") ||
					    child.getName().equals("debug") ||
					    child.getName().equals("param") || 
					    child.getName().equals("include") ||
					    child.getName().equals("option") ||
					    child.getName().equals("expression") ||
					    child.getName().equals("map") ||
					    child.getName().equals("methods") ||
					    child.getName().equals("method") ||
					    child.getName().equals("validations") ||
					    child.getName().equals("check") ) ) {
				throw new MetaCompileException(filename, child.getLineNr(), "Unknown tag <" + child.getName() + "/> encountered");
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
				generateCode(child, copy, filename);
			}
		}
		
	}
	
}
