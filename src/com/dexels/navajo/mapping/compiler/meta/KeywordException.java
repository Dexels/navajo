package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class KeywordException extends MetaCompileException {

	public KeywordException(String fileName, XMLElement offendingTag, String message) {
		super(fileName, offendingTag);
		super.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2326978089816572024L;

}
