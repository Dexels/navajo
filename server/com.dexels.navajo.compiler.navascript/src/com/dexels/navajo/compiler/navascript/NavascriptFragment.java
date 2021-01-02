package com.dexels.navajo.compiler.navascript;

import java.util.ArrayList;
import java.util.List;

public abstract class NavascriptFragment {

	static List<String> specialChars = new ArrayList<>();
	protected StringBuffer fragmentBuffer = new StringBuffer();
	
	static {
		specialChars.add("+");
		specialChars.add("-");
		specialChars.add("*");
		specialChars.add("/");
		specialChars.add("%");
		specialChars.add("AND");
		specialChars.add("OR");
		specialChars.add(">");
		specialChars.add("<");
		specialChars.add(">=");
		specialChars.add("<=");
		specialChars.add("==");
		specialChars.add("!=");
	}
	
	public void consumeToken(String s) {
		consumeToken(fragmentBuffer, s);
	}
	
	public String consumedFragment() {
		return fragmentBuffer.toString();
	}
	
	public abstract void finalize();
	
	// +, -, *, /, %, AND, OR, <, >, <=, >=, ==, != should have space before and after.
	public void consumeToken(StringBuffer sb, String content) {
		if ( specialChars.contains(content.trim())) {
			sb.append(" " + content.trim() + " ");
		} else {
			sb.append(content);
		}
	}
	
}
