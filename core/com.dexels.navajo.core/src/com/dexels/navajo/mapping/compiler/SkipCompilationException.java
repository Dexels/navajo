package com.dexels.navajo.mapping.compiler;

/**
 * Indicated that a script shouldn't be compiled, it is not an error and probably
 * does not need to be logged extensively. Used for script fragments, that only
 * should be used as includes.
 */

public class SkipCompilationException extends Exception {

	public SkipCompilationException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 4069280458449005971L;

}
