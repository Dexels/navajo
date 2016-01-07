package com.dexels.navajo.document.stream.events;

import com.dexels.navajo.document.Message;

public class ArrayElement extends NavajoStreamEvent {
	private final String path;
	private final Message message;
	
	public ArrayElement(Message message, String path) {
		super(NavajoEventTypes.ARRAY_ELEMENT);
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
