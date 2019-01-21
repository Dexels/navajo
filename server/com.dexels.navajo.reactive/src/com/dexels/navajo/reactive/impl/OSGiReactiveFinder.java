package com.dexels.navajo.reactive.impl;

import com.dexels.navajo.reactive.CoreReactiveFinder;
import com.dexels.navajo.reactive.api.Reactive;

public class OSGiReactiveFinder extends CoreReactiveFinder {

	
	public void activate() {
		//
		Reactive.setFinderInstance(this);
	}
	
	public void deactivate() {
		Reactive.setFinderInstance(null);
	}
}
