package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;

public interface SimpleScript {
	public Observable<NavajoStreamEvent> call(Navajo input);
}
