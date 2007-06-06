package com.dexels.navajo.server.enterprise.integrity;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;

public class DummyWorker implements WorkerInterface {

	public WorkerInterface getInstance() {
		return new DummyWorker();
	}
	
	public Navajo getResponse(Access a, Navajo request) {
		return null;
	}
	
	public void setResponse(Navajo request, Navajo response) {
		// Do nothing.
	}
	
	public void removeFromRunningRequestsList(Navajo request) {
		// Do nothing.
	}

}
