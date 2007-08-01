package com.dexels.navajo.mapping.compiler.meta;

public class UnknownAdapterException extends MetaCompileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4473135041452626810L;

	public UnknownAdapterException(String tagname, int lineNr, String filename) {
		super(filename, lineNr);
		super.message = "Unknown adapter tag: <" + tagname + "/>";
	}
}
