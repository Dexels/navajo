package com.dexels.navajo.document.stream.api;

import java.util.Map;
import java.util.Stack;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.XMLEvent;

import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;

public class NAVADOC {
	
	public static Operator<Navajo,NavajoStreamEvent> collect(final Map<String,Object> attributes) {
		return new Operator<Navajo,NavajoStreamEvent>(){
			private final NavajoStreamCollector collector = new NavajoStreamCollector();
			@Override
			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super Navajo> n) {
				return new Subscriber<NavajoStreamEvent>() {

					@Override
					public void onCompleted() {
						if(!n.isUnsubscribed()) {
	 						n.onCompleted();
						}
					}

					@Override
					public void onError(Throwable e) {
						if(!n.isUnsubscribed()) {
						n.onError(e);
						}
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						if(!n.isUnsubscribed()) {
						collector.feed(event,n);
						}
					}
				};
			}};
	}
	
	
	public static Operator<byte[],NavajoStreamEvent> serialize() {
		return new Operator<byte[],NavajoStreamEvent>(){
			private final NavajoStreamSerializer serializer = new NavajoStreamSerializer();
			@Override
			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super byte[]> n) {
				return new Subscriber<NavajoStreamEvent>() {

					@Override
					public void onCompleted() {
						if(!n.isUnsubscribed()) {
	 						n.onCompleted();
						}
					}

					@Override
					public void onError(Throwable e) {
						if(!n.isUnsubscribed()) {
						n.onError(e);
						}
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						if(!n.isUnsubscribed()) {
							Observable<byte[]> res = serializer.feed(event);
							res.subscribe(a->n.onNext(a));
						}
					}
				};
			}
		};
	}
	
	public static Operator<Observable<byte[]>,NavajoStreamEvent> serializeStream() {
		return new Operator<Observable<byte[]>,NavajoStreamEvent>(){
			private final NavajoStreamSerializer serializer = new NavajoStreamSerializer();
			@Override
			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super Observable<byte[]>> n) {
				return new Subscriber<NavajoStreamEvent>() {

					@Override
					public void onCompleted() {
						if(!n.isUnsubscribed()) {
	 						n.onCompleted();
						}
					}

					@Override
					public void onError(Throwable e) {
						if(!n.isUnsubscribed()) {
						n.onError(e);
						}
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						if(!n.isUnsubscribed()) {
							Observable<byte[]> res = serializer.feed(event);
							n.onNext(res);
						}
					}
				};
			}
		};
	}
	public static Operator<NavajoStreamEvent,NavajoStreamEvent> filterMessageIgnore() {
		return new Operator<NavajoStreamEvent,NavajoStreamEvent>(){
			private final Stack<Boolean> ignoreLevel = new Stack<Boolean>();
//			private final Stack<String> level = new Stack<String>();
			
			@Override
			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super NavajoStreamEvent> n) {

				
				return new Subscriber<NavajoStreamEvent>() {

					@Override
					public void onCompleted() {
						if(!n.isUnsubscribed()) {
	 						n.onCompleted();
						}
					}

					@Override
					public void onError(Throwable e) {
						if(!n.isUnsubscribed()) {
						n.onError(e);
						}
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						if(!n.isUnsubscribed()) {
							switch(event.type()) {
								case ARRAY_ELEMENT_STARTED:
								case ARRAY_STARTED:
								case MESSAGE_STARTED:
								case MESSAGE_DEFINITION_STARTED:
									String mode = (String) event.attribute("mode");
									boolean isIgnore = "ignore".equals(mode);
									ignoreLevel.push(isIgnore);
//									System.err.println("push: "+"ignore".equals(mode)+" == "+ignoreLevel);
//									level.push(event.path());
									if(!ignoreLevel.contains(true)) {
										n.onNext(event);
									}
									break;
									
								case ARRAY_DONE:
								case ARRAY_ELEMENT:
								case MESSAGE_DEFINITION:
								case MESSAGE:
									if(!ignoreLevel.contains(true)) {
										n.onNext(event);
									}
//									level.pop();
									
									ignoreLevel.pop();
									break;
									
								case NAVAJO_DONE:
								case NAVAJO_STARTED:
									n.onNext(event);
									break;
								default:
									throw new UnsupportedOperationException("Unknown event found in NAVADOC");
							}
						}
					}
				};
			}};
	}
	

	public static Operator<NavajoStreamEvent,Navajo> stream() {
		return new Operator<NavajoStreamEvent,Navajo>(){
			@Override
			public Subscriber<? super Navajo> call(Subscriber<? super NavajoStreamEvent> n) {
				return new Subscriber<Navajo>() {

					@Override
					public void onCompleted() {
						if(!n.isUnsubscribed()) {
	 						n.onCompleted();
						}
					}

					@Override
					public void onError(Throwable e) {
						if(!n.isUnsubscribed()) {
						n.onError(e);
						}
					}

					@Override
					public void onNext(Navajo event) {
						if(!n.isUnsubscribed()) {
							NavajoDomStreamer.feed(event).subscribe(ee->n.onNext(ee));
//						collector.feed(event,n);
						}
					}
				};
			}};
	}
	
	public static Operator<Observable<NavajoStreamEvent>,Navajo> streamObservable() {
		return new Operator<Observable<NavajoStreamEvent>,Navajo>(){
			@Override
			public Subscriber<? super Navajo> call(Subscriber<? super Observable<NavajoStreamEvent>> n) {
				return new Subscriber<Navajo>() {

					@Override
					public void onCompleted() {
						if(!n.isUnsubscribed()) {
	 						n.onCompleted();
						}
					}

					@Override
					public void onError(Throwable e) {
						if(!n.isUnsubscribed()) {
						n.onError(e);
						}
					}

					@Override
					public void onNext(Navajo event) {
						if(!n.isUnsubscribed()) {
							n.onNext(NavajoDomStreamer.feed(event));
//						collector.feed(event,n);
						}
					}
				};
			}};
	}
	
	
	public static Operator<NavajoStreamEvent,XMLEvent> parse(final Map<String,Object> attributes) {
		return new Operator<NavajoStreamEvent,XMLEvent>(){

			@Override
			public Subscriber<? super XMLEvent> call(Subscriber<? super NavajoStreamEvent> in) {
				ObservableNavajoParser parser = new ObservableNavajoParser(attributes,in);
				return new Subscriber<XMLEvent>(){

					@Override
					public void onCompleted() {
						if(!in.isUnsubscribed()) {
							in.onCompleted();
						}
					}

					@Override
					public void onError(Throwable e) {
						if(!in.isUnsubscribed()) {
							in.onError(e);
						}
						
					}

					@Override
					public void onNext(XMLEvent x) {
						if(!in.isUnsubscribed()) {
							parser.parseXmlEvent(x, in);
							
						}
						
					}};
			}};
	}
}
