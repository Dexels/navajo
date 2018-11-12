package com.dexels.navajo.reactive.api;

public class ReactiveParameterException extends RuntimeException {

	private static final long serialVersionUID = 6115559675419129641L;

	public ReactiveParameterException() {
	}

	public ReactiveParameterException(String message) {
		super(message);
	}

	public ReactiveParameterException(Throwable cause) {
		super(cause);
	}

	public ReactiveParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReactiveParameterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
