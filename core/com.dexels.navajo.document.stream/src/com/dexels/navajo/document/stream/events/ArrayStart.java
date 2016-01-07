package com.dexels.navajo.document.stream.events;

import com.dexels.navajo.document.Message;

public class ArrayStart extends NavajoStreamEvent {
	private final String path;
	private final Message message;
	
	public ArrayStart(Message message, String path) {
		super(NavajoEventTypes.ARRAY_START);
		this.path = path;
		this.message = message;
	}
	public String getPath() {
		return path;
	}

	public Message getMessage() {
		return message;
	}
	
	public String toString() {
		return getClass().getName() +" : "+path;
	}

}
