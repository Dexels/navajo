package com.dexels.navajo.document.stream.events;

public class NavajoStreamEvent {
	protected final NavajoEventTypes type;
	
	public NavajoStreamEvent(NavajoEventTypes type) {
		this.type = type;
	}

	public enum NavajoEventTypes {
		MESSAGE,HEADER,ARRAY_START,ARRAY_ELEMENT,ARRAY_DONE,NAVAJO_DONE
	}

	public NavajoEventTypes type() {
		return this.type;
	}
}
