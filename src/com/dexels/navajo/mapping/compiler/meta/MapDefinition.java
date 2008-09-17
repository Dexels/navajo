package com.dexels.navajo.mapping.compiler.meta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.XMLElement;

/**
 * This class holds the meta data definition of an adapter.
 * 
 * @author arjen
 *
 */
@SuppressWarnings("unchecked")
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
	protected HashMap<String, MethodDefinition> methods = new HashMap<String, MethodDefinition>();
	
	public ValueDefinition getValueDefinition(String name) {
		return values.get(name);
	}
	
	public MethodDefinition getMethodDefinition(String name) {
		return methods.get(name);
	}
	
	/**
	 * Strips the dot in a tag name.
	 * 
	 * @param e
	 * @return
	 */
	private final String stripDot(XMLElement e) {
		if ( e.getName().indexOf(".") == -1 ) {
			return e.getName();
		}
		String value = e.getName().substring(e.getName().indexOf(".") + 1);
		return value;
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
		if ( d != null ) {
			md.description = d.getContent();
		}
		XMLElement a = e.getElementByTagName("abstract");
		if ( a != null ) {
			md.abstractMap = a.getContent().equals("true");
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
		
		//System.err.println("IN GENERATECODE FOR : " + in.getName() + ", tagname is: " + tagName + ", linenr: " + in.getLineNr());
		if ( in.getName().equals("map."+tagName)  ) {
			map = new TSLElement(in, "map");
			map.setAttribute("object", objectName);
			String condition = (String) in.getAttribute("condition");
			if (  condition != null && !condition.equals("") ) {
				map.setAttribute("condition", condition);
			}
			// Parse attributes using ValueDefinition.
			Iterator<String> attributes = in.enumerateAttributeNames();
			
			HashSet<String> required = new HashSet<String>();
			// Get all 'automatic' parameters and determine required parameters.
			Iterator<ValueDefinition> auto = values.values().iterator();
			while ( auto.hasNext() )  {
				ValueDefinition pd = auto.next();
				if ( pd.getRequired().equals("automatic") ) {
					System.err.println("AUTOMATIC!!!!!!!!!!" + pd.getName());
					pd.generateCode(in, pd.getValue(), null, map, true, filename);
					//out.addChild(pdx);
				} else if ( pd.getRequired().equals("true") ) {
					required.add(pd.getName());
				}
			}
			
			
			while ( attributes.hasNext() ) {
				String attribName =  attributes.next();
				String attribValue = (String) in.getAttribute(attribName);
				//System.err.println("Looking up: " + attribName);
				ValueDefinition vd = getValueDefinition(attribName);
				//System.err.println("Found vd: " + vd);
				if ( vd != null ) {
					vd.generateCode(in, attribValue, null, map, true, filename);
					required.remove(attribName);
				} else if ( !attribName.equals("condition") ){
					throw new UnknownMapInitializationParameterException("map."+tagName, attribName, in, filename);
				}
			}
			
            // Check if all required parameters are present.
			if ( required.size() > 0 ) {
				throw new MissingParameterException(required, tagName, in, filename );
			}
			
			out.addChild(map);
		}
		
		// Parse children tags.
		Vector v = in.getChildren();
		for ( int i = 0; i < v.size(); i++ ) {
			XMLElement child = (XMLElement) v.get(i);
			// Case I: a non-primitive map ref construct:
			// <field><map ref=""> or <message><map ref=""> or <property><map ref="">
			if ( child.getName().indexOf(".") != -1 && getValueDefinition(stripDot(child)) != null && 
				  (	( child.getChildren().size() > 0 && child.getFirstChild().getName().equals("map") ) ||
				    ( child.getParent().getName().equals("message") || child.getParent().getName().equals("property") ) )
			   ) {
				// Maybe an out ValueDefinition, map ref stuff...
				String field = stripDot(child);
				String filter = (String) child.getAttribute("filter");
				ValueDefinition vd = getValueDefinition(field);
				// It is either <field><map ref=""> or <message><map ref=""/> or <property><map ref=""/>
				//System.err.println("Tag: " + child.getName() + ", field " + field + ", type =" + vd.getType());
				MapDefinition md = myMetaData.getMapDefinition(vd.getMapType());
				if ( md != null ) {
					XMLElement out2 = vd.generateCode(child, field, filter, ( map != null ? map : out ), true, filename );
					md.generateCode(child, out2, filename );
				} else {
					throw new UnknownAdapterException(child.getName(), child, filename);
				}
	        // Case II: a simple field construct.
			} else if ( child.getName().indexOf(".") != -1 && getValueDefinition(stripDot(child)) != null ) {
				
				if ( child.getChildren().size() > 0 && !child.getFirstChild().getName().equals("value") ) {
					throw new MetaCompileException(filename, child, "Illegal children tags defined for tag <" + child.getName() + "/>");
				}
				
				String field = stripDot(child);
				String setterValue = ( child.getAttribute("value") != null ? (String) child.getAttribute("value") : (String) child.getAttribute("ref") );
				String condition = (String) child.getAttribute("condition");
				// Maybe value is given as tag content?
				if ( setterValue == null ) {
					setterValue = child.getContent();
					if ( setterValue == null || "".equals(setterValue) ) {
						throw new MetaCompileException(filename, child, "Did not find any value that could be set for setter <" + child.getName() + "/>");
					}
				}
				ValueDefinition vd = getValueDefinition(field);
					
				XMLElement remainder = null;
				remainder = vd.generateCode(child, setterValue, condition, ( map != null ? map : out ), true, filename );
			
		    // Case III: a multiple-field aka method construct.		
			} else if ( child.getName().indexOf(".") != -1 && getMethodDefinition(stripDot(child)) != null ) {
				String method = stripDot(child);
				MethodDefinition md = getMethodDefinition(method);
				md.generateCode(child, ( map != null ? map : out ), filename );
		    // Case IV: ?
//			} else if ( child.getName().equals("map." + tagName ) ) {
//				generateCode(child, ( map != null ? map : out ), filename );
		    // Case V: a new map initialization construct.
			} else if ( child.getName().startsWith("map." ) ) {
				MapDefinition md = myMetaData.getMapDefinition(child.getName().substring(4));
				if ( md == null ) {
					throw new MetaCompileException(filename, child, "Could not find map definition for: " + child.getName().substring(4));
				}
				if ( md.abstractMap ) {
					throw new MetaCompileException(filename, child, "Illegal declaration of abstract adapter: " + md.tagName);
				}
				if ( md != null ) {
					md.generateCode(child, ( map != null ? map : out ), filename );
				} else {
					throw new UnknownAdapterException(child.getName(), child, filename);
				}
			} else if (!( child.getName().equals("message") || 
					    child.getName().equals("property") ||
					    child.getName().equals("field") ||
					    child.getName().equals("comment") ||
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
				throw new MetaCompileException(filename, child, "Unknown tag/method <" + child.getName() + "/> encountered");
			}
			else {
				// Copy it.
				XMLElement copy = new TSLElement(child, child.getName());
				if ( map != null) { 
					map.addChild(copy);
				} else {
					out.addChild(copy);
				}
				Iterator<String> allAttribs =  child.enumerateAttributeNames();
				while ( allAttribs.hasNext() ) {
					String name = allAttribs.next();
					String value = (String) child.getAttribute(name);
					copy.setAttribute(name, value);
				}
				// Copy text node, if it exists.
				if ( child.getContent() != null ) {
					copy.setContent(child.getContent());
				}
				generateCode(child, copy, filename);
			}
		}
		
	}
	
}
