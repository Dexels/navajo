package com.dexels.navajo.server.enterprise.workflow;

import com.dexels.navajo.document.Navajo;

public interface WorkFlowInterface {
	
	public String getDefinition();
	public String getMyId();
	public String getTenant();
	public Navajo getLocalNavajo();
	public int getNextWorkflowEventCounter();
	public boolean isDebug();
	
}
