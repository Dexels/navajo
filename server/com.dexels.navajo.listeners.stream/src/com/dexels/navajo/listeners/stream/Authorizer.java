package com.dexels.navajo.listeners.stream;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class Authorizer implements Observer<NavajoStreamEvent> {

	private final Observable<NavajoStreamEvent> input;
	private final Observer<NavajoStreamEvent> output;
	private final Subscription subscription;

	public Authorizer(Observable<NavajoStreamEvent> authStream,Observable<NavajoStreamEvent> input, Observer<NavajoStreamEvent> output) {
		this.input = input;
		this.output = output;
		this.subscription = authStream.subscribe(this);
	}
	
	@Override
	public void onCompleted() {

	}

	@Override
	public void onError(Throwable arg0) {

	}

	@Override
	public void onNext(NavajoStreamEvent headerEvent) {
		Header h = (Header)headerEvent.body();
		System.err.println("Script: "+h.getRPCName());
//		BaseScriptInstance bsi = new BaseScriptInstance(h.getRPCName(), input,output);
		subscription.unsubscribe();
		

	}

}
