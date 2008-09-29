package com.dexels.navajo.adapter.queue;

import java.util.HashSet;
import java.util.Stack;

import com.dexels.navajo.server.enterprise.queue.Queuable;

public class MemoryStore implements MessageStore {

	private static Stack<Queuable> store = new Stack<Queuable>();
	private static Stack<Queuable> failures = new Stack<Queuable>();
	
	public Queuable getNext()  throws Exception {
		System.err.println("Getting work from store, size = " + store.size());
		if ( store.size() != 0 ) {
			synchronized (store ) {
				Queuable q = (Queuable) store.pop();
				System.err.println("WAITTIME: " + ( q.getWaitUntil() - System.currentTimeMillis() ) );
				if ( q.getWaitUntil() < System.currentTimeMillis() ) {
					return q;
				} else {
					store.push(q);
				}
			}
		}
		return null;
	}
	
	public void putMessage(Queuable handler, boolean failure) {
		if ( !failure ) {
			synchronized (store ) {
				store.push(handler);
			}
		} else {
			failures.push(handler);
		}
	}

	public int getSize() {
		return store.size();
	}

	public void emptyQueue() {
		synchronized (store ) {
			store.clear();
			failures.clear();
		}
	}

	public void rewind() {
		
	}

	public HashSet<QueuedAdapter> getQueuedAdapters() {
		// TODO Auto-generated method stub
		return null;
	}

	public HashSet<QueuedAdapter> getDeadQueue() {
		System.err.println("IN GET DEADQUEUE, SIZE: " + failures.size());
		return new HashSet( failures );
	}

	public void takeOverPersistedAdapters(String fromServer) {
		// TODO Auto-generated method stub
		
	}
	
}
