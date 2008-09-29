package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.UserException;

public class BroadcastMap implements Mappable {

	public String message;
	public int timeToLive;
	public String recipientExpression;
	
	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Access access) throws MappableException, UserException {

	}

	public void store() throws MappableException, UserException {
		DispatcherFactory.getInstance().setBroadcast(message,timeToLive,recipientExpression);
		
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
