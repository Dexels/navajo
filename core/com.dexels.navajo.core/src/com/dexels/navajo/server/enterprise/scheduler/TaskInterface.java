package com.dexels.navajo.server.enterprise.scheduler;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.UserException;

public interface TaskInterface extends Serializable, TaskMXBean {

	public TaskInterface getInstance();
	public void setTrigger(String s) throws UserException;
	public void setNavajo(Navajo n);
	@Override
	public String getId();
	public void setKeepRequestResponse(boolean keepRequestResponse);
	public void setPersisted(boolean b);
	public void runTask();
	public void setTrigger(TriggerInterface trigger);
	public TriggerInterface getTrigger();
	public String getWorkflowId();
	public String getWorkflowDefinition();
	public void setWorkflowDefinition(String workflowDefinition);
	public void setForceSync(boolean b);
	public boolean isProxy();
	
	
}
