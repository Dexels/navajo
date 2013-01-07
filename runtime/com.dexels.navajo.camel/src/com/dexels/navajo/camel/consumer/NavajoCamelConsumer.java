package com.dexels.navajo.camel.consumer;

import org.apache.camel.Consumer;

import com.dexels.navajo.script.api.TmlRunnable;

public interface NavajoCamelConsumer extends Consumer {

	public abstract void process(TmlRunnable tr) throws Exception;

}