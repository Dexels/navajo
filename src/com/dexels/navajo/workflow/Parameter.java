package com.dexels.navajo.workflow;

public class Parameter {

	private String name;
	private String expression;
	
	public Parameter(String name, String expression) {
		this.name = name;
		this.expression = expression;
	}

	public String getName() {
		return name;
	}

	public String getExpression() {
		return expression;
	}
	
}
