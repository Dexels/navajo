package com.dexels.navajo.document.stream.example;

import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;

public class SimpleInit implements Script {


	@Override
	public Observable<NavajoStreamEvent> call(Observable<NavajoStreamEvent> observable) {
		return null;
	}

}
