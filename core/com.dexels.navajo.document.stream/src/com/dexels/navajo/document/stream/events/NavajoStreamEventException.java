package com.dexels.navajo.document.stream.events;

public class NavajoStreamEventException extends RuntimeException {

	private static final long serialVersionUID = -6524813949471907785L;

	public NavajoStreamEventException() {
	}

	public NavajoStreamEventException(String message) {
		super(message);
	}

	public NavajoStreamEventException(Throwable cause) {
		super(cause);
	}

	public NavajoStreamEventException(String message, Throwable cause) {
		super(message, cause);
	}

	public NavajoStreamEventException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
