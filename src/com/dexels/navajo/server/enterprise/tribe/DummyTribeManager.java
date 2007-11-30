package com.dexels.navajo.server.enterprise.tribe;

import com.dexels.navajo.tribe.SmokeSignal;
import com.dexels.navajo.tribe.TribeMember;

public class DummyTribeManager implements TribeManagerInterface {

	public void terminate() {
	}

	public void broadcast(SmokeSignal m) {
	}

	public TribeMember getChief() {
		return null;
	}

}
