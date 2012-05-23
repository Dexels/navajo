package com.dexels.navajo.dsl.tsl;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

public class InjectionTest {
	@Inject
	public Logger logger;

	public InjectionTest() {
		System.err.println("Construc.");
	}
	public void doSomething() {
		logger.fatal("pow!");
	}
}
