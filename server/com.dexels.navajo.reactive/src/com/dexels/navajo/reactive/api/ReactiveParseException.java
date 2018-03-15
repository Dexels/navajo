package com.dexels.navajo.reactive.api;

public class ReactiveParseException extends RuntimeException {

	private static final long serialVersionUID = 6115559675419129641L;

	public ReactiveParseException() {
	}

	public ReactiveParseException(String message) {
		super(message);
	}

	public ReactiveParseException(Throwable cause) {
		super(cause);
	}

	public ReactiveParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReactiveParseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
