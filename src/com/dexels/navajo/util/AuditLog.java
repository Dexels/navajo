/*
 * Created on 26-jan-2006
 *
 * @author Dexels developer
 * @version $Id$
 */
package com.dexels.navajo.util;

/**
 * @author Arjen
 *
 */
public final class AuditLog {

	private final static String AUDIT_MESSAGE_TASK_SCHEDULER = "AUDIT LOG (Task Scheduler): ";

	public static void log(String subsystem, String message) {
		System.err.println(subsystem + message);
	}
}
