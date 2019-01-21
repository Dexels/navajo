package com.dexels.navajo.document.stream.api;

import java.util.Stack;
import java.util.function.Function;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.FlowableOperator;


public class NAVADOC {
	
	

	
	public static FlowableOperator<NavajoStreamEvent,NavajoStreamEvent> setPropertyValue(final String messagePath, String property, String value) {
		return messageWithPath(messagePath, msg->msg.withValue(property, value),false);
	}
	
	public static FlowableOperator<NavajoStreamEvent,NavajoStreamEvent> messageWithPath(final String messagePath) {
		return messageWithPath(messagePath,m->m,true);
	}

	public static FlowableOperator<NavajoStreamEvent,NavajoStreamEvent> messageWithPath(final String messagePath, final Function<Msg,Msg> operation, boolean filter) {

		return new FlowableOperator<NavajoStreamEvent,NavajoStreamEvent>(){
			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super NavajoStreamEvent> outbound) {
				return new Subscriber<NavajoStreamEvent>() {

					private final Stack<String> pathStack = new Stack<>();
					private Subscription subscription;
					
					@Override
					public void onComplete() {
						outbound.onComplete();
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
								Msg transformed;
								try {
									transformed = operation.apply((Msg) event.body());
									outbound.onNext(Events.message(transformed, event.path(), event.attributes()));
								} catch (Exception e) {
									outbound.onError(e);
								}
								return;
							}
							pathStack.pop();
							break;
						case ARRAY_ELEMENT:
							if(matches(messagePath,pathStack)) {
								Msg transformed;
								try {
									transformed = operation.apply((Msg) event.body());
									outbound.onNext(Events.arrayElement(transformed,event.attributes()));
								} catch (Exception e) {
									outbound.onError(e);
								}
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

					@Override
					public void onSubscribe(Subscription s) {
						this.subscription = s;
					}
				};
			}
		};
	}

	public static FlowableOperator<String,NavajoStreamEvent> observeBinary(final String path) {
		return new FlowableOperator<String,NavajoStreamEvent>(){
			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super String> outbound) {
				return new Subscriber<NavajoStreamEvent>() {

					private final Stack<String> pathStack = new Stack<>();
					
					@Override
					public void onComplete() {
						outbound.onComplete();
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
								outbound.onComplete();
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

					@Override
					public void onSubscribe(Subscription arg0) {
					}
				};
			}

		};
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
