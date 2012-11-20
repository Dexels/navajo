package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.enterprise.workflow.WorkFlowInterface;

public abstract class WorkflowEvent {

	private final String workflowId;
	private final String workflowName;
	private final long timestamp;

	private final Navajo workflowState;
	private final int workflowEventCount;
	private final boolean debug;
	
	public WorkflowEvent(WorkFlowInterface wf) {
		workflowId = wf.getMyId();
		workflowName = wf.getDefinition();
		timestamp  = System.currentTimeMillis();
		workflowState = wf.getLocalNavajo().copy();
		workflowEventCount = wf.getNextWorkflowEventCounter();
		debug = wf.isDebug();
	}
	
	public Navajo getWorkflowState() {
		return workflowState;
	}

	public String getWorkflowName() {
		return workflowName;
	}
	
	public String getWorkflowId() {
		return workflowId;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public int getWorkflowEventCount() {
		return workflowEventCount;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
}

