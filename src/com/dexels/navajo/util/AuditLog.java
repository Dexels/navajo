/*
 * Created on 26-jan-2006
 *
 * @author Dexels developer
 * @version $Id$
 */
package com.dexels.navajo.util;

import com.dexels.navajo.server.Dispatcher;

/**
 * @author Arjen
 *
 */
public final class AuditLog {

	public final static String AUDIT_MESSAGE_TASK_SCHEDULER = "[AUDIT LOG] (Task Scheduler): ";
	public final static String AUDIT_MESSAGE_ASYNC_RUNNER = "[AUDIT LOG] (Async Runner): ";
	public final static String AUDIT_MESSAGE_STAT_RUNNER = "[AUDIT LOG] (Statistics Runner): ";
	public final static String AUDIT_MESSAGE_INTEGRITY_WORKER = "[AUDIT LOG] (Integrity Worker): ";
	public final static String AUDIT_MESSAGE_LOCK_MANAGER = "[AUDIT LOG] (Locking Manager): ";
	public final static String AUDIT_MESSAGE_DISPATCHER = "[AUDIT LOG] (Dispatcher): ";
	public final static String AUDIT_MESSAGE_CLOCK = "[AUDIT LOG] (Clock): ";
	public final static String AUDIT_MESSAGE_WORKFLOW = "[AUDIT LOG] (Workflow): ";
	public final static String AUDIT_MESSAGE_TRIBEMANAGER = "[AUDIT LOG] (TribeManager): ";
	public final static String AUDIT_MESSAGE_SHAREDSTORE = "[AUDIT LOG] (SharedStore): ";
	public final static String AUDIT_MESSAGE_MONITOR = "[AUDIT LOG] (Monitoring Agent): ";

	private static volatile String instanceName;
	
	public final static void log(String subsystem, String message) {
		if ( instanceName == null && Dispatcher.getInstance() != null ) {
			instanceName = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
		}
		System.err.println(instanceName + ":" + subsystem + message);
	}
}
