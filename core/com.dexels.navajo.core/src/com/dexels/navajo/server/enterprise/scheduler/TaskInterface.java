package com.dexels.navajo.server.enterprise.scheduler;

import java.io.Serializable;
import java.util.Date;

import com.dexels.navajo.document.Navajo;

public interface TaskInterface extends Serializable, TaskMXBean {

	@Override
	public String getId();
	public TaskInterface setNavajo(Navajo n);
	public void setKeepRequestResponse(boolean keepRequestResponse);
	public void setPersisted(boolean b);
	public void runTask();
	public TaskInterface setTrigger(TriggerInterface trigger);
	public TaskInterface setTrigger(String s) throws TriggerException;
	public TriggerInterface getTrigger();
	public String getWorkflowId();
	public String getWorkflowDefinition();
	public void setWorkflowDefinition(String workflowDefinition);
	public void setForceSync(boolean b);
	public boolean isProxy();
	public String getOwner();
	public TaskInterface setTenant(String tenant);
	public String getTenant();
	public boolean needsPersistence();
	public TaskInterface setId(String id);
	public void setRemove(boolean b, boolean c);
	public boolean isKeepRequestResponse();
	public Navajo getNavajo();
	public String getClientId();
	public String getPassword();
	public String getTaskDescription();
	public boolean isPersisted();
	public void setInactive(boolean b);
	public Date getStartTime();
	public Date getFinishedTime();
	public boolean isFinished();
	public String getStatus();
	public String getErrorMessage();
	public Navajo getResponse();
	public TaskInterface setUsername(String username);
	public TaskInterface setPassword(String password);	
}
