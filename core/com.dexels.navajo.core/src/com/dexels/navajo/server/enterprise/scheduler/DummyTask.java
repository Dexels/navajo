package com.dexels.navajo.server.enterprise.scheduler;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.UserException;

public class DummyTask implements TaskInterface {

	private static final long serialVersionUID = 4518273204586299917L;
	private TriggerInterface trigger;
	private String workflowDefinition;

	@Override
	public TaskInterface getInstance() {
		return new DummyTask();
	}

	@Override
	public void setNavajo(Navajo n) {
		
	}

	@Override
	public void setTrigger(String s) throws UserException {
		
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
	public void setTrigger(TriggerInterface trigger) {
		this.trigger = trigger;
	}

	@Override
	public TriggerInterface getTrigger() {
		return this.trigger;
	}

	@Override
	public String getWorkflowId() {
		// TODO Auto-generated method stub
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

}
