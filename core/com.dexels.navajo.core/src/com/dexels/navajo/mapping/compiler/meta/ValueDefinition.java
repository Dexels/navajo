/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

/**
 * 
 * @author arjen
 *
 */
public class ValueDefinition {

	protected String name;
	protected String map;
	protected String value;
	protected String required;
	protected String direction;
	
	public ValueDefinition(String name, String map, String required, String direction ) {
		this.name = name;
		this.map = map;
		this.required = required;
		this.direction = direction;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(String v) {
		this.value = v;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getRequired() {
		return required;
	}
	
	public void setRequired(String required) {
		this.required = required;
	}
	
	public String getMap() {
		return map;
	}
	
	public String getMapType() {
		if ( map != null ) {
			return map.replace('[', ' ').replace(']', ' ').trim();
		} else {
			return null;
		}
	}
	
	public void setMap(String map) {
		this.map = map;
	}
	
	public static ValueDefinition parseDef(XMLElement e) throws KeywordException {
		
		String name = (String) e.getAttribute("name");
		if ( name.equals("condition") ) {
			throw new KeywordException("", null, "Illegal parameter specified: " + name);
		}
		
		String map = (String) e.getAttribute("map");
		String required = (String) e.getAttribute("required");
		String direction = (String) e.getAttribute("direction");
		String value = (String) e.getAttribute("value");
		ValueDefinition vd = new ValueDefinition(name, map, required, direction);
		if ( value != null ) {
			vd.setValue(value);
		}
		
		return vd;
	}
	
	/**
	 * in: 1) <map:sqlmap datasource="sportlinkkernel">
	 *     2) <map:set field="datasource" value="sportlinkkernel"/>
	 *     3) <map:set field="datasource">sportlinkkernel</map:set>
	 * @param in
	 * @param out
	 * @throws MetaCompileException 
	 */
	public XMLElement generateCode(XMLElement currentIn, String setterValue, boolean textNode, 
			                       String condition, XMLElement out, boolean append, String filename) throws MetaCompileException {
		
		// Case I: <field><expression/></field> construct.
		if ( ( direction.equals("in") || direction.equals("automatic") ) && map == null) { 
			XMLElement field = new TSLElement(currentIn, "field");
			field.setAttribute("name", ( this.getClass().getName().equals("com.dexels.navajo.mapping.compiler.meta.ValueDefinition") ? name : ((ParameterDefinition) this).getField() ) );
			if ( condition != null && !condition.equals("") ) {
				field.setAttribute("condition", condition);
			}
			XMLElement expression = new TSLElement(currentIn, "expression");
			field.addChild(expression);
			expression.setAttribute("xml:space", "preserve");
			// Case Ia: stringliteral, create construct <expression>[STRING CONTENT]<expression>
			if ( textNode && (setterValue.length() == 0 || setterValue.charAt(0) != '{') ) {
				expression.setContent(setterValue);
			} 
			// Case Ib: other, if string type automatically put quotes (') around string.
			//                 if string type surrounded by {} or any other type, assume normal expression, 
			//                      use <expression><value>[EXPRESSION]</value></expression> construct.
			else {
				if ( setterValue.length() > 0 && setterValue.charAt(0) == '{' ) {  // Force expression.
					setterValue = setterValue.replace('{', ' ');
					setterValue = setterValue.replace('}', ' ');
				}
				XMLElement value = new TSLElement(currentIn, "value");
				expression.addChild(value);
				value.setContent(setterValue);
			}
			if ( append ) {
				out.addChild(field);
			}
			return field;
	    // Case II: <message><map ref=""/></message> or <property><map ref=""/></property> construct or <param type="array"><map ref="">
		} else if ( ( currentIn.getParent().getName().equals("message") || 
				      currentIn.getParent().getName().equals("property") ||
				      currentIn.getParent().getName().equals("param") ) && map != null ){ // Generate <map ref=""> construction
			XMLElement mapref = new TSLElement(currentIn, "map");
			mapref.setAttribute("ref", setterValue);
			if ( condition != null && !condition.equals("") ) {
				mapref.setAttribute("filter", condition);
			}
			out.addChild(mapref);
			return mapref;
	    // Case III: <field><map ref=""/></field> construct.
		} else if ( currentIn.getFirstChild().getName().equals("map") && map != null ) { // Generate <field name=""><map ref=""></field> construction...
			XMLElement field = new TSLElement(currentIn, "field");
			field.setAttribute("name", ( this.getClass().getName().equals("com.dexels.navajo.mapping.compiler.meta.ValueDefinition") ? name : ((ParameterDefinition) this).getField() ) );
			if ( condition != null && !condition.equals("") ) {
				field.setAttribute("condition", condition);
			}
			out.addChild(field);
			return field;
		} else {
			throw new MetaCompileException(filename, currentIn, "Unknown value tag for setter value: " + setterValue + ", tagname = " + currentIn.getName());
		}
		
	}

}
