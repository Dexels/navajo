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
	
	/**
	 * Rewind the messagestore to the first queueable object.
	 */
	public void rewind();
	
	/**
	 * Gets the next queuable object from the messagestore.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Queuable getNext() throws Exception;
	
	/**
	 * The number of queuable objects in the message store.
	 * 
	 * @return
	 */
	public int getSize();
	
	/**
	 * Empty the entire message store.
	 */
	public void emptyQueue();
	
	/**
	 * Get the set of all queuable objects.
	 * @return
	 */
	public HashSet<QueuedAdapter> getQueuedAdapters();
	
	/**
	 * Get the set of all queuable objects residing in the dead queue.
	 * 
	 * @return
	 */
	public HashSet<QueuedAdapter> getDeadQueue();
	
	/**
	 * This method can be used to take over control of persisted workflow of another server.
	 * TODO: Figure out how to move Binary objects (file references!).
	 * 
	 * @param fromServer
	 */
	public void takeOverPersistedAdapters(String fromServer);
	
}
