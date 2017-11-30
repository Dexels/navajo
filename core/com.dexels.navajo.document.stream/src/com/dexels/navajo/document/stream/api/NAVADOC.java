package com.dexels.navajo.document.stream.api;

import java.util.Stack;

import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable.Operator;
import rx.Subscriber;
import rx.functions.Func1;

public class NAVADOC {
	
	

	
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
							break;
						case ARRAY_ELEMENT_STARTED:
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
								outbound.onNext(Events.arrayElement(transformed,event.attributes()));
								return;
							}
							break;
							// TODO Support these?
						case ARRAY_STARTED:
							pathStack.push(event.path());
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
						String joined = String.join("/", pathStack);
						return path.equals(joined);
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
						String joined = String.join("/", pathStack);
						return path.equals(joined);
					}
				};
			}};
	}

	
//	public static Operator<Object,NavajoStreamEvent> propertyValue(String property) {
//		return new Operator<Object,NavajoStreamEvent>(){
//
//			@Override
//			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super Object> outbound) {
//				return new Subscriber<NavajoStreamEvent>(){
//
//					@Override
//					public void onCompleted() {
//						outbound.onCompleted();
//					}
//
//					@Override
//					public void onError(Throwable e) {
//						outbound.onError(e);
//					}
//
//					@Override
//					public void onNext(NavajoStreamEvent event) {
//						if(event.type()==NavajoEventTypes.ARRAY_ELEMENT && event.type()==NavajoEventTypes.MESSAGE) {
//							Msg b = (Msg) event.body();
//							outbound.onNext(b.value(property));
//						}
//						
//					}};
//			}};
//		
//	}
//	
	
	}
