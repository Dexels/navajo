package com.dexels.navajo.document.navascript.tags;

public class NS3Utils {

	public static String generateIndent(int indent) {
		StringBuffer sb = new StringBuffer();
		for ( int i = 0; i < indent; i++ ) {
			sb.append(NS3Constants.INDENT_STEP);
		}
		return sb.toString();
	}
}
