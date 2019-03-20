package com.dexels.navajo.server;

public class DispatcherFactory {

	private static DispatcherInterface instance;

  
	private DispatcherFactory(DispatcherInterface injectedDispatcher) {	
		instance = injectedDispatcher;
	}
	
	public static DispatcherFactory createDispatcher(DispatcherInterface injectedDispatcher) {
		return new DispatcherFactory(injectedDispatcher);
	}
	public static synchronized DispatcherInterface getInstance() {
		return instance;
	}
	public static synchronized  void setInstance(DispatcherInterface dispatcher) {
		instance = dispatcher;
	}


}
