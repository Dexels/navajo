/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.events;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.stream.api.Method;
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
	public static NavajoStreamEvent done(List<Method> methods) {
		return new NavajoStreamEvent(null,NavajoEventTypes.NAVAJO_DONE,methods,Collections.emptyMap());
	}

	public static NavajoStreamEvent started(NavajoHead head) {
		return new NavajoStreamEvent(null,NavajoEventTypes.NAVAJO_STARTED,head,Collections.emptyMap());
	}

//	list.add(Events.binaryStarted(p.getName(),p.getLength(),Optional.ofNullable(p.getDescription()),,Optional.ofNullable(p.getDirection())));

	public static NavajoStreamEvent binaryStarted(String name, int length, Optional<String> description, Optional<String> direction, Optional<String> subtype) {
		Map<String,Object> attributes = new HashMap<>();
		if(description.isPresent()) {
			attributes.put("description", description.get());
		}
		if(direction.isPresent()) {
			attributes.put("direction", direction.get());
		}
		if(subtype.isPresent()) {
			attributes.put("subtype", subtype.get());
		}
		attributes.put("length", length);
		return new NavajoStreamEvent(name, NavajoEventTypes.BINARY_STARTED, null, attributes);
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
