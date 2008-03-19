package com.dexels.navajo.server.enterprise.scheduler;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.UserException;

public class DummyTask implements TaskInterface {

	public TaskInterface getInstance() {
		return new DummyTask();
	}

	public void setNavajo(Navajo n) {
		
	}

	public void setTrigger(String s) throws UserException {
		
	}

	public String getId() {
		return null;
	}

	public void setKeepRequestResponse(boolean keepRequestResponse) {
	}

	public void setPersisted(boolean b) {
	}

}
