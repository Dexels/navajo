package com.dexels.navajo.mapping;

public class GenericDependentResource implements DependentResource {

	private String type;
	private String value;
	
	public GenericDependentResource(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

}
