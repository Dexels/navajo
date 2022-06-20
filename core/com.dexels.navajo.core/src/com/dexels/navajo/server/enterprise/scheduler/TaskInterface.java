/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.scheduler;

import java.io.Serializable;

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
}
