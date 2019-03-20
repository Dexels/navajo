package com.dexels.navajo.server.enterprise.tribe;

public class TribeManagerFactory {

	private static TribeManagerInterface instance = null;
	
	public static TribeManagerInterface getInstance() {
		return instance;
	}

	public static final void shutdown() {
		if(instance==null) {
			return;
		}
		instance.terminate();
		instance = null;
	}

	public static void setInstance(TribeManagerInterface tm) {
		instance = tm;
	}
}
