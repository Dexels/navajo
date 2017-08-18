package com.dexels.navajo.document.stream.xml;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.reactivestreams.Subscriber;

import com.dexels.navajo.document.stream.NavajoStreamHandler;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.Events;
//import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.impl.StreamSaxHandler;


public class ObservableNavajoParser  {
	
	private StreamSaxHandler feeder;
	
	public ObservableNavajoParser(final Subscriber<? super NavajoStreamEvent> subscriber) {
		this.feeder = new StreamSaxHandler(new NavajoStreamHandler(){

			@Override
			public void messageDone(Map<String, String> attributes, List<Prop> properties) {
				subscriber.onNext(Events.message(Msg.create(properties), attributes.get("name"),attributes));
			}

			@Override
			public void messageStarted(Map<String, String> attributes) {
				subscriber.onNext(Events.messageStarted(attributes.get("name"),attributes));
			}

			@Override
			public void messageDefinitionStarted(Map<String, String> attributes) {
				subscriber.onNext(Events.messageDefinitionStarted(attributes.get("name")));
			}

			@Override
			public void messageDefinition(Map<String, String> attributes, List<Prop> properties) {
				subscriber.onNext(Events.messageDefinition(Msg.createDefinition(properties), attributes.get("name")));				
			}

			@Override
			public void arrayStarted(Map<String, String> attributes) {
				subscriber.onNext(Events.arrayStarted(attributes.get("name"),attributes));
			}

			@Override
			public void arrayElementStarted() {
				subscriber.onNext(Events.arrayElementStarted(Collections.emptyMap()));
			}

			@Override
			public void arrayElement(List<Prop> properties) {
				subscriber.onNext(Events.arrayElement(Msg.createElement(properties), Collections.emptyMap()));				
			}

			@Override
			public void arrayDone(String name) {
				subscriber.onNext(Events.arrayDone(name));				
			}

			@Override
			public void navajoStart(NavajoHead head) {
				subscriber.onNext(Events.started(head));
			}

			@Override
			public void navajoDone() {
				subscriber.onNext(Events.done());				
			}

			@Override
			public void binaryStarted(String name) {
				subscriber.onNext(Events.binaryStarted(name));
			}

			@Override
			public void binaryContent(String data) {
				subscriber.onNext(Events.binaryContent(data));
			}

			@Override
			public void binaryDone() {
				subscriber.onNext(Events.binaryDone());
			}
		});
	}

	public int parseXmlEvent(final XMLEvent xmlEvent) {
		switch(xmlEvent.getType()) {
			case START_DOCUMENT:
				return ObservableNavajoParser.this.feeder.startDocument();
			case END_DOCUMENT:
				return ObservableNavajoParser.this.feeder.endDocument();
			case START_ELEMENT:
				return ObservableNavajoParser.this.feeder.startElement(xmlEvent.getText(),xmlEvent.getAttributes());
			case END_ELEMENT:
				return ObservableNavajoParser.this.feeder.endElement(xmlEvent.getText());
			case TEXT:
				return ObservableNavajoParser.this.feeder.text(xmlEvent.getText());
		}
		return 0;
	}

}
