package com.dexels.navajo.server.listener.http.schedulers;

public class SchedulingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7151141602562144947L;

	public final static String SERVICE_NOT_AVAILABLE = "Service not available";

	private String webservice = null;
	private String user = null;

	public SchedulingException(String webservice, String message) {
		super(message);
		this.webservice = webservice;
	}

	public SchedulingException(String webservice, String user, String message) {
		super(message);
		this.webservice = webservice;
		this.user = user;
	}

	public String getLocalizedMessage() {
		if (user == null) {
			return getMessage() + ": " + webservice;
		} else {
			return getMessage() + ": " + webservice + "(user=" + user + ")";
		}
	}

	public String getWebservice() {
		return webservice;
	}

	public String getUser() {
		return user;
	}

}
