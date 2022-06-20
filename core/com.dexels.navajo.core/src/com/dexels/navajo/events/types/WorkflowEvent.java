/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.enterprise.workflow.WorkFlowInterface;

public abstract class WorkflowEvent {

	private final String workflowId;
	private final String workflowName;
	private final String workflowTenant;
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
		workflowTenant = wf.getTenant();
		debug = wf.isDebug();
	}
	
	public Navajo getWorkflowState() {
		return workflowState;
	}

	public String getWorkflowName() {
		return workflowName;
	}
	
	public String getWorkflowTenant() {
        return workflowTenant;
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

