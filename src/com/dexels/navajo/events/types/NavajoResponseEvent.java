package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;

public class NavajoResponseEvent implements NavajoEvent {

	private Navajo navajo;
	
	public Navajo getNavajo() {
		return navajo;
	}
	
}
