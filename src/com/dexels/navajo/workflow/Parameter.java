package com.dexels.navajo.workflow;

import java.io.Serializable;
import java.util.ArrayList;

public final class Parameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2928305184859925521L;
	
	private String name;
	private ArrayList<ConditionalExpression> expressions = new ArrayList<ConditionalExpression>();
	
	public Parameter(String name, ArrayList<ConditionalExpression> expressions) {
		this.name = name;
		this.expressions = expressions;
	}

	public final String getName() {
		return name;
	}

	public final ArrayList<ConditionalExpression>  getExpressions() {
		return expressions;
	}
	
}
