package com.dexels.navajo.compiler.navascript;

public class ExpressionFragment implements NavascriptFragment {

	private StringBuffer expressionStr = new StringBuffer();
	private boolean isLiteral;
	
	public void consumeToken(String content) {
		expressionStr.append(content + " ");
	}
	
	public String getExpressionStr() {
		return expressionStr.toString();
	}

	@Override
	public String consumedFragment() {
		return expressionStr.toString();
	}
	
	public void finalize() {
		
	}
}
