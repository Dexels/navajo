package com.dexels.navajo.document.stream.impl;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.NavajoStreamHandler;
import com.dexels.navajo.document.stream.events.ArrayDone;
import com.dexels.navajo.document.stream.events.ArrayElement;
import com.dexels.navajo.document.stream.events.ArrayStart;
import com.dexels.navajo.document.stream.events.HeaderEvent;
import com.dexels.navajo.document.stream.events.MessageEvent;
import com.dexels.navajo.document.stream.events.NavajoDone;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XMLEvent;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class ObservableNavajoParser  {
	
	private Subscriber<? super NavajoStreamEvent> currentSubscriber;
	private StreamSaxHandler feeder;

	public ObservableNavajoParser() {

		this.feeder = new StreamSaxHandler(new NavajoStreamHandler(){

			@Override
			public void messageDone(Message msg, String path) {
				currentSubscriber.onNext(new MessageEvent(msg, path));
			}

			@Override
			public void arrayStarted(Message msg, String path) {
				currentSubscriber.onNext(new ArrayStart(msg, path));
			}

			@Override
			public void arrayElement(Message msg, String path) {
				currentSubscriber.onNext(new ArrayElement(msg, path));
			}

			@Override
			public void arrayDone(String path) {
				currentSubscriber.onNext(new ArrayDone(path));
			}

			@Override
			public void header(Header h) {
				currentSubscriber.onNext(new HeaderEvent(h));
			}

			@Override
			public void navajoDone() {
				currentSubscriber.onNext(new NavajoDone());
//				currentSubscriber.onCompleted();
				
			}});
		
//		this.feeder = new SaxXmlFeeder(handler);
	}
	public Observable<NavajoStreamEvent> feed(final XMLEvent xmlEvent) {
		Observable<NavajoStreamEvent> currentObservable = Observable.<NavajoStreamEvent>create(new OnSubscribe<NavajoStreamEvent>() {
			@Override
			public void call(Subscriber<? super NavajoStreamEvent> subscriber) {
				ObservableNavajoParser.this.currentSubscriber = subscriber;
				switch(xmlEvent.getType()) {
					case START_DOCUMENT:
						ObservableNavajoParser.this.feeder.startDocument();
						break;
					case END_DOCUMENT:
						ObservableNavajoParser.this.feeder.endDocument();
//						ObservableNavajoParser.this.currentSubscriber.onCompleted();;
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
		});
	return currentObservable;
	}

}
