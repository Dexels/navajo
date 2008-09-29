package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEvent;

public class NavajoExceptionEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1224320416969244502L;
	
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
	public Navajo getEventNavajo() {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Message event = NavajoFactory.getInstance().createMessage(input, "__event__");
		try {
			input.addMessage(event);
			Property webservice = NavajoFactory.getInstance().createProperty(input, "Webservice", 
					Property.STRING_PROPERTY, getWebservice(), 0, "", Property.DIR_OUT);
			Property exception = NavajoFactory.getInstance().createProperty(input, "Exception", 
					Property.STRING_PROPERTY, getException().getMessage(), 0, "", Property.DIR_OUT);
			Property accessId = NavajoFactory.getInstance().createProperty(input, "Exception", 
					Property.STRING_PROPERTY, getAccessId(), 0, "", Property.DIR_OUT);
			Property user = NavajoFactory.getInstance().createProperty(input, "Exception", 
					Property.STRING_PROPERTY, getUser(), 0, "", Property.DIR_OUT);
			event.addProperty(webservice);
			event.addProperty(exception);
			event.addProperty(accessId);
			event.addProperty(user);
		} catch (NavajoException e) {
			e.printStackTrace(System.err);
		}
		return input;
	}

	public String getAccessId() {
		return accessId;
	}

	public String getUser() {
		return user;
	}
}
