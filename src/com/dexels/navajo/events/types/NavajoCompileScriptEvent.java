package com.dexels.navajo.events.types;

import com.dexels.navajo.events.NavajoEvent;

public class NavajoCompileScriptEvent implements NavajoEvent {

	private String webservice;
	
	public NavajoCompileScriptEvent(String webservice) {
		this.webservice = webservice;
	}

	public String getWebservice() {
		return webservice;
	}
}
