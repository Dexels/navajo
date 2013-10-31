package com.dexels.navajo.events.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEvent;

/**
 * This type of events is fired when there are 1 or more full access log writes.
 * Triggered from StatisticsRunner.
 * 
 * @author arjen
 *
 */
public class AccessLogEvent implements NavajoEvent {

	private static final long serialVersionUID = 8600682387835507176L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(AccessLogEvent.class);
	private int accessLogCount;
	
	public AccessLogEvent(int accessLogCount) {
		this.accessLogCount = accessLogCount;
	}

	@Override
	public Navajo getEventNavajo() {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Message event = NavajoFactory.getInstance().createMessage(input, "__event__");
		try {
			input.addMessage(event);
			Property count = NavajoFactory.getInstance().createProperty(input, "AccessLogCount", 
					Property.INTEGER_PROPERTY, this.accessLogCount+"", 0, "", Property.DIR_OUT);
			event.addProperty(count);
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		return input;
	}

	public int getAccessLogCount() {
		return accessLogCount;
	}

}
