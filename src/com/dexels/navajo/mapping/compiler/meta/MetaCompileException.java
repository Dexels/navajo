package com.dexels.navajo.mapping.compiler.meta;

public class MetaCompileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193610832873773439L;

	protected int lineNr;
	protected String message;
	protected String fileName;
	
	public  MetaCompileException(String fileName, int lineNr) {
		this.fileName = fileName;
		this.lineNr = lineNr;
	}
	
	public  MetaCompileException(String fileName, int lineNr, String message) {
		this.fileName = fileName;
		this.lineNr = lineNr;
		this.message = message;
	}
	
	public int getLineNr() {
		return lineNr;
	}
	
	public void setLineNr(int lineNr) {
		this.lineNr = lineNr;
	}
	
	public String getMessage() {
		return message + " (" + fileName + ":" + lineNr + ")";
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
