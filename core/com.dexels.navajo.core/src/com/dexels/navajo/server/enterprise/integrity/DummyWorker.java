/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.integrity;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;

public class DummyWorker implements WorkerInterface {

	public WorkerInterface getInstance() {
		return new DummyWorker();
	}
	
	@Override
	public Navajo getResponse(Access a, Navajo request) {
		return null;
	}
	
	@Override
	public void setResponse(Navajo request, Navajo response) {
		// Do nothing.
	}
	
	@Override
	public void removeFromRunningRequestsList(Navajo request) {
		// Do nothing.
	}

}
