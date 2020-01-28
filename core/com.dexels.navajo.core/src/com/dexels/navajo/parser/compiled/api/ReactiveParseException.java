package com.dexels.navajo.parser.compiled.api;

public class ReactiveParseException extends RuntimeException {

	private static final long serialVersionUID = 1347861954879038546L;
	public ReactiveParseException(String message) {
		super(message);
	}

	public ReactiveParseException(String message, Throwable root) {
		super(message,root);
	}
	public ReactiveParseException(Throwable root) {
		super(root);
	}

}
