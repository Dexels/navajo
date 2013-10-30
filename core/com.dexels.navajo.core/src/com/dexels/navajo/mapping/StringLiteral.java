package com.dexels.navajo.mapping;

public class StringLiteral {

	private final String myString;
	
	public StringLiteral(String s) {
		myString = s;
	}
	
	@Override
	public String toString() {
		return myString;
	}
}
