package com.dexels.navajo.document.stream.events;

import com.dexels.navajo.document.Header;

public class HeaderEvent extends NavajoStreamEvent {
	private final Header header;
	public HeaderEvent(Header h) {
		super(NavajoEventTypes.HEADER);
		this.header = h;
	}
	public Header getHeader() {
		return header;
	}

	
	public String toString() {
		return getClass().getName() +" : "+header;
	}

}
