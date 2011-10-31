package com.dexels.navajo.client.async;


public class AsyncClientFactory {

	private static AsyncClient client = null;
	
	private AsyncClientFactory() {
		
		// no instantiation
	}
	public static AsyncClient getInstance() {
		synchronized (AsyncClientFactory.class) {
			if(client==null) {
				client = new AsyncClient();
			}
			return client;
		}
	}

}
