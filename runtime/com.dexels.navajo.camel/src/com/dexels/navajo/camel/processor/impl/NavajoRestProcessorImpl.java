package com.dexels.navajo.camel.processor.impl;

import java.io.StringWriter;
import java.io.Writer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;

public class NavajoRestProcessorImpl implements Processor {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoRestProcessorImpl.class);
	
	public NavajoRestProcessorImpl() {
		logger.warn("Navajo input processor present!");
	}

	@Override
	public void process(Exchange ex) throws Exception {
		logger.warn("Start proccing NavajoInput");
		org.apache.camel.Message in = ex.getIn();
		Object body = in.getBody();
		if (! (body instanceof Navajo)) {
			throw new Exception();
		}
		Navajo document = (Navajo) body;
		String targeturl = document.getHeader().getHeaderAttribute("targeturl");
		String targetmethod = document.getHeader().getHeaderAttribute("targetmethod");
		JSONTML json = JSONTMLFactory.getInstance();
		Writer w = new StringWriter()		;
		json.format(document, w, false);
		ex.getOut().setBody(w.toString());
		ex.getOut().setHeader("targeturl", targeturl);
		ex.getOut().setHeader("CamelHttpMethod", targetmethod);
		ex.getOut().setHeader("origDocument", document);
	}
}
