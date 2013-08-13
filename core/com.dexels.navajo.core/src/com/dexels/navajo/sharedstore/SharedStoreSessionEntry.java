package com.dexels.navajo.sharedstore;

public class SharedStoreSessionEntry {

	private final String objectName;
	private final String formattedName;
	
	public SharedStoreSessionEntry(final String name, final String formattedName) {
		this.objectName = name;
		this.formattedName = formattedName;
	}
	
	public String getObjectName() {
		return objectName;
	}

	public String getFormattedName() {
		return formattedName;
	}

}
