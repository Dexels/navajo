package com.dexels.navajo.document.stream.events;

import com.dexels.navajo.document.Message;

public class MessageEvent extends NavajoStreamEvent {
	private final String path;
	private final Message message;
	
	public MessageEvent(Message message, String path) {
		super(NavajoEventTypes.MESSAGE);
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
