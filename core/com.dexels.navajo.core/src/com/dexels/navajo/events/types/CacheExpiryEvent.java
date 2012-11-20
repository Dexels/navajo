package com.dexels.navajo.events.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEvent;

public class CacheExpiryEvent implements NavajoEvent {

	private String webservice;
	private String key;
	
	private final static Logger logger = LoggerFactory
			.getLogger(CacheExpiryEvent.class);
	private static final long serialVersionUID = -7870880336884588772L;

	public CacheExpiryEvent(String webservice, String key) {
		this.webservice = webservice;
		this.key = key;
	}
	
	public Navajo getEventNavajo() {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Message event = NavajoFactory.getInstance().createMessage(input, "__event__");
		try {
			input.addMessage(event);
			Property webservice = NavajoFactory.getInstance().createProperty(input, "Webservice", 
					Property.STRING_PROPERTY, getWebservice(), 0, "", Property.DIR_OUT);
			event.addProperty(webservice);
			
			Property key = NavajoFactory.getInstance().createProperty(input, "Key", 
					Property.STRING_PROPERTY, getKey(), 0, "", Property.DIR_OUT);
			event.addProperty(key);
			
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		return input;
	}

	public String getWebservice() {
		return webservice;
	}

	public String getKey() {
		return key;
	}

}
