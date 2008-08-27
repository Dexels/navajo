package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.enterprise.tribe.TribeMemberInterface;

/**
 * This event is used to communicate internally that a specific tribe member has passed away.
 * 
 * @author arjen
 *
 */
public class TribeMemberDownEvent implements NavajoEvent {

	private TribeMemberInterface tm;
	
	public TribeMemberDownEvent(TribeMemberInterface tm) {
		this.tm = tm;
	}

	public TribeMemberInterface getTm() {
		return tm;
	}

	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}
}
