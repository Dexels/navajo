/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.async;

import java.util.HashMap;
import java.util.Map;


public class NavajoClientResourceManager {

	private final Map<String,AsyncClient> clients = new HashMap<>();
	private static NavajoClientResourceManager instance = null;
	
	public synchronized void activate() {
		instance = this;
	}

	public synchronized void deactivate() {
		instance = null;
	}

	public static synchronized NavajoClientResourceManager getInstance() {
		return instance;
	}
	public void addAsyncClient(AsyncClient a) {
		clients.put(a.getName(), a);
	}

	public void removeAsyncClient(AsyncClient a) {
		clients.remove(a.getName());
	}
	
	public AsyncClient getAsyncClient(String name) {
		return clients.get(name);
	}

}
