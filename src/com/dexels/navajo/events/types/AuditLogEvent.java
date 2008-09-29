package com.dexels.navajo.events.types;

import java.util.Date;
import java.util.logging.Level;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.DispatcherFactory;

public class AuditLogEvent implements NavajoEvent, LevelEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2957804056238962208L;
	
	private static String instanceName = null;
	private String subSystem;
	private String message;
	private Level level;
	private String accessId;
	private Date created;
	
	public AuditLogEvent(String subSystem, String msg, Level level) {
		message = msg;
		this.subSystem = subSystem;
		this.level = level;
		this.created = new java.util.Date();
		try {
			if ( instanceName == null ) {
				if ( DispatcherFactory.getInstance() != null ) {
					instanceName = DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
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

	public Level getLevel() {
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
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Message event = NavajoFactory.getInstance().createMessage(input, "__event__");
		try {
			input.addMessage(event);
			
			Property message = NavajoFactory.getInstance().createProperty(input, "Message", 
					Property.STRING_PROPERTY, getMessage(), 0, "", Property.DIR_OUT);
			Property level = NavajoFactory.getInstance().createProperty(input, "Level", 
					Property.STRING_PROPERTY, getLevel().getName(), 0, "", Property.DIR_OUT);
			Property subsystem = NavajoFactory.getInstance().createProperty(input, "Subsystem", 
					Property.STRING_PROPERTY, getSubSystem(), 0, "", Property.DIR_OUT);
		
			event.addProperty(message);
			event.addProperty(level);
			event.addProperty(subsystem);
			
		} catch (NavajoException e) {
			e.printStackTrace(System.err);
		}
		return input;
	}
}
