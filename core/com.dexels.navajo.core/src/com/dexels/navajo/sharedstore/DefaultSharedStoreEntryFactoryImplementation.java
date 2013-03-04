package com.dexels.navajo.sharedstore;

import java.io.Serializable;

public class DefaultSharedStoreEntryFactoryImplementation implements
		SharedStoreEntryFactory {

	@Override
	public SharedStoreEntry constructSharedStoreEntry(String name, Serializable value) {
		return new SharedStoreEntry(name, value);
	}

}
