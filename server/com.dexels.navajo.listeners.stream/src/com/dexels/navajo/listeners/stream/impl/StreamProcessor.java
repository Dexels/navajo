package com.dexels.navajo.listeners.stream.impl;

import rx.observables.ConnectableObservable;

public class StreamProcessor {
	private final ConnectableObservable<byte[]> observable;

	public StreamProcessor(ConnectableObservable<byte[]> o) {
		this.observable = o;
	}
}
