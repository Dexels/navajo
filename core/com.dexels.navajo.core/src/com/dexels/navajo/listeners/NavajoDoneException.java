package com.dexels.navajo.listeners;


public class NavajoDoneException extends RuntimeException {

	private static final long serialVersionUID = -4326007996229766587L;

	public NavajoDoneException(Throwable pending) {
		super(pending);
	}

}
