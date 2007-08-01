package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class ParameterDefinition extends ValueDefinition {

	public ParameterDefinition(String name, String field, String type, String required, String direction, int order, String value) {
		super(name, type, required, direction);
		this.field = field;
		this.order = order;
		this.value = value;
	}

	private String field;
	private int order;
	private String value;
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public static ParameterDefinition parseDef(XMLElement e, int order) {
		
		String name = (String) e.getAttribute("name");
		String type = (String) e.getAttribute("type");
		String required = (String) e.getAttribute("required");
		String field = (String) e.getAttribute("field");
		String value = (String) e.getAttribute("value");
		
		ParameterDefinition pd = new ParameterDefinition(name, field, type, required, "in", order, value);
		
		return pd;
	}

	public int getOrder() {
		return order;
	}

	public String getValue() {
		return value;
	}
	
}
