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
	protected boolean required;
	protected String direction;
	
	public ValueDefinition(String name, String type, boolean required, String direction ) {
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
	
	public boolean isRequired() {
		return required;
	}
	
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * in: 1) <map:sqlmap datasource="sportlinkkernel">
	 *     2) <map:set field="datasource" value="sportlinkkernel"/>
	 *     3) <map:set field="datasource">sportlinkkernel</map:set>
	 * @param in
	 * @param out
	 */
	public XMLElement generateCode(String setterValue, XMLElement out) {
		
		if ( direction.equals("in") ) {
			XMLElement field = new CaseSensitiveXMLElement();
			field.setName("field");
			field.setAttribute("name", ( this.getClass().getName().equals("com.dexels.navajo.mapping.compiler.meta.ValueDefinition") ? name : ((ParameterDefinition) this).getField() ) );
			XMLElement expression = new CaseSensitiveXMLElement();
			expression.setName("expression");
			field.addChild(expression);
			expression.setAttribute("xml:space", "preserve");
			if ( type.equals("stringliteral")) {
				expression.setContent(setterValue);
			} else {
				if ( type.equals("string")) {
					setterValue = "'" + setterValue + "'";
				}
				XMLElement value = new CaseSensitiveXMLElement();
				value.setName("value");
				expression.addChild(value);
				value.setContent(setterValue);
			}
			out.addChild(field);
		} else {
			XMLElement mapref = new CaseSensitiveXMLElement();
			mapref.setName("map");
			mapref.setAttribute("ref", setterValue);
			out.addChild(mapref);
			return mapref;
		}
		return null;
	}
	
	public static void main(String [] args) throws Exception {
		
		ValueDefinition vd = new ValueDefinition("datasource", "string", false, "in");
		
		StringWriter sw = new StringWriter();
		XMLElement start = new CaseSensitiveXMLElement();
		start.setName("tsl");
		vd.generateCode("sportlinkkernel", start);
		sw = new StringWriter();
		start.write(sw);
		System.err.println(sw.toString());
	}
}
