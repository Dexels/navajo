package com.dexels.navajo.workflow;

import com.dexels.navajo.server.jmx.GenericThreadMXBean;

public interface WorkFlowManagerMXBean extends GenericThreadMXBean {

	public String getId();
	public int getInstanceCount();
	public int getDefinitionCount();
	public void clearInstances(String definition);
	public void clearAllInstances();
	
}
