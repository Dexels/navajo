package com.dexels.navajo.server.enterprise.scheduler;

import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.enterprise.workflow.WorkFlowManagerInterface;

public class DummyWebserviceListener implements WebserviceListenerRegistryInterface {

	@Override
	public boolean afterWebservice(String webservice, Access a) {
		return false;
	}

	@Override
	public Navajo beforeWebservice(String webservice, Access a) {
		return null;
	}

	@Override
	public Navajo beforeWebservice(String webservice, Access a,
			Set<String> ignoreTaskList, boolean locally) {
		return null;
	}

	@Override
	public void removeTrigger(TriggerInterface afterWebserviceTrigger) {
		
	}

	@Override
	public void registerTrigger(TriggerInterface afterWebserviceTrigger) {
	}

	@Override
	public boolean afterWebservice(String webservice, Access a,
			Set<String> ignoreTaskList, boolean locally) {
		return false;
	}

	@Override
	public boolean isActiveRegisteredWebservice(String string) {
		return false;
	}

	@Override
	public void setWorkflowManager(WorkFlowManagerInterface wfmi) {
		
	}

    @Override
    public void addRegisteredWebservice(String wfName, String webservice) {
        
    }

    @Override
    public void unregisterWebservicesFromWorkflow(String wfName) {
        
    }

}
