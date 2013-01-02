package com.dexels.navajo.tipi.context;



public class ContextFactory {
	private static ContextManager instance;
	
	public static void setInstance(ContextManager cm) {
		ContextFactory.instance = cm;
	}

	public static ContextManager getInstance() {
		return ContextFactory.instance;
	}
}
