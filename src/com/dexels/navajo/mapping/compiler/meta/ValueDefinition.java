package com.dexels.navajo.mapping.compiler.meta;

import java.io.StringWriter;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
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
		return type.substring(type.indexOf("map:") + 4, type.indexOf(" "));
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
	public XMLElement generateCode(String setterValue, String condition, XMLElement out, boolean append, String filename) throws Exception {
		
		if ( ( direction.equals("in") || direction.equals("automatic") ) && !type.startsWith("map:")) { // generate <field name=""><expression value=""/></field> construction
					
			XMLElement field = new CaseSensitiveXMLElement();
			field.setName("field");
			field.setAttribute("name", ( this.getClass().getName().equals("com.dexels.navajo.mapping.compiler.meta.ValueDefinition") ? name : ((ParameterDefinition) this).getField() ) );
			if ( condition != null && !condition.equals("") ) {
				field.setAttribute("condition", condition);
			}
			XMLElement expression = new CaseSensitiveXMLElement();
			expression.setName("expression");
			field.addChild(expression);
			expression.setAttribute("xml:space", "preserve");
			if ( type.equals("stringliteral") && !setterValue.startsWith("{") ) {
				expression.setContent(setterValue);
			} else {
				if ( type.equals("string") && !setterValue.startsWith("{") ) {
					setterValue = "'" + setterValue + "'";
				}
				if ( setterValue.startsWith("{") ) {  // Force expression.
					//System.err.println("FOUND ESCAPED EXPRESSION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					setterValue = setterValue.replace('{', ' ');
					setterValue = setterValue.replace('}', ' ');
				}
				XMLElement value = new CaseSensitiveXMLElement();
				value.setName("value");
				expression.addChild(value);
				value.setContent(setterValue);
			}
			if ( append ) {
				out.addChild(field);
			}
			return field;
		} else if ( direction.equals("out") && type.startsWith("map:") ){ // Generate <map ref=""> construction
			XMLElement mapref = new CaseSensitiveXMLElement();
			mapref.setName("map");
			mapref.setAttribute("ref", setterValue);
			if ( condition != null && !condition.equals("") ) {
				mapref.setAttribute("filter", condition);
			}
			out.addChild(mapref);
			return mapref;
		} else if ( direction.equals("in") && type.startsWith("map:") ) { // Generate <field name=""><map ref=""></field> construction...
			XMLElement field = new CaseSensitiveXMLElement();
			field.setName("field");
			field.setAttribute("name", ( this.getClass().getName().equals("com.dexels.navajo.mapping.compiler.meta.ValueDefinition") ? name : ((ParameterDefinition) this).getField() ) );
			if ( condition != null && !condition.equals("") ) {
				field.setAttribute("condition", condition);
			}
			XMLElement mapref = new CaseSensitiveXMLElement();
			mapref.setName("map");
			mapref.setAttribute("ref", setterValue);
			field.addChild(mapref);
			out.addChild(field);
			return mapref;
		} else {
			throw new Exception("Unknown value tag");
		}
		
	}
	
	public static void main(String [] args) throws Exception {
		
		ValueDefinition vd = new ValueDefinition("datasource", "string", "false", "in");
		
		StringWriter sw = new StringWriter();
		XMLElement start = new CaseSensitiveXMLElement();
		start.setName("tsl");
		vd.generateCode("sportlinkkernel", null, start, true, "aap.xml");
		sw = new StringWriter();
		start.write(sw);
		System.err.println(sw.toString());
	}
}
