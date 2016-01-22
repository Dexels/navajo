package com.dexels.navajo.document.stream.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.NavajoStreamHandler;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
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
					currentSubscriber.onNext(new NavajoStreamEvent(path,NavajoEventTypes.MESSAGE_DEFINITION,msg,attributes));
				} else {
					currentSubscriber.onNext(new NavajoStreamEvent(path,NavajoEventTypes.MESSAGE,msg,attributes));
				}
			}

			@Override
			public void arrayStarted(Message msg, String path) {
				arrayCount.put(path,new AtomicInteger(0));
				currentSubscriber.onNext(new NavajoStreamEvent(path,NavajoEventTypes.ARRAY_STARTED,msg,attributes));
			}

			@Override
			public void arrayElementStarted(Message msg, String path) {
				currentSubscriber.onNext(new NavajoStreamEvent(path,NavajoEventTypes.ARRAY_ELEMENT_STARTED, msg,attributes));
				
			}
			@Override
			public void arrayElement(Message msg, String path) {
				currentSubscriber.onNext(new NavajoStreamEvent(path,NavajoEventTypes.ARRAY_ELEMENT, msg,attributes));
			}

			@Override
			public void arrayDone(Message msg, String path) {
				currentSubscriber.onNext(new NavajoStreamEvent(path,NavajoEventTypes.ARRAY_DONE,msg, attributes));
			}

			@Override
			public void header(Header h) {
				currentSubscriber.onNext(new NavajoStreamEvent(null,NavajoEventTypes.HEADER,h,attributes));
			}

			@Override
			public void navajoDone() {
				currentSubscriber.onNext(new NavajoStreamEvent(null,NavajoEventTypes.NAVAJO_DONE,null, attributes));
				
			}

			@Override
			public void messageStarted(Message element, String path) {
				if(Message.MSG_TYPE_DEFINITION.equals(element.getType())) {
					currentSubscriber.onNext(new NavajoStreamEvent(path,NavajoEventTypes.MESSAGE_DEFINITION_STARTED,element, attributes));
				} else if (Message.MSG_TYPE_ARRAY_ELEMENT.equals(element.getType())) {
					currentSubscriber.onNext(new NavajoStreamEvent(path,NavajoEventTypes.ARRAY_ELEMENT_STARTED,element, attributes));
				} else {
					currentSubscriber.onNext(new NavajoStreamEvent(path,NavajoEventTypes.MESSAGE_STARTED,element, attributes));
				}
				
			}

			@Override
			public void navajoStart() {
				currentSubscriber.onNext(new NavajoStreamEvent(null,NavajoEventTypes.NAVAJO_STARTED,null, attributes));
				
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
