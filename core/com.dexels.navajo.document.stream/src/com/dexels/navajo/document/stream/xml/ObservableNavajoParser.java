/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.stream.NavajoStreamHandler;
import com.dexels.navajo.document.stream.api.Method;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.Events;
//import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.impl.StreamSaxHandler;


public class ObservableNavajoParser  {
	
	private StreamSaxHandler feeder;
	List<NavajoStreamEvent> list = new ArrayList<>();
	
	public ObservableNavajoParser() {
		this.feeder = new StreamSaxHandler(new NavajoStreamHandler(){

			@Override
			public void messageDone(Map<String, String> attributes, List<Prop> properties) {
				list.add(Events.message(Msg.create(properties), attributes.get("name"),attributes));
			}

			@Override
			public void messageStarted(Map<String, String> attributes) {
				list.add(Events.messageStarted(attributes.get("name"),attributes));
			}

			@Override
			public void messageDefinitionStarted(Map<String, String> attributes) {
				list.add(Events.messageDefinitionStarted(attributes.get("name")));
			}

			@Override
			public void messageDefinition(Map<String, String> attributes, List<Prop> properties) {
				list.add(Events.messageDefinition(Msg.createDefinition(properties), attributes.get("name")));				
			}

			@Override
			public void arrayStarted(Map<String, String> attributes) {
				list.add(Events.arrayStarted(attributes.get("name"),attributes));
			}

			@Override
			public void arrayElementStarted() {
				list.add(Events.arrayElementStarted(Collections.emptyMap()));
			}

			@Override
			public void arrayElement(List<Prop> properties) {
				list.add(Events.arrayElement(Msg.createElement(properties), Collections.emptyMap()));				
			}

			@Override
			public void arrayDone(String name) {
				list.add(Events.arrayDone(name));				
			}

			@Override
			public void navajoStart(NavajoHead head) {
				list.add(Events.started(head));
			}

			@Override
			public void navajoDone(List<Method> methods) {
				list.add(Events.done(methods));				
			}

			@Override
			public void binaryStarted(String name, int length, Optional<String> description, Optional<String> direction, Optional<String> subtype) {
				list.add(Events.binaryStarted(name,length,description,direction,subtype));
			}

			@Override
			public void binaryContent(String data) {
				list.add(Events.binaryContent(data));
			}

			@Override
			public void binaryDone() {
				list.add(Events.binaryDone());
			}
		});
	}

	public List<NavajoStreamEvent> parseXmlEvent(final XMLEvent xmlEvent) {
		switch(xmlEvent.getType()) {
			case START_DOCUMENT:
				ObservableNavajoParser.this.feeder.startDocument();
				break;
			case END_DOCUMENT:
				ObservableNavajoParser.this.feeder.endDocument();
				break;
			case START_ELEMENT:
				ObservableNavajoParser.this.feeder.startElement(xmlEvent.getText(),xmlEvent.getAttributes());
				break;
			case END_ELEMENT:
				ObservableNavajoParser.this.feeder.endElement(xmlEvent.getText());
				break;
			case TEXT:
				ObservableNavajoParser.this.feeder.text(xmlEvent.getText());
				break;
		}
		List<NavajoStreamEvent> copy = new ArrayList<>(list);
		list.clear();
		return copy;
	}

}
