package com.dexels.navajo.workflow;

public final class ConditionalExpression {

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
