package com.dexels.navajo.workflow;

import java.io.Serializable;

public final class Parameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2928305184859925521L;
	
	private String name;
	private String expression;
	
	public Parameter(String name, String expression) {
		this.name = name;
		this.expression = expression;
	}

	public final String getName() {
		return name;
	}

	public final String getExpression() {
		return expression;
	}
	
}
