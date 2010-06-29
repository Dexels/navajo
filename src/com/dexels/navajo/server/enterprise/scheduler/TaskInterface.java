package com.dexels.navajo.server.enterprise.scheduler;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.UserException;

public interface TaskInterface extends Serializable, TaskMXBean {

	public TaskInterface getInstance();
	public void setTrigger(String s) throws UserException;
	public void setNavajo(Navajo n);
	public String getId();
	public void setKeepRequestResponse(boolean keepRequestResponse);
	public void setPersisted(boolean b);
	public void runTask();
	
}
