package com.dexels.navajo.server.enterprise.scheduler;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;

public interface WebserviceListenerRegistryInterface {

	public boolean afterWebservice(String webservice, Access a);
	public Navajo beforeWebservice(String webservice, Access a);
	
}
