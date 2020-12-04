/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
