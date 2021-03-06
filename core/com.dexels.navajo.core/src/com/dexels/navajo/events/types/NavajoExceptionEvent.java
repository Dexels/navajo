/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEvent;

public class NavajoExceptionEvent implements NavajoEvent {

	private static final long serialVersionUID = 1224320416969244502L;
	private static final Logger logger = LoggerFactory.getLogger(NavajoExceptionEvent.class);
	private String webservice;
	private Throwable myException;
	private String accessId;
	private String user;
	
	public NavajoExceptionEvent(String webservice, String accessId, String user, Throwable exception) {
		this.webservice = webservice;
		this.myException = exception;
		this.accessId = accessId;
		this.user = user;
	}

	/**
	 * Get the name of the webservice that was compiled.
	 * 
	 * @return
	 */
	public String getWebservice() {
		return webservice;
	}
	
	public Throwable getException() {
		return myException;
	}
	
	/**
	 * Return the event parameters as a Navajo object with a message __event__.
	 * 
	 */
	@Override
	public Navajo getEventNavajo() {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Message event = NavajoFactory.getInstance().createMessage(input, "__event__");
		try {
			input.addMessage(event);
			Property webserviceProperty = NavajoFactory.getInstance().createProperty(input, "Webservice", 
					Property.STRING_PROPERTY, getWebservice(), 0, "", Property.DIR_OUT);
			Property exception = NavajoFactory.getInstance().createProperty(input, "Exception", 
					Property.STRING_PROPERTY, getException().getMessage(), 0, "", Property.DIR_OUT);
			Property accessIdProperty = NavajoFactory.getInstance().createProperty(input, "AccessId", 
					Property.STRING_PROPERTY, getAccessId(), 0, "", Property.DIR_OUT);
			Property userProperty = NavajoFactory.getInstance().createProperty(input, "User", 
					Property.STRING_PROPERTY, getUser(), 0, "", Property.DIR_OUT);
			event.addProperty(webserviceProperty);
			event.addProperty(exception);
			event.addProperty(accessIdProperty);
			event.addProperty(userProperty);
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		return input;
	}

	public String getAccessId() {
		return accessId;
	}

	public String getUser() {
		return user;
	}
	@Override
    public boolean isSynchronousEvent() {
        return false;
    }
}
