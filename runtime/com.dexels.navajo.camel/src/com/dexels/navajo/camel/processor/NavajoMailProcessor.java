package com.dexels.navajo.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class NavajoMailProcessor implements Processor {

	public NavajoMailProcessor() {
		System.err.println("Creating navajo mail processor");
	}
	
	@Override
	public void process(Exchange ex) throws Exception {
		Message in = ex.getIn();
		Message out = ex.getOut();

	}

}
