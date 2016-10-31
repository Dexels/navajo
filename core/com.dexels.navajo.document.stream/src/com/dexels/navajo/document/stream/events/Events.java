package com.dexels.navajo.document.stream.events;

import java.util.Collections;
import java.util.Map;

import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;

public class Events {
	
	public static NavajoStreamEvent messageDefinitionStarted(String name) {
		return new NavajoStreamEvent(name,NavajoEventTypes.MESSAGE_DEFINITION_STARTED,null,Collections.emptyMap());
	}
	
	public static NavajoStreamEvent messageDefinition(Msg message, String name) {
		return new NavajoStreamEvent(name,NavajoEventTypes.MESSAGE_DEFINITION,message,Collections.emptyMap());
	}

	public static NavajoStreamEvent message(Msg message, String name, Map<String,? extends Object> messageAttributes) {
		return new NavajoStreamEvent(name,NavajoEventTypes.MESSAGE,message,messageAttributes);
	}

	public static NavajoStreamEvent messageStarted(String name, Map<String,? extends Object> messageAttributes) {

		return new NavajoStreamEvent(name,NavajoEventTypes.MESSAGE_STARTED,null,messageAttributes);
	}
	
	public static NavajoStreamEvent arrayStarted(String name,Map<String,? extends Object> attributes) {
		return new NavajoStreamEvent(name,NavajoEventTypes.ARRAY_STARTED,null,attributes);
	}

	public static NavajoStreamEvent arrayElementStarted(Map<String,? extends Object> attributes) {
		return new NavajoStreamEvent("UnnamedArrayElement",NavajoEventTypes.ARRAY_ELEMENT_STARTED,null,attributes);
	}

	public static NavajoStreamEvent arrayElement(Msg m,Map<String,? extends Object> attributes) {
		return new NavajoStreamEvent("UnnamedArrayElement",NavajoEventTypes.ARRAY_ELEMENT,m,attributes);
	}

	public static NavajoStreamEvent arrayDone(String name) {
		return new NavajoStreamEvent(null,NavajoEventTypes.ARRAY_DONE,null,Collections.emptyMap());
	}

	public static NavajoStreamEvent done() {
		return new NavajoStreamEvent(null,NavajoEventTypes.NAVAJO_DONE,null,Collections.emptyMap());
	}
	public static NavajoStreamEvent started(NavajoHead head) {
		return new NavajoStreamEvent(null,NavajoEventTypes.NAVAJO_STARTED,head,Collections.emptyMap());
	}

	public static NavajoStreamEvent binaryStarted(String name) {
		return new NavajoStreamEvent(name, NavajoEventTypes.BINARY_STARTED, null, Collections.emptyMap());
	}

	public static NavajoStreamEvent binaryContent(String data) {
		return new NavajoStreamEvent(null, NavajoEventTypes.BINARY_CONTENT, data, Collections.emptyMap());
	}

	public static NavajoStreamEvent binaryDone() {
		return new NavajoStreamEvent(null, NavajoEventTypes.BINARY_DONE, null, Collections.emptyMap());
	}

//	public static Map<String,Prop> propMap(List<Prop> list) {
//		Map<String,Prop> result = new HashMap<>();
//		for (Prop prop : list) {
//			result.put(prop.name(), prop);
//		}
//		return Collections.unmodifiableMap(result);
//	}
}
