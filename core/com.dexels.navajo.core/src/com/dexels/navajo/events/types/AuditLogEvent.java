/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events.types;

import java.util.Date;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.DispatcherFactory;

public class AuditLogEvent implements NavajoEvent, LevelEvent {

	private static final long serialVersionUID = -2957804056238962208L;
	
	private static final Logger logger = LoggerFactory
			.getLogger(AuditLogEvent.class);
	
	private static String instanceName = null;
	private String subSystem;
	private String message;
	private Level level;
	private String accessId;
	private Date created;
	
	public AuditLogEvent(QueuableFailureEvent qfe) {
		this("QUEUABLEFAILURE", 
		     ( qfe.getMyQueable() != null ? qfe.getMyQueable().getClass().getName() : "unknown"), Level.SEVERE);
	}
	
	public AuditLogEvent(TribeMemberDownEvent tde) {
		this("TRIBEMEMBERDOWN", tde.getTm().getMemberName(), Level.SEVERE);
	}
	
	public AuditLogEvent(NavajoHealthCheckEvent nhc) {
		this("HEALTHCHECK", nhc.getMessage(), nhc.getLevel());
	}
	
	public AuditLogEvent(ServerTooBusyEvent nhc) {
		this("SERVERTOOBUSY", "Number of simulatenously running service is "+nhc.toString(), Level.SEVERE);
	}
	
	public AuditLogEvent(String subSystem, String msg, Level level) {
		message = msg;
		this.subSystem = subSystem;
		this.level = level;
		this.created = new java.util.Date();
		try {
			if ( instanceName == null && DispatcherFactory.getInstance() != null) {
					instanceName = DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
			}
		} catch (NavajoException te) {
			//
		}
	}

	public String getMessage() {
		return message;
	}

	public String getSubSystem() {
		return subSystem;
	}

	@Override
	public Level getLevel() {
		return level;
	}
	
	@Override
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

	@Override
	public Navajo getEventNavajo() {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Message event = NavajoFactory.getInstance().createMessage(input, "__event__");
		try {
			input.addMessage(event);
			
			Property eventMessage = NavajoFactory.getInstance().createProperty(input, "Message", 
					Property.STRING_PROPERTY, getMessage(), 0, "", Property.DIR_OUT);
			Property eventLevel = NavajoFactory.getInstance().createProperty(input, "Level", 
					Property.STRING_PROPERTY, getLevel().getName(), 0, "", Property.DIR_OUT);
			Property eventSubsystem = NavajoFactory.getInstance().createProperty(input, "Subsystem", 
					Property.STRING_PROPERTY, getSubSystem(), 0, "", Property.DIR_OUT);
		
			event.addProperty(eventMessage);
			event.addProperty(eventLevel);
			event.addProperty(eventSubsystem);
			
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		return input;
	}
	
    @Override
    public boolean isSynchronousEvent() {
        return false;
    }
}
