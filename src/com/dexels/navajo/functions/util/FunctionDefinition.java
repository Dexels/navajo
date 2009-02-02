package com.dexels.navajo.functions.util;

public class FunctionDefinition {

	private String object;
	private String description;
	private String usage;
	
	public FunctionDefinition(String object, String description, String usage) {
		this.object = object;
		this.description = description;
		this.usage = usage;
	}

	public String getObject() {
		return object;
	}

	public String getDescription() {
		return description;
	}

	public String getUsage() {
		return usage;
	}
}
