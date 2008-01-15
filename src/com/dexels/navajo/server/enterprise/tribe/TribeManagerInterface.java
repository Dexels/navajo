package com.dexels.navajo.server.enterprise.tribe;

import com.dexels.navajo.document.Navajo;

public interface TribeManagerInterface {

	public void terminate();
	public Navajo forward(Navajo in) throws Exception;
	public void broadcast(Navajo in) throws Exception;
	public void broadcast(SmokeSignal m);
	
}
