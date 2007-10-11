package com.dexels.navajo.tribe;

public class SharedStoreFactory {

	private static volatile SharedStoreInterface instance = null;
	private static Object semaphore = new Object();
	
	public final static SharedStoreInterface getInstance() {
		
		if ( instance != null ) {
			return instance;
		}
		
		synchronized (semaphore ) {
			if ( instance != null ) {
				return instance;
			}
			instance = new SharedFileStore();
		}
		
		return instance;
	}
}
