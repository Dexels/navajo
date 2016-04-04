package com.dexels.navajo.document.stream.example;

import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;

import rx.Observable;

public class Echo implements Script {

//	Executor exe = Executors.newFixedThreadPool(10);

	@Override
	public Observable<NavajoStreamEvent> call(Observable<NavajoStreamEvent> input) {
		return input.filter(e->e.type()!=NavajoEventTypes.NAVAJO_DONE && e.type()!=NavajoEventTypes.NAVAJO_STARTED);
	}
}
