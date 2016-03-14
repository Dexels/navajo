package com.dexels.navajo.document.stream.xml;

import com.dexels.navajo.document.stream.impl.SaxXmlFeeder;

import rx.Observable;

public class ObservableXmlFeeder {

	private final SaxXmlFeeder feeder = new SaxXmlFeeder();

	@Deprecated
	public Observable<XMLEvent> feed(byte[] bytes) {
//		System.err.println("bytes: "+bytes.position()+" remaining: "+bytes.remaining());
//		if(bytes.limit()==bytes.position()) {
//			System.err.println("Ending input");
//			feeder.endOfInput();
//		}
//		System.err.println("Feeding bytes: "+bytes.remaining());
		Iterable<XMLEvent> event = feeder.parse(bytes);
		if(feeder.getException()!=null) {
			return Observable.<XMLEvent>error(feeder.getException());
		}
		return Observable.from(event);
	}	
	
	public void endOfInput() {
		feeder.endOfInput();
	}
	
//	
//	@Deprecated
//	public Observable<XMLEvent> feed(byte[] bytes) {
//		return feed(ByteBuffer.wrap(bytes));
//	}
	
}
