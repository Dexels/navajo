package com.dexels.navajo.workflow;

import java.io.Serializable;

public final class ConditionalExpression implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4772219522840149089L;
	
	private final String condition;
	private final String expression; // value
	
	public ConditionalExpression(String condition, String expression) {
		if ( condition != null ) {
			this.condition = condition;
		} else {
			this.condition = "true";
		}
		this.expression = expression;
	}
	
	public final String getCondition() {
		return condition;
	}
	
	public final String getExpression() {
		return expression;
	}
	
	
}
