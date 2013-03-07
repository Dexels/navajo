package com.dexels.navajo.sharedstore;

import java.io.Serializable;

public interface SharedStoreEntryFactory {

	public SharedStoreEntry constructSharedStoreEntry(String name, Serializable value);
	
}
