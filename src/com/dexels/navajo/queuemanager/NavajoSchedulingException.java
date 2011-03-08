package com.dexels.navajo.queuemanager;


public class NavajoSchedulingException extends Exception {

	public NavajoSchedulingException(Exception e) {
		super(e);
	}

	public NavajoSchedulingException(String message) {
		super(message);
	}
	public NavajoSchedulingException(String message, Exception e) {
		super(message,e);
	}
}
