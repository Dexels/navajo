package com.dexels.navajo.compiler.navascript;

public class ConditionFragment extends NavascriptFragment {

	private StringBuffer expressionStr = new StringBuffer();
	private boolean isFinal = false;
	
	public String getExpressionStr() {
		return expressionStr.toString();
	}

	public void finalize() {
		isFinal = true;
	}
}

