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
