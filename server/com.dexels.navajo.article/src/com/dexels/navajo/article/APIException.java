package com.dexels.navajo.article;

public class APIException extends Exception {
	private static final long serialVersionUID = -5492265925114738046L;
	
	private APIErrorCode errorCode;
	
	public APIException (String message, Throwable cause, APIErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public APIErrorCode getErrorCode() {
		return errorCode;
	}
}
