package com.dexels.navajo.document.stream.xml;

import org.reactivestreams.Subscriber;

import io.reactivex.FlowableOperator;

public class XML2 {
	

	public static FlowableOperator<XMLEvent, byte[]> parse() {
		return new FlowableOperator<XMLEvent, byte[]>() {

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super XMLEvent> xe) throws Exception {
				return new ParseSubscriber(xe);
			}
			
		};
	}
}
