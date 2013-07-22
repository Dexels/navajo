package com.dexels.navajo.server;

public class GlobalManagerRepositoryFactory {
	public static GlobalManagerRepository instance;
	
	public static void setGlobalManagerInstance(GlobalManagerRepository repo) {
		instance = repo;
	}

	public static GlobalManagerRepository getGlobalManagerInstance() {
		return instance;
	}

}
