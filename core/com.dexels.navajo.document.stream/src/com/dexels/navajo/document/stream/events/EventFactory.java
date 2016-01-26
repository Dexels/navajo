package com.dexels.navajo.document.stream.events;

import java.util.Collections;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;

public class EventFactory {
	
	public static NavajoStreamEvent messageDefinitionStarted(Message content, String path) {
		return new NavajoStreamEvent(path,NavajoEventTypes.MESSAGE_DEFINITION_STARTED,content,Collections.emptyMap());
	}
	
	public static NavajoStreamEvent messageDefinition(Message content, String path) {
		return new NavajoStreamEvent(path,NavajoEventTypes.MESSAGE_DEFINITION,content,Collections.emptyMap());
	}

	public static NavajoStreamEvent message(Message content, String path) {
		return new NavajoStreamEvent(path,NavajoEventTypes.MESSAGE,content,Collections.emptyMap());
	}

	public static NavajoStreamEvent messageStarted(Message content, String path) {
		return new NavajoStreamEvent(path,NavajoEventTypes.MESSAGE_STARTED,content,Collections.emptyMap());
	}
	
	public static NavajoStreamEvent arrayStarted(Message content, String path) {
		return new NavajoStreamEvent(path,NavajoEventTypes.ARRAY_STARTED,content,Collections.emptyMap());
	}

	public static NavajoStreamEvent arrayElementStarted(Message content, String path) {
		return new NavajoStreamEvent(path,NavajoEventTypes.ARRAY_ELEMENT_STARTED,content,Collections.emptyMap());
	}

	public static NavajoStreamEvent arrayElement(Message content, String path) {
		return new NavajoStreamEvent(path,NavajoEventTypes.ARRAY_ELEMENT,content,Collections.emptyMap());
	}

	public static NavajoStreamEvent arrayDone(Message content, String path) {
		return new NavajoStreamEvent(path,NavajoEventTypes.ARRAY_DONE,content,Collections.emptyMap());
	}
	
	public static NavajoStreamEvent header(Header content) {
		return new NavajoStreamEvent(null,NavajoEventTypes.HEADER,content,Collections.emptyMap());
	}

	public static NavajoStreamEvent navajoDone() {
		return new NavajoStreamEvent(null,NavajoEventTypes.NAVAJO_DONE,null,Collections.emptyMap());
	}
	public static NavajoStreamEvent navajoStarted() {
		return new NavajoStreamEvent(null,NavajoEventTypes.NAVAJO_STARTED,null,Collections.emptyMap());
	}
}
