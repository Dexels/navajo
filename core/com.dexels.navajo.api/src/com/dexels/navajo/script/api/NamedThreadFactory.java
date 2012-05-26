package com.dexels.navajo.script.api;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

	private final String name;
	private long counter = 0;
	public NamedThreadFactory(String name) {
		this.name = name;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = Executors.defaultThreadFactory().newThread(r);
		t.setName(name+"_"+getSuffix());
		return t;
	}

	private long getSuffix() {
		return ++counter;
	}

}