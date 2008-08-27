package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEvent;

public class NavajoCompileScriptEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1224320416969244502L;
	
	private String webservice;
	
	public NavajoCompileScriptEvent(String webservice) {
		this.webservice = webservice;
	}

	public String getWebservice() {
		return webservice;
	}
	
	public Navajo getEventNavajo() {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Message event = NavajoFactory.getInstance().createMessage(input, "__event__");
		try {
			input.addMessage(event);
			Property webservice = NavajoFactory.getInstance().createProperty(input, "Webservice", 
					Property.STRING_PROPERTY, getWebservice(), 0, "", Property.DIR_OUT);
			event.addProperty(webservice);
		} catch (NavajoException e) {
			e.printStackTrace(System.err);
		}
		return input;
	}
}
