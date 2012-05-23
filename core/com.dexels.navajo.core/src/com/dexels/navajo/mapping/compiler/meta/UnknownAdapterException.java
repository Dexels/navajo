package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class UnknownAdapterException extends MetaCompileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4473135041452626810L;

	public UnknownAdapterException(String tagname, XMLElement offendingTag, String filename) {
		super(filename, offendingTag);
		super.message = "Unknown adapter tag: <" + tagname + "/>";
	}
}
