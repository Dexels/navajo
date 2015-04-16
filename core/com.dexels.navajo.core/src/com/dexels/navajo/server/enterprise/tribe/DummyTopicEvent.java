package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

public class DummyTopicEvent implements TopicEvent {

	private final Serializable myMessage;
	
	public DummyTopicEvent(Serializable msg) {
		myMessage = msg;
	}
	
	@Override
	public Object getMessage() {
		return myMessage;
	}

	@Override
	public Object getSource() {
		return null;
	}

}
