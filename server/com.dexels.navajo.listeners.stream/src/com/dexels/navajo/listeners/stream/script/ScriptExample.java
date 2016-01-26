package com.dexels.navajo.listeners.stream.script;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Subscriber;

public class ScriptExample extends Subscriber<NavajoStreamEvent> {

	private final Subscriber<NavajoStreamEvent> outputSubscriber;
	
	public void onActivate() {
	}
	
	public void process(Observable<NavajoStreamEvent> event,Message... required) {
		
	}
	public ScriptExample(Subscriber<NavajoStreamEvent> outputSubscriber) {
		this.outputSubscriber = outputSubscriber;
//		Observable<T>.create(s->{});
	}

	@Override
	public void onCompleted() {
		outputSubscriber.onCompleted();
	}

	@Override
	public void onError(Throwable ex) {
		outputSubscriber.onError(ex);
	}

	@Override
	public void onNext(NavajoStreamEvent e) {
		outputSubscriber.onNext(e);
	}

}
