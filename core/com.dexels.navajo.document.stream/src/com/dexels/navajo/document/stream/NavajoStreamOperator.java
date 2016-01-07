package com.dexels.navajo.document.stream;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable.Operator;
import rx.Subscriber;

public class NavajoStreamOperator implements Operator<NavajoStreamEvent,byte[]> {

	@Override
	public Subscriber<? super byte[]> call(Subscriber<? super NavajoStreamEvent> subscriber) {
		return new Subscriber<byte[]>() {

			@Override
			public void onCompleted() {
				if(!subscriber.isUnsubscribed()) {
					subscriber.onCompleted();
				}				
			}

			@Override
			public void onError(Throwable e) {
				if(!subscriber.isUnsubscribed()) {
					subscriber.onError(e);
				}	
			}

			@Override
			public void onNext(byte[] bb) {
				if(!subscriber.isUnsubscribed()) {
//					subscriber.onNext(bb);
				}					
				
			}
		};	}



}
