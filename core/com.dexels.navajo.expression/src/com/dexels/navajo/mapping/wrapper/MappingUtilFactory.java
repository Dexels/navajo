package com.dexels.navajo.mapping.wrapper;

public final class MappingUtilFactory {
	private static MappingUtilInterface instance;
	
	private MappingUtilFactory() {}
	
	public static MappingUtilInterface getInstance() {
		return instance;
	}

	public static void setInstance(MappingUtilInterface instance) {
		MappingUtilFactory.instance = instance;
	}
	
}
