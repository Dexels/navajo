package com.dexels.navajo.document.comparatormanager;


public class ComparatorManagerFactory {

	private static ComparatorManager instance;

	private ComparatorManagerFactory() {
		// no instance
	}
	public static synchronized ComparatorManager getInstance() {
		return instance;
	}

	public static synchronized void setInstance(ComparatorManager instance) {
		ComparatorManagerFactory.instance = instance;
	}
	
	
}
