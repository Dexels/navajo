package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class UnknownMapInitializationParameterException extends MetaCompileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3367752279745524035L;

	public UnknownMapInitializationParameterException(String adapter, String parameter, XMLElement offendingTag, String filename) {
		super(filename,offendingTag);
		super.message = "Trying to set unknown initialization parameter [" + parameter + "] for adapter  <" + adapter + "/>";
	}

}
