package com.dexels.navajo.resource.navajo.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.client.async.AsyncClient;

public class NavajoClientResourceManager {

	private final Map<String,AsyncClient> clients = new HashMap<String,AsyncClient>();
	private static NavajoClientResourceManager instance = null;
	
	public synchronized void activate() {
		instance = this;
	}

	public synchronized void deactivate() {
		instance = null;
	}

	public synchronized static NavajoClientResourceManager getInstance() {
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
