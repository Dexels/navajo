package com.dexels.navajo.mapping.compiler.meta;

public class UnknownMethodException extends MetaCompileException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -472827098999476707L;

	public UnknownMethodException(String tagname, int lineNr, String filename) {
		super(filename, lineNr);
		super.message = "Unknown method or output value tag: <" + tagname + "/>";
	}
}
