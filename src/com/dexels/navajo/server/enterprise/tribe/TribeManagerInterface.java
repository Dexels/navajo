package com.dexels.navajo.server.enterprise.tribe;

import com.dexels.navajo.tribe.SmokeSignal;
import com.dexels.navajo.tribe.TribeMember;


public interface TribeManagerInterface {

	public void terminate();
	public TribeMember getChief();
	public void broadcast(SmokeSignal m);
	
}
