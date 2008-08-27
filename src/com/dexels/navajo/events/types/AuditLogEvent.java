package com.dexels.navajo.events.types;

import java.util.Date;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.Dispatcher;

public class AuditLogEvent implements NavajoEvent {

	private static String instanceName = null; //Dispatcher.getInstance().getNavajoConfig().getInstanceName();
	private String subSystem;
	private String message;
	private String level;
	private String accessId;
	private Date created;
	
	public AuditLogEvent(String subSystem, String msg, String level) {
		message = msg;
		this.subSystem = subSystem;
		this.level = level;
		this.created = new java.util.Date();
		try {
			if ( instanceName == null ) {
				if ( Dispatcher.getInstance() != null ) {
					instanceName = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
				}
			}
		} catch (Throwable te) {}
	}

	public String getMessage() {
		return message;
	}

	public String getSubSystem() {
		return subSystem;
	}

	public String getLevel() {
		return level;
	}
	
	public String toString() {
		return (instanceName + ":" + subSystem + "-> " + message + " (" + level + ")");
	}

	public String getInstanceName() {
		return instanceName;
	}

	public Date getCreated() {
		return created;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}
}
