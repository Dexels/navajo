package com.dexels.navajo.adapter.queue;

import java.util.HashSet;

import com.dexels.navajo.server.enterprise.queue.Queuable;

public interface MessageStore {

	/**
	 * Puts a Queuable Object into the MessageStore.
	 * 
	 * @param handler
	 * @param failure
	 */
	public void putMessage(Queuable handler, boolean failure);
	public void rewind();
	public Queuable getNext() throws Exception;
	public int getSize();
	public void emptyQueue();
	public HashSet<QueuedAdapter> getQueuedAdapters();
	public HashSet<QueuedAdapter> getDeadQueue();
	public void takeOverPersistedAdapters(String fromServer);
	
}
