package com.dexels.navajo.server.jmx;

import java.util.Date;


public class NavajoNotification  extends javax.management.Notification {

	public final static String WARNING = "WARNING";
	public final static String ERROR = "ERROR";
	public final static String INFO = "INFO";
	public final static String DYING = "DYING";
	public static final String NAVAJO_NOTIFICATION = "navajo.notification";
	 
	private static final long serialVersionUID = 1132783424221165434L;

	private long warningLevel = 0;
	private long currentLevel = 0;
	private String severeness = "WARNING";
	private String message = null;
	private Date time = null;
	
	public NavajoNotification(     Object source, 
								   long sequenceNumber,
								   Date timeStamp,
								   long warningLevel,
								   long currentLevel,
								   String severeness,
								   String message) {
		super(NAVAJO_NOTIFICATION, source, sequenceNumber);
		this.time = (Date) timeStamp.clone();
		this.warningLevel = warningLevel;
		this.currentLevel = currentLevel;
		this.message = message;
		this.severeness = severeness;
	}
	
	public String getMessage() {
		return this.message;
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

	public Date getTime() {
		return (Date) time.clone();
	}
}
