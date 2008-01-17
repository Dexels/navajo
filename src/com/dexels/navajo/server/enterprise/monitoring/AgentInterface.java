package com.dexels.navajo.server.enterprise.monitoring;

public interface AgentInterface {

	/**
	 * Start the monitoring agent.
	 */
	public void start();
	
	/**
	 * Stop the monitoring agent.
	 */
	public void stop();
	
	/**
	 * Get some information about the type of agent.
	 * 
	 * @return
	 */
	public String getAgentInfo();
	
}
