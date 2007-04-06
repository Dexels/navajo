package com.dexels.navajo.adapter.queue;

import java.util.Stack;

public class MemoryStore implements MessageStore {

	private static Stack store = new Stack();
	
	public Queable getNext()  throws Exception {
		System.err.println("Getting work from store, size = " + store.size());
		if ( store.size() != 0 ) {
			synchronized (store ) {
				return (Queable) store.pop();
			}
		}
		return null;
	}
	
	public void putMessage(Queable handler, boolean failure) {
		System.err.println("Putting work in store: " + handler.getClass().getName());
		if ( !failure ) {
			synchronized (store ) {
				store.push(handler);
			}
		}
	}

	public int getSize() {
		return store.size();
	}

	public void emptyQueue() {
		synchronized (store ) {
			store.clear();
		}
	}

	public void rewind() {
		
	}
	
}
