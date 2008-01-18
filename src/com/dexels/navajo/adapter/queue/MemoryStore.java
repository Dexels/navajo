package com.dexels.navajo.adapter.queue;

import java.util.HashSet;
import java.util.Stack;

import com.dexels.navajo.server.enterprise.queue.Queuable;

public class MemoryStore implements MessageStore {

	private static Stack<Queuable> store = new Stack<Queuable>();
	
	public Queuable getNext()  throws Exception {
		System.err.println("Getting work from store, size = " + store.size());
		if ( store.size() != 0 ) {
			synchronized (store ) {
				return (Queuable) store.pop();
			}
		}
		return null;
	}
	
	public void putMessage(Queuable handler, boolean failure) {
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

	public HashSet<QueuedAdapter> getQueuedAdapters() {
		// TODO Auto-generated method stub
		return null;
	}

	public HashSet<QueuedAdapter> getDeadQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void takeOverPersistedAdapters(String fromServer) {
		// TODO Auto-generated method stub
		
	}
	
}
