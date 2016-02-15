package com.dexels.navajo.document.stream.xml;

import java.nio.ByteBuffer;

import com.dexels.navajo.document.stream.impl.SaxXmlFeeder;
import com.dexels.navajo.document.stream.xml.XMLEvent;

import rx.Subscriber;
import rx.Observable.Operator;

public class XML {
	public static Operator<XMLEvent,ByteBuffer> parse() {
		return new Operator<XMLEvent,ByteBuffer>(){
			private final SaxXmlFeeder feeder = new SaxXmlFeeder();

			@Override
			public Subscriber<? super ByteBuffer> call(Subscriber<? super XMLEvent> in) {
				return new Subscriber<ByteBuffer>() {

					@Override
					public void onCompleted() {
						if(!in.isUnsubscribed()) {
							feeder.endOfInput();
						}
					}

					@Override
					public void onError(Throwable e) {
						if(!in.isUnsubscribed()) {
							in.onError(e);
						}
					}

					@Override
					public void onNext(ByteBuffer bytes) {
						if(in.isUnsubscribed()) {
							return;
						}
						Iterable<XMLEvent> event = feeder.parse(bytes);
						bytes.flip();
						bytes.clear();
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
