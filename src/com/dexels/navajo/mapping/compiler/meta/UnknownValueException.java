package com.dexels.navajo.mapping.compiler.meta;

public class UnknownValueException extends MetaCompileException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6792620993996882281L;

	public UnknownValueException(String tag, String parameter, int lineNr, String filename) {
		super(filename,lineNr);
		if ( parameter != null ) {
			super.message = "Trying to set unknown parameter [" + parameter + "] in tag <" + tag + "/>";
		} else {
			super.message = "Trying to set empty parameter in tag <" + tag + "/>";
		}
	}

}
