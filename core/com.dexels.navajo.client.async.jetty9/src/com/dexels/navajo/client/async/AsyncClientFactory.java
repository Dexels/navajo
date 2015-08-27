package com.dexels.navajo.client.async;

import com.dexels.navajo.client.async.impl.AsyncClientImpl;


public class AsyncClientFactory {

	private static ManualAsyncClient client = null;
	
	private AsyncClientFactory() {
		
		// no instantiation
	}
	public static ManualAsyncClient getManualInstance() {
		synchronized (AsyncClientFactory.class) {
			if(client==null) {
				client = new AsyncClientImpl();
			}
			return client;
		}
	}
	
//	public static ManualAsyncClient getServerInstance(String url, )

	public static ManualAsyncClient createInstance() {
		return new AsyncClientImpl();
	}


}
