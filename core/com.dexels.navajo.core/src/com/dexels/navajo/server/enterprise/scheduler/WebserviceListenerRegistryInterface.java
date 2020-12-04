/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.scheduler;

import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.enterprise.workflow.WorkFlowManagerInterface;

public interface WebserviceListenerRegistryInterface {

	public boolean afterWebservice(String webservice, Access a);
	public Navajo beforeWebservice(String webservice, Access a);
	public Navajo beforeWebservice(String webservice, Access a,
			Set<String> ignoreTaskList, boolean locally);

	public void removeTrigger(TriggerInterface afterWebserviceTrigger);
	public void registerTrigger(TriggerInterface afterWebserviceTrigger);
	public boolean afterWebservice(String webservice, Access a,
			Set<String> ignoreTaskList, boolean locally);
	public boolean isActiveRegisteredWebservice(String string);
	public void setWorkflowManager(WorkFlowManagerInterface wfmi);
	
	/** Register a webservice as having a possible before- or afterwebservice trigger.
	 * Note that it doesn't have to be active still - that is determined in isActiveRegisteredWebservice()
	 * The workflow is used for de-registring.
	 */
    public void addRegisteredWebservice(String wfName, String webservice);
    public void unregisterWebservicesFromWorkflow(String wfName);

	
}
