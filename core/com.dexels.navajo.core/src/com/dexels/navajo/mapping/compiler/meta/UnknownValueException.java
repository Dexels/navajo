package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class UnknownValueException extends MetaCompileException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6792620993996882281L;

	public UnknownValueException(String tag, String parameter, XMLElement offendingTag, String filename) {
		super(filename,offendingTag);
		if ( parameter != null ) {
			super.message = "Trying to set unknown parameter [" + parameter + "] in tag <" + tag + "/>";
		} else {
			super.message = "Trying to set empty parameter in tag <" + tag + "/>";
		}
	}

}
