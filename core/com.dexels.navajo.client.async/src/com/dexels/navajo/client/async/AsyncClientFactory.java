package com.dexels.navajo.client.async;



public class AsyncClientFactory {

	private static ManualAsyncClient client = null;
	
	private AsyncClientFactory() {
		// no instantiation
	}
	public static ManualAsyncClient getManualInstance() {
		synchronized (AsyncClientFactory.class) {
			return client;
		}
	}
	public static void setInstance(ManualAsyncClient asyncClientImpl) {
		client = asyncClientImpl;
	}
	



}
