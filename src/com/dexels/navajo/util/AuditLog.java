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

	public final static String AUDIT_MESSAGE_TASK_SCHEDULER = "AUDIT LOG (Task Scheduler): ";
	public final static String AUDIT_MESSAGE_ASYNC_RUNNER = "AUDIT LOG (Async Runner): ";
	public final static String AUDIT_MESSAGE_STAT_RUNNER = "AUDIT LOG (Statistics Runner): ";
	public final static String AUDIT_MESSAGE_INTEGRITY_WORKER = "AUDIT LOG (Integrity Worker): ";
	public final static String AUDIT_MESSAGE_LOCK_MANAGER = "AUDIT LOG (Locking Manager): ";
	public final static String AUDIT_MESSAGE_DISPATCHER = "AUDIT LOG (Dispatcher): ";

	public final static void log(String subsystem, String message) {
		System.err.println(subsystem + message);
	}
}
