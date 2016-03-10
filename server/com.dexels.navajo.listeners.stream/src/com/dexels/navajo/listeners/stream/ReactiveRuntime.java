package com.dexels.navajo.listeners.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.ReactiveScript;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable.Operator;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Action2;

public class ReactiveRuntime implements Operator<NavajoStreamEvent,NavajoStreamEvent>, ReactiveScript {

	private final Stack<String> messageNames = new Stack<>();
	private final Map<String,AtomicInteger> arrayCounts = new HashMap<>();
	
	private final Map<String, Action2<Msg,Subscriber<? super NavajoStreamEvent>>> callbacks = new HashMap<>();
	private final Map<String, Action2<Msg,Subscriber<? super NavajoStreamEvent>>> elementCallbacks = new HashMap<>();
	private final List<Action1<Subscriber<? super NavajoStreamEvent>>> onComplete = new ArrayList<>();
	private final List<Action1<Subscriber<? super NavajoStreamEvent>>> onStart = new ArrayList<>();
	private Map<String, Action1<Subscriber<? super NavajoStreamEvent>>> beforeCallbacks = new HashMap<>();
	private Map<String, Action1<Subscriber<? super NavajoStreamEvent>>> afterCallbacks = new HashMap<>();

	@Override
	public void onMessage(String path, Action2<Msg,Subscriber<? super NavajoStreamEvent>> callback) {
		callbacks.put(path, callback);
	}

	@Override
	public void onElement(String path, Action2<Msg,Subscriber<? super NavajoStreamEvent>> callback) {
		elementCallbacks.put(path, callback);
	}
	
	@Override
	public void onElement(String path, Action1<Subscriber<? super NavajoStreamEvent>> before,
			Action2<Msg, Subscriber<? super NavajoStreamEvent>> callback,
			Action1<Subscriber<? super NavajoStreamEvent>> after) {
		elementCallbacks.put(path, callback);
		beforeCallbacks.put(path, before);
		afterCallbacks.put(path, after);
		
	}

	@Override
	public void onComplete(Action1<Subscriber<? super NavajoStreamEvent>> callback) {
		onComplete.add(callback);
	}

	@Override
	public void onStart(Action1<Subscriber<? super NavajoStreamEvent>> callback) {
		onStart.add(callback);
	}

	private void callOnComplete(Subscriber<? super NavajoStreamEvent> output) {
		for (Action1<Subscriber<? super NavajoStreamEvent>> action : onComplete) {
			action.call(output);
		}
	}

	private void callOnStart(Subscriber<? super NavajoStreamEvent> output) {
		for (Action1<Subscriber<? super NavajoStreamEvent>> action : onStart) {
			action.call(output);
		}
	}
	private void callMessageCallbacks(String path, Msg msg, Subscriber<? super NavajoStreamEvent> output) {
		Action2<Msg,Subscriber<? super NavajoStreamEvent>> callback = callbacks.get(path);
		if(callback!=null) {
			callback.call(msg, output);
		}
			
	}

	private void callBeforeCallbacks(String path,Subscriber<? super NavajoStreamEvent> output) {
		Action1<Subscriber<? super NavajoStreamEvent>> callback = beforeCallbacks.get(path);
		if(callback!=null) {
			callback.call(output);
		}
			
	}

	private void callAfterCallbacks(String path,Subscriber<? super NavajoStreamEvent> output) {
		Action1<Subscriber<? super NavajoStreamEvent>> callback = afterCallbacks.get(path);
		if(callback!=null) {
			callback.call(output);
		}
			
	}	
	
	private void callElementCallbacks(String path, Msg msg, Subscriber<? super NavajoStreamEvent> output) {
		Action2<Msg,Subscriber<? super NavajoStreamEvent>> callback = elementCallbacks.get(path);
		if(callback!=null) {
			callback.call(msg, output);
		}
			
	}

	private String currentPath() {
		String eventPath = String.join("/", messageNames.toArray(new String[]{}));
		return eventPath;
	}

	@SuppressWarnings("unchecked")
	private void processEvent(NavajoStreamEvent event,Subscriber<? super NavajoStreamEvent> output) {
		switch (event.type()) {
			case MESSAGE_STARTED:
				messageNames.push(event.path());
				break;
			case MESSAGE:
				callMessageCallbacks(currentPath(),Msg.createElement((List<Prop>) event.body()), output);
				messageNames.pop();
				break;
			case ARRAY_STARTED:
				messageNames.push(event.path());
				callBeforeCallbacks(currentPath(), output);
				arrayCounts.put(currentPath(), new AtomicInteger(0));
				break;
			case ARRAY_DONE:
				callAfterCallbacks(currentPath(), output);
				messageNames.pop();
				break;
			case ARRAY_ELEMENT_STARTED:
				int count = arrayCounts.get(currentPath()).get();
				messageNames.push("@"+count);
				break;
			case ARRAY_ELEMENT:
				messageNames.pop();
				callElementCallbacks(currentPath(),Msg.createElement((List<Prop>) event.body()), output);
				AtomicInteger atocount = arrayCounts.get(currentPath());
				atocount.incrementAndGet();
				break;
			case MESSAGE_DEFINITION_STARTED:
				messageNames.push("@definition");
				break;
			case MESSAGE_DEFINITION:
				messageNames.pop();
				break;
			case NAVAJO_DONE:
				callOnComplete(output);
				break;
			case NAVAJO_STARTED:
				callOnStart(output);
				break;
			default:
				break;
		}
	}

	@Override
	public Subscriber<? super NavajoStreamEvent> call(final Subscriber<? super NavajoStreamEvent> outSubscription) {
		return new Subscriber<NavajoStreamEvent>(){

			@Override
			public void onCompleted() {
//				outSubscription.onCompleted();
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
				outSubscription.onError(e);
			}

			@Override
			public void onNext(NavajoStreamEvent event) {
				processEvent(event,outSubscription);
			}};

	
	
	}





}
