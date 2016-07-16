package com.dexels.navajo.server.enterprise.scheduler;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.UserException;

public interface TaskInterface extends Serializable, TaskMXBean {

	@Override
	public String getId();
	public TaskInterface setNavajo(Navajo n);
	public void setKeepRequestResponse(boolean keepRequestResponse);
	public void setPersisted(boolean b);
	public void runTask();
	public TaskInterface setTrigger(TriggerInterface trigger);
	public TaskInterface setTrigger(String s) throws UserException;
	public TriggerInterface getTrigger();
	public String getWorkflowId();
	public String getWorkflowDefinition();
	public void setWorkflowDefinition(String workflowDefinition);
	public void setForceSync(boolean b);
	public boolean isProxy();
	public String getOwner();
	public TaskInterface setTenant(String tenant);
	public String getTenant();	
}
