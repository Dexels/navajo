package com.dexels.navajo.server.enterprise.scheduler;

import java.util.Date;

import com.dexels.navajo.document.Navajo;

public class DummyTask implements TaskInterface {

	private static final long serialVersionUID = 4518273204586299917L;
	private TriggerInterface trigger;
	private String workflowDefinition;
	private String owner = null;
	private String tenant;
	

	public TaskInterface getInstance() {
		return new DummyTask();
	}

	@Override
	public TaskInterface setNavajo(Navajo n) {
		return this;
	}

	@Override
	public TaskInterface setTrigger(String s) throws TriggerException {
		return this;
		
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public void setKeepRequestResponse(boolean keepRequestResponse) {
	}

	@Override
	public void setPersisted(boolean b) {
	}

	public void run() {
		
	}

	@Override
	public String getTriggerDescription() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public String getWebservice() {
		return null;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public boolean isRunning() {
		return false;
	}

	@Override
	public void runTask() {
		
	}

	@Override
	public TaskInterface setTrigger(TriggerInterface trigger) {
		this.trigger = trigger;
		return this;
	}

	@Override
	public TriggerInterface getTrigger() {
		return this.trigger;
	}

	@Override
	public String getWorkflowId() {
		return null;
	}

	@Override
	public String getWorkflowDefinition() {
		return workflowDefinition;
	}

	@Override
	public void setWorkflowDefinition(String workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}

	@Override
	public void setForceSync(boolean b) {
//		this.forceSync = b;
		
	}

	@Override
	public boolean isProxy() {
		return false;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public TaskInterface setTenant(String tenant) {
		this.tenant = tenant;
		return this;
	}

	@Override
	public String getTenant() {
		return this.tenant;
	}

	@Override
	public boolean needsPersistence() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TaskInterface setId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRemove(boolean b, boolean c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isKeepRequestResponse() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Navajo getNavajo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTaskDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPersisted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInactive(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getFinishedTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Navajo getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskInterface setUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskInterface setPassword(String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
