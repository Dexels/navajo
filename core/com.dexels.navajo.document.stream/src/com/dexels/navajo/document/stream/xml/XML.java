package com.dexels.navajo.document.stream.xml;

import com.dexels.navajo.document.stream.impl.SaxXmlFeeder;

import rx.Observable.Operator;
import rx.Subscriber;

public class XML {
	public static Operator<XMLEvent,byte[]> parse() {
		return new Operator<XMLEvent,byte[]>(){
			private final SaxXmlFeeder feeder = new SaxXmlFeeder();

			@Override
			public Subscriber<? super byte[]> call(Subscriber<? super XMLEvent> in) {
				return new Subscriber<byte[]>() {

					@Override
					public void onCompleted() {
						feeder.endOfInput();
//						parse(in, ByteBuffer.wrap(new byte[]{}));
						in.onCompleted();

					}

					@Override
					public void onError(Throwable e) {
						if(!in.isUnsubscribed()) {
							in.onError(e);
						}
					}

					@Override
					public void onNext(byte[] bytes) {
						if(in.isUnsubscribed()) {
							return;
						}
						parse(in, bytes);
					}

					private void parse(Subscriber<? super XMLEvent> in, byte[] bytes) {
						Iterable<XMLEvent> event = feeder.parse(bytes);
						if(feeder.getException()!=null) {
							in.onError(feeder.getException());
						} else {
							for (XMLEvent xmlEvent : event) {
								in.onNext(xmlEvent);
							}
						}
					}
				};
			}};
		
	}

}
