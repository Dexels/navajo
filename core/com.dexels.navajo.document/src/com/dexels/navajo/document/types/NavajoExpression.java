package com.dexels.navajo.document.types;

import com.dexels.navajo.document.Property;

public class NavajoExpression extends NavajoType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7098796186928338855L;
	private String contents;
	
	public NavajoExpression(String s) {
		super(Property.EXPRESSION_PROPERTY);
		contents = s.replaceAll("\n", " ");
	}

	@Override
	public String toString() {
		return contents;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
