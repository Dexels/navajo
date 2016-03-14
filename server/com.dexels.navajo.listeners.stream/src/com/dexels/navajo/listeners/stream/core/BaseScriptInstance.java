package com.dexels.navajo.listeners.stream.core;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;

public abstract class BaseScriptInstance {

	public abstract Observable<NavajoStreamEvent> call(NavajoStreamEvent event);
	

}
