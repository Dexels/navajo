package com.dexels.navajo.events.types;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.enterprise.tribe.TribeMemberInterface;

public class TribeMemberDownEvent implements NavajoEvent {

	private TribeMemberInterface tm;
	
	public TribeMemberDownEvent(TribeMemberInterface tm) {
		this.tm = tm;
	}

	public TribeMemberInterface getTm() {
		return tm;
	}
}
