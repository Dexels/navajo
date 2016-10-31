package com.dexels.navajo.document.stream.api;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.XMLEvent;
import com.dexels.navajo.document.types.Binary;

import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.functions.Func1;

public class NAVADOC {
	
	
	private final static Logger logger = LoggerFactory.getLogger(NAVADOC.class);

	
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
	
	public static Operator<NavajoStreamEvent,NavajoStreamEvent> setPropertyValue(final String messagePath, String property, Object value) {
		return messageWithPath(messagePath, msg->msg.withValue(property, value),false);
	}
	
	public static Operator<NavajoStreamEvent,NavajoStreamEvent> messageWithPath(final String messagePath) {
		return messageWithPath(messagePath,m->m,true);
	}

	public static Operator<NavajoStreamEvent,NavajoStreamEvent> messageWithPath(final String messagePath, final Func1<Msg,Msg> operation, boolean filter) {
		return new Operator<NavajoStreamEvent,NavajoStreamEvent>(){
			@Override
			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super NavajoStreamEvent> outbound) {
				return new Subscriber<NavajoStreamEvent>() {

					private final Stack<String> pathStack = new Stack<>();
					
					@Override
					public void onCompleted() {
						outbound.onCompleted();
					}

					@Override
					public void onError(Throwable e) {
						outbound.onError(e);
						
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						switch(event.type()) {
						case MESSAGE_STARTED:
							pathStack.push(event.path());
//							System.err.println("Stack: "+pathStack);
							break;
						case ARRAY_ELEMENT_STARTED:
//							pathStack.push(event.path());
//							System.err.println("Stack: "+pathStack);
							break;
						case MESSAGE:
							if(matches(messagePath,pathStack)) {
								Msg transformed = operation.call((Msg) event.body());
								outbound.onNext(Events.message(transformed, event.path(), event.attributes()));
								return;
							}
							pathStack.pop();
							break;
						case ARRAY_ELEMENT:
							if(matches(messagePath,pathStack)) {
								Msg transformed = operation.call((Msg) event.body());
//								System.err.println("Transformed: "+transformed);
								outbound.onNext(Events.arrayElement(transformed,event.attributes()));
								return;
							}
//							pathStack.pop();
							break;
							// TODO Support these?
						case ARRAY_STARTED:
							pathStack.push(event.path());
//							System.err.println("Stack: "+pathStack);
							break;
						case ARRAY_DONE:
							pathStack.pop();
							break;
						default:
							break;
						}
						if(!filter) {
							outbound.onNext(event);
						}
						
					}

					private boolean matches(String path, Stack<String> pathStack) {
//						System.err.println("Compare path: "+path+" with stack: "+pathStack);
						String joined = String.join("/", pathStack);
						return path.equals(joined);
					}
				};
			}};
	}
	
	public static Operator<Binary,String> gatherBinary() {
		return new Operator<Binary,String>(){

			@Override
			public Subscriber<? super String> call(Subscriber<? super Binary> out) {
				return new Subscriber<String>() {
					Binary result = null;

					@Override
					public void onCompleted() {
						try {
							if(result!=null) {
								result.finishPushContent();
								out.onNext(result);
							}
							out.onCompleted();
						} catch (IOException e) {
							out.onError(e);
						}
						
					}

					@Override
					public void onError(Throwable e) {
						out.onError(e);
					}

					@Override
					public void onNext(String s) {
						if(result==null) {
							result = createBinary();
						}
						try {
							result.pushContent(s);
						} catch (IOException e) {
							out.onError(e);
						}
					}
					
					private Binary createBinary() {
						Binary result = new Binary();
						try {
							result.startPushRead();
						} catch (IOException e1) {
							logger.error("Error: ", e1);
						}
						return result;
					}
				};
			}

};
	}
	
	public static Operator<String,NavajoStreamEvent> observeBinary(final String path) {
		return new Operator<String,NavajoStreamEvent>(){
			@Override
			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super String> outbound) {
				return new Subscriber<NavajoStreamEvent>() {

					private final Stack<String> pathStack = new Stack<>();
					
					@Override
					public void onCompleted() {
						outbound.onCompleted();
					}

					@Override
					public void onError(Throwable e) {
						outbound.onError(e);
						
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						switch(event.type()) {
						case MESSAGE_STARTED:
							pathStack.push(event.path());
							break;
						case ARRAY_ELEMENT_STARTED:
							break;
						case MESSAGE:
							pathStack.pop();
							break;
						case ARRAY_ELEMENT:
							break;
							// TODO Support these?
						case ARRAY_STARTED:
							pathStack.push(event.path());
							break;
						case ARRAY_DONE:
							pathStack.pop();
							break;
							
						case BINARY_STARTED:
							pathStack.push(event.path());
							break;
						case BINARY_DONE:
							if(matches(path,pathStack)) {
								outbound.onCompleted();
							}
							pathStack.pop();
							break;
						case BINARY_CONTENT:
							if(matches(path,pathStack)) {
								outbound.onNext((String) event.body());
							}
						default:
							break;
						}
					}

					private boolean matches(String path, Stack<String> pathStack) {
//						System.err.println("Compare path: "+path+" with stack: "+pathStack);
						String joined = String.join("/", pathStack);
						return path.equals(joined);
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
//	
//	public static Operator<Object,NavajoStreamEvent> propertyValue(final String messagePath, String property) {
//		return messageWithPath(messagePath, msg->msg.value(property));
//	}
	public static Operator<Object,NavajoStreamEvent> propertyValue(String property) {
		return new Operator<Object,NavajoStreamEvent>(){

			@Override
			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super Object> outbound) {
				return new Subscriber<NavajoStreamEvent>(){

					@Override
					public void onCompleted() {
						outbound.onCompleted();
					}

					@Override
					public void onError(Throwable e) {
						outbound.onError(e);
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						if(event.type()==NavajoEventTypes.ARRAY_ELEMENT && event.type()==NavajoEventTypes.MESSAGE) {
							Msg b = (Msg) event.body();
							outbound.onNext(b.value(property));
						}
						
					}};
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
