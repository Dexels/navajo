package com.dexels.navajo.tribe;

import java.io.Serializable;

import com.dexels.navajo.server.Dispatcher;

public abstract class Request implements Serializable {

	public String owner = null;
	public Answer predefined = null;
	private String guid = null;
	protected boolean blocking = true;
	// ignoreRequestOnSender specifies whether the request should NEVER be performed on the server from  which the request originated.
	private boolean ignoreRequestOnSender = false;
	
	public Request() {
		owner = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
		guid = hashCode() + "-" + System.currentTimeMillis();
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setPredefinedAnswer(Answer a) {
		predefined = a;
	}
	
	public Answer getPredefinedAnswer() {
		return predefined;
	}
	
	public abstract Answer getAnswer();

	public String getGuid() {
		return guid;
	}
	
	public boolean isBlocking() {
		return blocking;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	public boolean isIgnoreRequestOnSender() {
		return ignoreRequestOnSender;
	}

	public void setIgnoreRequestOnSender(boolean ignoreRequestOnSender) {
		this.ignoreRequestOnSender = ignoreRequestOnSender;
	}
}
