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

//	public static FlowableOperator<XMLEvent, byte[]> parseRxFlowable2() {
//		return new FlowableOperator<XMLEvent, byte[]>() {
//			private final SaxXmlFeeder feeder = new SaxXmlFeeder();
//			private final AtomicLong produced = new AtomicLong();
//			private final AtomicLong consumed = new AtomicLong();
//			@Override
//			public Subscriber<? super byte[]> apply(
//					Subscriber<? super XMLEvent> in) throws Exception {
//				return new Subscriber<byte[]>() {
//
//					private Subscription subscription;
//					
//					@Override
//					public void onComplete() {
//						feeder.endOfInput();
//						in.onComplete();
//					}
//
//					@Override
//					public void onError(Throwable e) {
//						in.onError(e);
//						
//					}
//
//					@Override
//					public void onNext(byte[] bytes) {
////						if(in.isUnsubscribed()) {
////							return;
////						}
//						parse(in, bytes);						
//					}
//
//					@Override
//					public void onSubscribe(Subscription s) {
//						this.subscription = s;
//						s.request(3);
//					}
//					
//					private void parse(Subscriber<? super XMLEvent> in, byte[] bytes) {
//						consumed.incrementAndGet();
//						Iterable<XMLEvent> event = feeder.parse(bytes);
////						this.subscription.request(1);
//						if(feeder.getException()!=null) {
//							in.onError(feeder.getException());
//						} else {
//							for (XMLEvent xmlEvent : event) {
//								in.onNext(xmlEvent);
//								long l = produced.incrementAndGet();
//								System.err.println("produced: "+l);
//							}
//						}
//					}
//				};
//			}
//		};
//	}
//	

}
