package com.dexels.navajo.mapping.compiler.meta;

public class UnknownMapInitializationParameterException extends MetaCompileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3367752279745524035L;

	public UnknownMapInitializationParameterException(String adapter, String parameter, int lineNr, String filename) {
		super(filename,lineNr);
		super.message = "Trying to set unknown initialization parameter [" + parameter + "] for adapter  <" + adapter + "/>";
	}

}
