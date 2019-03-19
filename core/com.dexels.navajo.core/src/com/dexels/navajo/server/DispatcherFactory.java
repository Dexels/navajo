package com.dexels.navajo.server;

public class DispatcherFactory {

	private static DispatcherInterface instance;

  
	public DispatcherFactory(DispatcherInterface injectedDispatcher) {	
		instance = injectedDispatcher;
	}
	
	public static synchronized DispatcherInterface getInstance() {
		return instance;
	}
	public static synchronized  void setInstance(DispatcherInterface dispatcher) {
		instance = dispatcher;
	}


}
