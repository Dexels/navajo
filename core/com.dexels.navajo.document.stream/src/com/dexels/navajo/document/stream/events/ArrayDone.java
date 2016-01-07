package com.dexels.navajo.document.stream.events;

public class ArrayDone extends NavajoStreamEvent {
	private final String path;
	public ArrayDone(String path) {
		super(NavajoEventTypes.ARRAY_DONE);
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	
	public String toString() {
		return getClass().getName() +" : "+path;
	}

}
