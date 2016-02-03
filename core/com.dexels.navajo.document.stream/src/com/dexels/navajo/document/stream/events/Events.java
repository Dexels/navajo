package com.dexels.navajo.document.stream.events;

import java.util.Collections;
import java.util.List;

import com.dexels.navajo.document.stream.api.NavajoHead;
//import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;

public class Events {
	
	public static NavajoStreamEvent messageDefinitionStarted(String name) {
		return new NavajoStreamEvent(name,NavajoEventTypes.MESSAGE_DEFINITION_STARTED,null,Collections.emptyMap());
	}
	
	public static NavajoStreamEvent messageDefinition(List<Prop> properties, String name) {
		return new NavajoStreamEvent(name,NavajoEventTypes.MESSAGE_DEFINITION,properties,Collections.emptyMap());
	}

	public static NavajoStreamEvent message(List<Prop> properties, String name) {
		return new NavajoStreamEvent(name,NavajoEventTypes.MESSAGE,properties,Collections.emptyMap());
	}

	public static NavajoStreamEvent messageStarted(String name) {
		return new NavajoStreamEvent(name,NavajoEventTypes.MESSAGE_STARTED,null,Collections.emptyMap());
	}
	
	public static NavajoStreamEvent arrayStarted(String name) {
		return new NavajoStreamEvent(name,NavajoEventTypes.ARRAY_STARTED,null,Collections.emptyMap());
	}

	public static NavajoStreamEvent arrayElementStarted(String name) {
		return new NavajoStreamEvent(name,NavajoEventTypes.ARRAY_ELEMENT_STARTED,null,Collections.emptyMap());
	}

	public static NavajoStreamEvent arrayElement(List<Prop> properties, String name) {
		return new NavajoStreamEvent(name,NavajoEventTypes.ARRAY_ELEMENT,properties,Collections.emptyMap());
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

//	public static Map<String,Prop> propMap(List<Prop> list) {
//		Map<String,Prop> result = new HashMap<>();
//		for (Prop prop : list) {
//			result.put(prop.name(), prop);
//		}
//		return Collections.unmodifiableMap(result);
//	}
}
