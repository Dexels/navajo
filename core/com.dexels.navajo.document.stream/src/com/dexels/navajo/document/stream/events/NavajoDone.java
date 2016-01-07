package com.dexels.navajo.document.stream.events;

public class NavajoDone extends NavajoStreamEvent {
	public NavajoDone() {
		super(NavajoEventTypes.NAVAJO_DONE);
	}

	public String toString() {
		return getClass().getName();
	}

}
