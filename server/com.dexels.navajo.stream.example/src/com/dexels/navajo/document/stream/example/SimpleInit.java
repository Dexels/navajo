package com.dexels.navajo.document.stream.example;

import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;
import io.reactivex.Observable;


public class SimpleInit implements Script {


	@Override
	public Flowable<NavajoStreamEvent> call(Flowable<NavajoStreamEvent> observable) {
		return null;
	}

}
