package com.dexels.navajo.adapter.queue;

import com.dexels.navajo.document.types.Binary;

public interface MessageStore {

	public void putMessage(Queable handler);
	public Queable getNext();
	
}
