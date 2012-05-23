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

	public void run() {
		// TODO Auto-generated method stub
		
	}

	public String getTriggerDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWebservice() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	public void runTask() {
		// TODO Auto-generated method stub
		
	}

}
