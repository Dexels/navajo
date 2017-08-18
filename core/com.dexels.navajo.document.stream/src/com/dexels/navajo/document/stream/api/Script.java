package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public interface Script {
	public Flowable<NavajoStreamEvent> call(Flowable<NavajoStreamEvent> input);
}
