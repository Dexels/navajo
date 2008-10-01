/*
 * Created on 26-jan-2006
 *
 * @author Dexels developer
 * @version $Id$
 */
package com.dexels.navajo.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.AuditLogEvent;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
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
	public final static String AUDIT_MESSAGE_USER = "User Log";
	public final static String AUDIT_MESSAGE_SCRIPTCOMPILER = "Script Compiler";
	public final static String AUDIT_MESSAGE_AUTHORISATION = "Authorisation Repository";

	private static volatile String instanceName;
	
	private String accessId;
	
	private final static Logger logger = Logger.getLogger("com.dexels.navajo.AuditLog");
	
	public final static void log(final String subsystem, final String message, Level level) {
		if ( instanceName == null && DispatcherFactory.getInstance() != null ) {
			instanceName = DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
		}
		logger.log(level, instanceName + ":" + subsystem + message);
		NavajoEventRegistry.getInstance().publishEvent(new AuditLogEvent(subsystem.toUpperCase(), message, level));
	}
	
	public final static void log(final String subsystem, final String message) {
		if ( instanceName == null && DispatcherFactory.getInstance() != null ) {
			instanceName = DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
		}
		logger.info(instanceName + ":" + subsystem + ": " + message);

		NavajoEventRegistry.getInstance().publishEvent(new AuditLogEvent(subsystem.toUpperCase(), message, Level.INFO));
	}
	
	public final static void log(final String subsystem, final String message,  Level level, String accessId) {
		if ( instanceName == null && DispatcherFactory.getInstance() != null ) {
			instanceName = DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
		}
		logger.info(instanceName + ":" + subsystem + message);
		AuditLogEvent ale = new AuditLogEvent(subsystem.toUpperCase(), message, level);
		ale.setAccessId(accessId);
		NavajoEventRegistry.getInstance().publishEvent(ale);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Level getLevel() {
		if ( level == null) {
			return Level.INFO;
		} else if ( level.equals(Level.INFO.getLocalizedName() )) {
			return Level.INFO;
		} else if ( level.equals(Level.SEVERE.getLocalizedName() )) {
			return Level.SEVERE;
		} else if ( level.equals(Level.WARNING.getLocalizedName() )) {
			return Level.WARNING;
		} else {
			return Level.INFO;
		}
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void kill() {
		
	}

	public void load(Access access) throws MappableException, UserException {
		if ( access != null ) {
			accessId = access.accessID;
		}
	}

	public void store() throws MappableException, UserException {
		AuditLogEvent ale = new AuditLogEvent(AUDIT_MESSAGE_USER, getMessage(), getLevel());
		ale.setAccessId(accessId);
		NavajoEventRegistry.getInstance().publishEvent(ale);
	}
}
