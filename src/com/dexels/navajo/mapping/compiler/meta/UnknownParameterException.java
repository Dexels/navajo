package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class UnknownParameterException extends MetaCompileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8641803615058174162L;
	
	public UnknownParameterException(String method, String parameter, XMLElement offendingTag, String filename) {
		super(filename,offendingTag);
		super.message = "Unknown parameter [" + parameter + "] for method <" + method + "/>";
	}

}
