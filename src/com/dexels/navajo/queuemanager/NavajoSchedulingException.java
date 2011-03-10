package com.dexels.navajo.queuemanager;


public class NavajoSchedulingException extends Exception {
	
	private int reason = UNKNOWN;
	
	public int getReason() {
		return reason;
	}
	public static final int UNKNOWN = -1;
	public static final int SCRIPT_PROBLEM = 0;
	public static final int REQUEST_REFUSED = 1;
	

	public NavajoSchedulingException(int reason, String message, Exception e) {
		super(message,e);
		this.reason = reason;
	}

	public NavajoSchedulingException(int reason, String message) {
		super(message);
		this.reason = reason;
	}	
}
