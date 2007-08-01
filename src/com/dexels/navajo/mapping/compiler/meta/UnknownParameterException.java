package com.dexels.navajo.mapping.compiler.meta;

public class UnknownParameterException extends MetaCompileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8641803615058174162L;
	
	public UnknownParameterException(String method, String parameter, int lineNr, String filename) {
		super(filename,lineNr);
		super.message = "Unknown parameter [" + parameter + "] for method <" + method + "/>";
	}

}
