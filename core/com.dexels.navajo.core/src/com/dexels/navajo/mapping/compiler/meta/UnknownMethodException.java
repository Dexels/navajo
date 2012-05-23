package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class UnknownMethodException extends MetaCompileException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -472827098999476707L;

	public UnknownMethodException(String tagname, XMLElement offendingTag, String filename) {
		super(filename, offendingTag);
		super.message = "Unknown method or output value tag: <" + tagname + "/>";
	}
}
