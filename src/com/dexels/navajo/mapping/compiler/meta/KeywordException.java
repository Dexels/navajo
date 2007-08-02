package com.dexels.navajo.mapping.compiler.meta;

public class KeywordException extends MetaCompileException {

	public KeywordException(String fileName, int lineNr, String message) {
		super(fileName, lineNr);
		super.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2326978089816572024L;

}
