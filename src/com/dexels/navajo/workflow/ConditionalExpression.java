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
	private final String navajoToUse; // value
	
	public ConditionalExpression(String condition, String expression, String navajoToUse) {
		if ( condition != null ) {
			this.condition = condition;
		} else {
			this.condition = "true";
		}
		this.expression = expression;
		this.navajoToUse = navajoToUse;
	}
	
	public final String getCondition() {
		return condition;
	}
	
	public final String getExpression() {
		return expression;
	}
	
	public boolean hasDefinedSourceNavajo() {
		return ( navajoToUse != null);
	}
	
	public String getSourceState() {
		if ( navajoToUse == null ) {
			return null;
		}
		StringTokenizer sts = new StringTokenizer(navajoToUse, ":");
		if ( sts.hasMoreTokens() ) {
			return sts.nextToken();
		}
		return null;
	}
	
	public String getSourceDirection() {
		if ( navajoToUse == null ) {
			return null;
		}
		StringTokenizer sts = new StringTokenizer(navajoToUse, ":");
		if ( sts.hasMoreTokens() ) {
			sts.nextToken();
			if ( sts.hasMoreTokens() ) {
				return sts.nextToken();
			}
		}
		return "request";
	}
}
