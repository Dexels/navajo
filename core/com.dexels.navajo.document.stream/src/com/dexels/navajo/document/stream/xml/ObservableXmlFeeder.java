package com.dexels.navajo.document.stream.xml;

import java.util.Map;

import com.dexels.navajo.document.stream.impl.SaxXmlFeeder;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

import rx.Observable;
import rx.Subscriber;

public class ObservableXmlFeeder {

	private final SaxXmlFeeder feeder;
	private Subscriber<? super XMLEvent> currentSubscriber;

	public ObservableXmlFeeder() {
		
		XmlInputHandler handler = new XmlInputHandler() {

			@Override
			public void text(String r) {
				if(!r.trim().equals("")) {
					currentSubscriber.onNext(new XMLEvent(XmlEventTypes.TEXT, r, null));
				}
			}

			@Override
			public void startElement(String tag, Map<String, String> h) {
				currentSubscriber.onNext(new XMLEvent(XmlEventTypes.START_ELEMENT, tag, h));
			}

			@Override
			public void startDocument() {
				currentSubscriber.onNext(new XMLEvent(XmlEventTypes.START_DOCUMENT, null, null));
			}

			@Override
			public void endElement(String tag) {
				currentSubscriber.onNext(new XMLEvent(XmlEventTypes.END_ELEMENT, tag, null));
			}

			@Override
			public void endDocument() {
				currentSubscriber.onNext(new XMLEvent(XmlEventTypes.END_DOCUMENT, null, null));
				currentSubscriber.onCompleted();
			}
		};

		this.feeder = new SaxXmlFeeder(handler);
	}

	public Observable<XMLEvent> feed(byte[] bytes) {
		return Observable.<XMLEvent> create(subscriber -> {
			ObservableXmlFeeder.this.currentSubscriber = subscriber;
			feeder.feed(bytes);
			if(bytes.length==0) {
				feeder.endOfInput();
			}
			subscriber.onCompleted();
		});
	}

}
