package com.dexels.navajo.listeners.stream.core;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observer;
import rx.Subscriber;

public class OutputSubscriber implements Observer<NavajoStreamEvent> {

	private final Subscriber<? super NavajoStreamEvent> core;

	public OutputSubscriber(Subscriber<? super NavajoStreamEvent> f) {
		this.core = f;
	}
	

	@Override
	public void onCompleted() {

		core.onCompleted();
	}

	@Override
	public void onError(Throwable ex) {
		core.onError(ex);
	}

	@Override
	public void onNext(NavajoStreamEvent n) {
		core.onNext(n);
	}

	public void emitMessage(Message m) {
		
	}
}
