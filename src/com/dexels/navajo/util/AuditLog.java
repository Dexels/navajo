/*
 * Created on 26-jan-2006
 *
 * @author Dexels developer
 * @version $Id$
 */
package com.dexels.navajo.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.AuditLogEvent;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

/**
 * @author Arjen
 *
 */
public final class AuditLog implements Mappable {

	public String message;
	public String level;
	
	public final static String AUDIT_MESSAGE_TASK_SCHEDULER = "Task Scheduler";
	public final static String AUDIT_MESSAGE_ASYNC_RUNNER = "Async Runner";
	public final static String AUDIT_MESSAGE_STAT_RUNNER = "Statistics Runner";
	public final static String AUDIT_MESSAGE_INTEGRITY_WORKER = "Integrity Worker";
	public final static String AUDIT_MESSAGE_LOCK_MANAGER = "Locking Manager";
	public final static String AUDIT_MESSAGE_DISPATCHER = "Dispatcher";
	public final static String AUDIT_MESSAGE_CLOCK = "Clock";
	public final static String AUDIT_MESSAGE_WORKFLOW = "Workflow";
	public final static String AUDIT_MESSAGE_TRIBEMANAGER = "TribeManager";
	public final static String AUDIT_MESSAGE_SHAREDSTORE = "SharedStore";
	public final static String AUDIT_MESSAGE_MONITOR = "Monitoring Agent";
	public final static String AUDIT_MESSAGE_QUEUEDADAPTERS = "Queued Adapters";
	public final static String AUDIT_MESSAGE_USER = "USER";

	private static volatile String instanceName;
	
	private final static Logger logger = Logger.getLogger("com.dexels.navajo.AuditLog");
	
	public final static void log(final String subsystem, final String message, Level level) {
		if ( instanceName == null && Dispatcher.getInstance() != null ) {
			instanceName = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
		}
		logger.log(level, instanceName + ":" + subsystem + message);
		NavajoEventRegistry.getInstance().publishEvent(new AuditLogEvent(subsystem.toUpperCase(), message, level.getLocalizedName()));
	}
	
	public final static void log(final String subsystem, final String message) {
		if ( instanceName == null && Dispatcher.getInstance() != null ) {
			instanceName = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
		}
		logger.info(instanceName + ":" + subsystem + message);
		
		NavajoEventRegistry.getInstance().publishEvent(new AuditLogEvent(subsystem.toUpperCase(), message, Level.INFO.getLocalizedName()));
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void kill() {
		
	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		
	}

	public void store() throws MappableException, UserException {
		NavajoEventRegistry.getInstance().publishEvent(new AuditLogEvent(AUDIT_MESSAGE_USER, getMessage(), getLevel()));
	}
}
