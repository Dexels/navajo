package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public interface SimpleScript {
	public Flowable<NavajoStreamEvent> call(Navajo input);
}
