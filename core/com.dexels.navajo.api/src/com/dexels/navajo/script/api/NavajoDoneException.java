package com.dexels.navajo.script.api;


public class NavajoDoneException extends RuntimeException {

	private static final long serialVersionUID = -8029651657536044461L;

	public NavajoDoneException(Throwable pending) {
		super(pending);
	}

}
