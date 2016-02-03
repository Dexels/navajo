package com.dexels.navajo.document.stream.xml;

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
//	private final Map<String,AtomicInteger> arrayCount = new HashMap<>();

	public ObservableNavajoParser(Map<String,Object> attributes) {
		this.feeder = new StreamSaxHandler(new NavajoStreamHandler(){

			@Override
			public void messageDone(Map<String, String> attributes, List<Prop> properties) {
					currentSubscriber.onNext(Events.message(properties, attributes.get("name")));
				
			}

			@Override
			public void messageStarted(Map<String, String> attributes) {
				currentSubscriber.onNext(Events.messageStarted(attributes.get("name")));
				
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
				currentSubscriber.onNext(Events.arrayStarted(attributes.get("name")));
			}

			@Override
			public void arrayElementStarted(Map<String, String> attributes) {
				currentSubscriber.onNext(Events.arrayElementStarted(attributes.get("name")));
			}

			@Override
			public void arrayElement(Map<String, String> attributes, List<Prop> properties) {
				currentSubscriber.onNext(Events.arrayElement(properties, attributes.get("name")));				
				
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
	
	
//	private static NavajoStreamEvent createHeader(Header h) {
//		return Events.started(new NavajoHead(h.getRPCName(), h.getRPCUser(), h.getRPCPassword(), h.getHeaderAttributes()));
//	}
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
