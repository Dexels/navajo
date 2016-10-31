package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Observable.Transformer;

public interface Script extends Transformer<NavajoStreamEvent, NavajoStreamEvent> {
	public Observable<NavajoStreamEvent> call(Observable<NavajoStreamEvent> input);
}
