package com.dexels.navajo.listeners.stream.impl;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable.Operator;
import rx.Subscriber;

public class ObservableAuthenticationHandler implements Operator<NavajoStreamEvent,NavajoStreamEvent> {
	

	private String authorizationObject = null;
	private String scriptName;

	private NavajoStreamEvent authorize(NavajoStreamEvent streamEvent) {
		if(streamEvent.type()==NavajoStreamEvent.NavajoEventTypes.HEADER) {
			Header header = (Header) streamEvent.body();
			this.authorizationObject  = header.getRPCUser()+"|"+header.getRPCPassword();
			this.scriptName = header.getRPCName();
		}
		return streamEvent.withAttribute("Auth",authorizationObject).withAttribute("Script", scriptName);
//		subscriber.onNext(streamEvent);	
		
	}

	@Override
	public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super NavajoStreamEvent> subscriber) {
		return new Subscriber<NavajoStreamEvent>(subscriber) {

			@Override
			public void onCompleted() {
				subscriber.onCompleted();
			}

			@Override
			public void onError(Throwable t) {
				subscriber.onError(t);
			}

			@Override
			public void onNext(NavajoStreamEvent streamEvent) {
				NavajoStreamEvent authorize = authorize(streamEvent);
				Object authAttribute = authorize.attribute("Auth");
				System.err.println("auth: "+authAttribute+" authorize: "+authorize);
				if("username|pw".equals( authAttribute)) {
					subscriber.onNext(authorize);
				} else {
					subscriber.onError(new Exception("Auth error!"));
				}
			}
		};
	}

	public String getScriptName() {
		return scriptName;
	}

}
