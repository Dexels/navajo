package com.dexels.navajo.client.async;

import com.dexels.navajo.client.async.impl.AsyncClientImpl;


public class AsyncClientFactory {

	private static AsyncClient client = null;
	
	private AsyncClientFactory() {
		
		// no instantiation
	}
	public static AsyncClient getInstance() {
		synchronized (AsyncClientFactory.class) {
			if(client==null) {
				client = new AsyncClientImpl();
			}
			return client;
		}
	}

}
