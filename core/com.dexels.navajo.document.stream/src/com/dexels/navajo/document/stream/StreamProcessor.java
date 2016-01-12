package com.dexels.navajo.document.stream;

import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;
import com.dexels.navajo.document.stream.xml.XMLEvent;

import rx.subjects.Subject;

public class StreamProcessor extends Subject<byte[], XMLEvent> {
	
	private final ObservableXmlFeeder oxf = new ObservableXmlFeeder();

	protected StreamProcessor(rx.Observable.OnSubscribe<XMLEvent> onSubscribe) {
		super(onSubscribe);
	}

	@Override
	public void onCompleted() {
	}

	@Override
	public void onError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNext(byte[] b) {
		oxf.feed(b);
		
	}

	@Override
	public boolean hasObservers() {
		return true;
	}
}
