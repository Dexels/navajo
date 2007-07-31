package com.dexels.navajo.mapping.compiler.meta;

public class ParameterDefinition extends ValueDefinition {

	public ParameterDefinition(String name, String field, String type, boolean required, String direction) {
		super(name, type, required, direction);
		this.field = field;
	}

	private String field;
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
}
