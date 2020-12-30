package com.dexels.navajo.compiler.navascript;

public class ConditionFragment implements NavascriptFragment {

	private StringBuffer expressionStr = new StringBuffer();
	private boolean isFinal = false;
	
	public String getExpressionStr() {
		return expressionStr.toString();
	}

	@Override
	public void consumeToken(String s) {
		if ( isFinal || s == null) {
			return;
		} 
		expressionStr.append(s + " ");
	}

	@Override
	public String consumedFragment() {
		return expressionStr.toString();
	}

	public void finalize() {
		isFinal = true;
	}
}

