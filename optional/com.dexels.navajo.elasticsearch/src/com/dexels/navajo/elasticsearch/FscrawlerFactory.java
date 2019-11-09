package com.dexels.navajo.elasticsearch;

public class FscrawlerFactory {
	private static FscrawlerService instance = null;

	public static void setInstance(FscrawlerService service) {
		instance = service;
	}
	
	public static FscrawlerService getInstance() {
		return instance;
	}

}
