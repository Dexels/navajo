package com.dexels.navajo.tribe;

import java.io.Serializable;

import com.dexels.navajo.server.Dispatcher;

public abstract class Request implements Serializable {

	public String owner = null;
	
	public Request() {
		owner = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
	}
	
	public String getOwner() {
		return owner;
	}
	
	public abstract Answer getAnswer();
	
}
