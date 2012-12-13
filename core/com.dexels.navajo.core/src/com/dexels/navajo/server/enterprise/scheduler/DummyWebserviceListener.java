package com.dexels.navajo.server.enterprise.scheduler;

import java.util.HashSet;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;

public class DummyWebserviceListener implements WebserviceListenerRegistryInterface {

	public boolean afterWebservice(String webservice, Access a) {
		// TODO Auto-generated method stub
		return false;
	}

	public Navajo beforeWebservice(String webservice, Access a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Navajo beforeWebservice(String webservice, Access a,
			HashSet<String> ignoreTaskList, boolean locally) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeTrigger(TriggerInterface afterWebserviceTrigger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerTrigger(TriggerInterface afterWebserviceTrigger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean afterWebservice(String webservice, Access a,
			HashSet<String> ignoreTaskList, boolean locally) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRegisteredWebservice(String string) {
		// TODO Auto-generated method stub
		return false;
	}

}
