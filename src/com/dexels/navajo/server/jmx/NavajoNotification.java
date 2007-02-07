package com.dexels.navajo.server.jmx;


public class NavajoNotification  extends javax.management.Notification {

	public final static String WARNING = "WARNING";
	public final static String ERROR = "ERROR";
	public final static String INFO = "INFO";
	public final static String DYING = "DYING";
	
	private static final long serialVersionUID = 1132783424221165434L;

	private long warningLevel = 0;
	private long currentLevel = 0;
	private String severeness = "WARNING";
	private String message = null;
	
	public NavajoNotification(String type, 
								   Object source, 
								   long sequenceNumber,
								   long warningLevel,
								   long currentLevel,
								   String severeness,
								   String message) {
		super(type, source, sequenceNumber);
		this.warningLevel = warningLevel;
		this.currentLevel = currentLevel;
		this.message = message;
		this.severeness = severeness;
	}
	
	public String getMessage() {
		return this.message;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public long getCurrentLevel() {
		return currentLevel;
	}

	public String getSevereness() {
		return severeness;
	}

	public long getWarningLevel() {
		return warningLevel;
	}

}
