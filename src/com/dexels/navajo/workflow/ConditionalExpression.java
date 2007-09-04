package com.dexels.navajo.workflow;

import java.io.Serializable;
import java.util.StringTokenizer;

public final class ConditionalExpression implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4772219522840149089L;
	
	private final String condition;
	private final String expression; // value
	private final String source; // value
	
	public ConditionalExpression(String condition, String expression, String source) {
		if ( condition != null ) {
			this.condition = condition;
		} else {
			this.condition = "true";
		}
		this.expression = expression;
		this.source = source;
	}
	
	public final String getCondition() {
		return condition;
	}
	
	public final String getExpression() {
		return expression;
	}
	
	public boolean hasDefinedSourceNavajo() {
		return ( source != null);
	}
	
	public String getSourceState() {
		if ( source == null ) {
			return null;
		}
		StringTokenizer sts = new StringTokenizer(source, ":");
		if ( sts.hasMoreTokens() ) {
			return sts.nextToken();
		}
		return null;
	}
	
	public String getSourceDirection() {
		if ( source == null ) {
			return null;
		}
		StringTokenizer sts = new StringTokenizer(source, ":");
		if ( sts.hasMoreTokens() ) {
			sts.nextToken();
			if ( sts.hasMoreTokens() ) {
				return sts.nextToken();
			}
		}
		return "request";
	}
}
