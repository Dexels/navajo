package com.dexels.navajo.sharedstore;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractSharedStore implements SharedStoreInterface {

	private Set<SharedStoreListener> listeners = new HashSet<SharedStoreListener>();
	
	public void addSharedStoreListener(SharedStoreListener ssl) {
		listeners.add(ssl);
	}
	
	public void removeSharedStoreListener(SharedStoreListener ssl) {
		listeners.remove(ssl);
	}
	
	public final void remove(String parent, String name) {
		if ( exists(parent, name) ) {
			saveRemove(parent, name);
			removeEvent(parent, name);
		}
	}
	
	private void removeEvent(String parent, String name) {
		for ( SharedStoreListener ssl : listeners ) {
			ssl.onRemove(parent, name);
		}
	}
}
