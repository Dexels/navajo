package com.dexels.navajo.document.stream.xml;

import static com.dexels.navajo.document.stream.events.EventFactory.message;
import static com.dexels.navajo.document.stream.events.EventFactory.messageDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.NavajoStreamHandler;
import com.dexels.navajo.document.stream.events.EventFactory;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.impl.StreamSaxHandler;

import rx.Observable;
import rx.Subscriber;

public class ObservableNavajoParser  {
	
	private Subscriber<? super NavajoStreamEvent> currentSubscriber;
	private StreamSaxHandler feeder;
	private final Map<String,AtomicInteger> arrayCount = new HashMap<>();

	public ObservableNavajoParser(Map<String,Object> attributes) {
		this.feeder = new StreamSaxHandler(new NavajoStreamHandler(){

			@Override
			public void messageDone(Message msg, String path) {
				if(Message.MSG_TYPE_DEFINITION.equals(msg.getType())) {
					currentSubscriber.onNext(messageDefinition(msg, path).withAttributes(attributes));
				} else {
					currentSubscriber.onNext(message(msg, path).withAttributes(attributes));
				}
			}

			@Override
			public void arrayStarted(Message msg, String path) {
				arrayCount.put(path,new AtomicInteger(0));
				currentSubscriber.onNext(EventFactory.arrayStarted(msg, path).withAttributes(attributes));
			}

			@Override
			public void arrayElementStarted(Message msg, String path) {
				currentSubscriber.onNext(EventFactory.arrayElementStarted(msg, path).withAttributes(attributes));
				
			}
			@Override
			public void arrayElement(Message msg, String path) {
				currentSubscriber.onNext(EventFactory.arrayElement(msg, path).withAttributes(attributes));
			}

			@Override
			public void arrayDone(Message msg, String path) {
				currentSubscriber.onNext(EventFactory.arrayDone(msg, path).withAttributes(attributes));
			}

			@Override
			public void header(Header h) {
				currentSubscriber.onNext(EventFactory.header(h).withAttributes(attributes));
			}

			@Override
			public void navajoDone() {
				currentSubscriber.onNext(EventFactory.navajoDone().withAttributes(attributes));
				
			}

			@Override
			public void messageStarted(Message element, String path) {
				if(Message.MSG_TYPE_DEFINITION.equals(element.getType())) {
					currentSubscriber.onNext(EventFactory.messageDefinitionStarted(element, path).withAttributes(attributes));
				} else if (Message.MSG_TYPE_ARRAY_ELEMENT.equals(element.getType())) {
					currentSubscriber.onNext(EventFactory.arrayElementStarted(element, path).withAttributes(attributes));
				} else {
					currentSubscriber.onNext(EventFactory.messageStarted(element, path).withAttributes(attributes));
				}
				
			}

			@Override
			public void navajoStart() {
				currentSubscriber.onNext(EventFactory.navajoStarted().withAttributes(attributes));
				
			}
});
		
//		this.feeder = new SaxXmlFeeder(handler);
	}
	public Observable<NavajoStreamEvent> feed(final XMLEvent xmlEvent) {
		return Observable.<NavajoStreamEvent>create(subscriber-> {
				ObservableNavajoParser.this.currentSubscriber = subscriber;
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
				subscriber.onCompleted();
		});
	}

}
