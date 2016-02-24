package com.dexels.navajo.document.stream.xml;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.stream.NavajoStreamHandler;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.Events;
//import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.impl.StreamSaxHandler;

//import static com.dexels.navajo.document.stream.events.Events.*;
import rx.Observable;
import rx.Subscriber;

public class ObservableNavajoParser  {
	
	private Subscriber<? super NavajoStreamEvent> currentSubscriber;
	private StreamSaxHandler feeder;
	
	public ObservableNavajoParser(final Map<String,Object> attributes, final Subscriber<? super NavajoStreamEvent> subscriber) {
		this.feeder = new StreamSaxHandler(new NavajoStreamHandler(){

			@Override
			public void messageDone(Map<String, String> attributes, List<Prop> properties) {
				subscriber.onNext(Events.message(properties, attributes.get("name"),attributes));
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
				subscriber.onNext(Events.messageDefinition(properties, attributes.get("name")));				
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
				subscriber.onNext(Events.arrayElement(properties, Collections.emptyMap()));				
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
		});
	}
	public ObservableNavajoParser(Map<String,Object> attributes) {
		this.feeder = new StreamSaxHandler(new NavajoStreamHandler(){

			@Override
			public void messageDone(Map<String, String> attributes, List<Prop> properties) {
					currentSubscriber.onNext(Events.message(properties, attributes.get("name"), attributes));
			}

			@Override
			public void messageStarted(Map<String, String> attributes) {
				currentSubscriber.onNext(Events.messageStarted(attributes.get("name"),attributes));
			}

			@Override
			public void messageDefinitionStarted(Map<String, String> attributes) {
				currentSubscriber.onNext(Events.messageDefinitionStarted(attributes.get("name")));
				
			}

			@Override
			public void messageDefinition(Map<String, String> attributes, List<Prop> properties) {
				currentSubscriber.onNext(Events.messageDefinition(properties, attributes.get("name")));				
			}

			@Override
			public void arrayStarted(Map<String, String> attributes) {
				currentSubscriber.onNext(Events.arrayStarted(attributes.get("name"),attributes));
			}

			@Override
			public void arrayElementStarted() {
				currentSubscriber.onNext(Events.arrayElementStarted( Collections.emptyMap()));
			}

			@Override
			public void arrayElement(List<Prop> properties) {
				currentSubscriber.onNext(Events.arrayElement(properties, Collections.emptyMap()));				
			}

			@Override
			public void arrayDone(String name) {
				currentSubscriber.onNext(Events.arrayDone(name));				
			}

			@Override
			public void navajoStart(NavajoHead head) {
				currentSubscriber.onNext(Events.started(head));
			}

			@Override
			public void navajoDone() {
				currentSubscriber.onNext(Events.done());				
			}
		});
	}

	public Observable<NavajoStreamEvent> feed(final XMLEvent xmlEvent) {
		return Observable.<NavajoStreamEvent>create(subscriber-> {
				ObservableNavajoParser.this.currentSubscriber = subscriber;
				parseXmlEvent(xmlEvent, subscriber);
				subscriber.onCompleted();
		});
	}

	public void parseXmlEvent(final XMLEvent xmlEvent, Subscriber<? super NavajoStreamEvent> subscriber) {
		switch(xmlEvent.getType()) {
			case START_DOCUMENT:
				ObservableNavajoParser.this.feeder.startDocument();
				break;
			case END_DOCUMENT:
				ObservableNavajoParser.this.feeder.endDocument();
				subscriber.onCompleted();;
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
	}

}
