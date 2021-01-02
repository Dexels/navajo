package com.dexels.navajo.compiler.navascript;

public class ExpressionFragment extends NavascriptFragment {

	private boolean isLiteral;

	// +, -, *, /, %, AND, OR, <, >, <=, >=, ==, != should have space before and after.
//	public void consumeToken(String content) {
//		if ( content.equals("$")) {
//			expressionStr.append(content);
//		} else {
//			expressionStr.append(content + " ");
//		}
//	}

	public String getExpressionStr() {
		return fragmentBuffer.toString();
	}

	public void finalize() {

	}
}
