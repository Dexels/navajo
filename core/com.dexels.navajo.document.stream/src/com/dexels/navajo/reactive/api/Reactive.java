package com.dexels.navajo.reactive.api;

public class Reactive {
	private static ReactiveFinder finderInstance;

	public static void setFinderInstance(ReactiveFinder f) {
		Reactive.finderInstance = f;
	}
	
	public static ReactiveFinder finderInstance() {
		if(finderInstance==null) {
			throw new RuntimeException("No finder instance found");
		}
		return finderInstance;
	}
}
