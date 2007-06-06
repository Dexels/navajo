package com.dexels.navajo.server.enterprise.integrity;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;

public interface WorkerInterface {

	public Navajo getResponse(Access a, Navajo request);
	public void setResponse(Navajo request, Navajo response);
	public void removeFromRunningRequestsList(Navajo request);
	
}
