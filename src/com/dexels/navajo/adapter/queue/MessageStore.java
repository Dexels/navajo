package com.dexels.navajo.adapter.queue;

import com.dexels.navajo.document.types.Binary;

public interface MessageStore {

	public void putMessage(Queable handler, boolean failure);
	public void rewind();
	public Queable getNext() throws Exception;
	public int getSize();
	public void emptyQueue();
	
}
