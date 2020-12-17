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
