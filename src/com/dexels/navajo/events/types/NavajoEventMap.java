package com.dexels.navajo.events.types;

import java.util.HashMap;

import com.dexels.navajo.events.NavajoEvent;

/**
 * This class contains a mapping between Navajo Server Event names and their NavajoEvent class.
 * 
 * @author arjen
 *
 */
public class NavajoEventMap {

	public final static HashMap<String,Class<? extends NavajoEvent>> navajoEvents;
	
	public static final String HEALTH_CHECK_EVENT = "healthcheck";
	public static final String COMPILESCRIPT_EVENT = "compilescript";
	public static final String EXCEPTION_EVENT = "exception";
	public static final String SERVER_TOO_BUSY_EVENT = "servertoobusy";
	public static final String REQUEST_EVENT = "request";
	public static final String RESPONSE_EVENT = "respons";
	public static final String TRIBEMEMBER_DOWN_EVENT = "tribememberdown";
	public static final String AUDITLOG_EVENT = "auditlog";
	public static final String QUEUABLE_FINISHED_EVENT = "queuablefinished";
	public static final String QUEUABLE_FAILURE_EVENT = "queuablefailure";
	
	
	static {
		
		navajoEvents = new HashMap<String,Class<? extends NavajoEvent>>();
		
		navajoEvents.put(HEALTH_CHECK_EVENT, NavajoHealthCheckEvent.class);
		navajoEvents.put(COMPILESCRIPT_EVENT, NavajoCompileScriptEvent.class);
		navajoEvents.put(EXCEPTION_EVENT, NavajoExceptionEvent.class);
		navajoEvents.put(SERVER_TOO_BUSY_EVENT, ServerTooBusyEvent.class);
		navajoEvents.put(REQUEST_EVENT, NavajoRequestEvent.class);
		navajoEvents.put(RESPONSE_EVENT, NavajoResponseEvent.class);
		navajoEvents.put(TRIBEMEMBER_DOWN_EVENT, TribeMemberDownEvent.class);
		navajoEvents.put(AUDITLOG_EVENT, AuditLogEvent.class);
		navajoEvents.put(QUEUABLE_FINISHED_EVENT, QueuableFinishedEvent.class);
		navajoEvents.put(QUEUABLE_FAILURE_EVENT, QueuableFailureEvent.class);
		
	}
	
	public static Class<? extends NavajoEvent> getEventClass(String description) {
		return navajoEvents.get(description);
	}
}
