package com.dexels.navajo.mapping.compiler.meta;

import java.io.StringWriter;

import com.dexels.navajo.document.nanoimpl.XMLElement;

/**
 * 
 * @author arjen
 *
 */
public class ValueDefinition {

	protected String name;
	protected String type;
	protected String value;
	protected String required;
	protected String direction;
	
	public ValueDefinition(String name, String type, String required, String direction ) {
		this.name = name;
		this.type = type;
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
	
	public String getType() {
		return type;
	}
	
	public String getMapType() {
		if ( type.indexOf(" ") != -1 ) {
			return type.substring(type.indexOf("map:") + 4, type.indexOf(" "));
		} else {
			return type.substring(type.indexOf("map:") + 4);
		}
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public static ValueDefinition parseDef(XMLElement e) throws KeywordException {
		
		String name = (String) e.getAttribute("name");
		if ( name.equals("condition") ) {
			throw new KeywordException("", null, "Illegal parameter specified: " + name);
		}
		
		String type = (String) e.getAttribute("type");
		String required = (String) e.getAttribute("required");
		String direction = (String) e.getAttribute("direction");
		String value = (String) e.getAttribute("value");
		ValueDefinition vd = new ValueDefinition(name, type, required, direction);
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
	 */
	public XMLElement generateCode(XMLElement currentIn, String setterValue, String condition, XMLElement out, boolean append, String filename) throws Exception {
		
		// Case I: <field><expression/></field> construct.
		if ( ( direction.equals("in") || direction.equals("automatic") ) && !type.startsWith("map:")) { 
			XMLElement field = new TSLElement(currentIn, "field");
			field.setAttribute("name", ( this.getClass().getName().equals("com.dexels.navajo.mapping.compiler.meta.ValueDefinition") ? name : ((ParameterDefinition) this).getField() ) );
			if ( condition != null && !condition.equals("") ) {
				field.setAttribute("condition", condition);
			}
			XMLElement expression = new TSLElement(currentIn, "expression");
			field.addChild(expression);
			expression.setAttribute("xml:space", "preserve");
			// Case Ia: stringliteral, create construct <expression>[STRING CONTENT]<expression>
			if ( type.equals("stringliteral") && !setterValue.startsWith("{") ) {
				expression.setContent(setterValue);
			} 
			// Case Ib: other, if string type automatically put quotes (') around string.
			//                 if string type surrounded by {} or any other type, assume normal expression, 
			//                      use <expression><value>[EXPRESSION]</value></expression> construct.
			else {
				if ( type.equals("string") && !setterValue.startsWith("{") && setterValue.indexOf("'") != -1) {
					throw new MetaCompileException(filename, currentIn, "Invalid ' character for string type: " + setterValue);
				}
				if ( type.equals("string") && !setterValue.startsWith("{") ) {
					setterValue = "'" + setterValue + "'";
				}
				if ( setterValue.startsWith("{") ) {  // Force expression.
					//System.err.println("FOUND ESCAPED EXPRESSION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
	    // Case II: <message><map ref=""/></message> or <property><map ref=""/></property> construct.
		} else if ( ( currentIn.getParent().getName().equals("message") || currentIn.getParent().getName().equals("property") ) && type.startsWith("map:") ){ // Generate <map ref=""> construction
			XMLElement mapref = new TSLElement(currentIn, "map");
			mapref.setAttribute("ref", setterValue);
			if ( condition != null && !condition.equals("") ) {
				mapref.setAttribute("filter", condition);
			}
			out.addChild(mapref);
			return mapref;
	    // Case III: <field><map ref=""/></field> construct.
		} else if ( currentIn.getFirstChild().getName().equals("map") && type.startsWith("map:") ) { // Generate <field name=""><map ref=""></field> construction...
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
	
	public static void main(String [] args) throws Exception {
		
		ValueDefinition vd = new ValueDefinition("datasource", "string", "false", "in");
		
		StringWriter sw = new StringWriter();
		XMLElement start = new TSLElement(null, "tsl");
		start.setName("tsl");
		vd.generateCode(null, "sportlinkkernel", null, start, true, "aap.xml");
		sw = new StringWriter();
		start.write(sw);
		System.err.println(sw.toString());
	}
}
