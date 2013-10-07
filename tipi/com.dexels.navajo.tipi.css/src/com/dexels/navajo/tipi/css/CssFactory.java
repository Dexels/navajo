package com.dexels.navajo.tipi.css;

public class CssFactory {
	private static CssApplier css;

	public static void setInstance(CssApplier cs) {
		CssFactory.css = cs;
	}
	
	public static CssApplier getInstance() {
		return css;
	}
}
