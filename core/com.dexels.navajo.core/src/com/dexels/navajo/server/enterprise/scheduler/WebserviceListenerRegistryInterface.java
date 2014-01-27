package com.dexels.navajo.server.enterprise.scheduler;

import java.util.HashSet;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.enterprise.workflow.WorkFlowManagerInterface;

public interface WebserviceListenerRegistryInterface {

	public boolean afterWebservice(String webservice, Access a);
	public Navajo beforeWebservice(String webservice, Access a);
	public Navajo beforeWebservice(String webservice, Access a,
			HashSet<String> ignoreTaskList, boolean locally);

	public void removeTrigger(TriggerInterface afterWebserviceTrigger);
	public void registerTrigger(TriggerInterface afterWebserviceTrigger);
	public boolean afterWebservice(String webservice, Access a,
			HashSet<String> ignoreTaskList, boolean locally);
	public boolean isRegisteredWebservice(String string);
	public void setWorkflowManager(WorkFlowManagerInterface wfmi);

	
}
