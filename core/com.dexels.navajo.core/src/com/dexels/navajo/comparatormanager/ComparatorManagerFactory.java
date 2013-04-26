package com.dexels.navajo.comparatormanager;

public class ComparatorManagerFactory {

	private static ComparatorManager instance;

	public static synchronized ComparatorManager getInstance() {
		return instance;
	}

	public static synchronized void setInstance(ComparatorManager instance) {
		ComparatorManagerFactory.instance = instance;
	}
	
	
}
