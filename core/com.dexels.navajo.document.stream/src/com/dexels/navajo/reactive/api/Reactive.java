package com.dexels.navajo.reactive.api;

public class Reactive {
	

	private static ReactiveFinder finderInstance;

	public static final String REACTIVE_SOURCE = "reactivesource";
	public static final String REACTIVE_TRANSFORMER = "reactivetransformer";
	public static final String REACTIVE_MAPPER = "reactivemapper";
	public static final String REACTIVE_PIPE = "reactivepipe";
	
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
