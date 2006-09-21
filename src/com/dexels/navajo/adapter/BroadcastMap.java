package com.dexels.navajo.adapter;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class BroadcastMap implements Mappable {

	public String message;
	public int timeToLive;
	public String recipientExpression;
	
	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {

	}

	public void store() throws MappableException, UserException {
		Dispatcher.getInstance().setBroadcast(message,timeToLive,recipientExpression);
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRecipientExpression() {
		return recipientExpression;
	}

	public void setRecipientExpression(String recipientExpression) {
		this.recipientExpression = recipientExpression;
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

}
