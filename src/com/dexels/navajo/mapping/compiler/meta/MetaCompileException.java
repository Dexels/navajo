package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MetaCompileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193610832873773439L;

	protected XMLElement offendingTag;
	protected String message;
	protected String fileName;
	
	public  MetaCompileException(String fileName, XMLElement offendingTag) {
		this.fileName = fileName;
		this.offendingTag = offendingTag;
	}
	
	public  MetaCompileException(String fileName, XMLElement offendingTag, String message) {
		this.fileName = fileName;
		this.offendingTag = offendingTag;
		this.message = message;
	}
	
	public XMLElement getOffendingTag() {
		return this.offendingTag;
	}
	
	public void setOffendingTag(XMLElement offendingTag) {
		this.offendingTag = offendingTag;
	}
	
	public String getMessage() {
		return message + " (" + fileName + ":" + (offendingTag.getLineNr()+1) + ")";
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
