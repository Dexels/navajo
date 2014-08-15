package com.dexels.navajo.camel.processor.impl;

import java.io.StringReader;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;

public class JsonNavajoProcessorImpl implements Processor {

	
	private final static Logger logger = LoggerFactory
			.getLogger(JsonNavajoProcessorImpl.class);
	
	public JsonNavajoProcessorImpl() {
		logger.warn("Navajo input processor present!");
	}

	@Override
	public void process(Exchange ex) throws Exception {
		logger.warn("Start proccing NavajoInput");
		org.apache.camel.Message in = ex.getIn();
		Object body = in.getBody(String.class);
		Navajo doc = null;
		
		if (body == null || body.equals("")) {
			// Nothing to parse...
			doc = NavajoFactory.getInstance().createNavajo();

		} else {
			JSONTML json = JSONTMLFactory.getInstance();
			doc = json.parse(new StringReader((String) body));
		}
		
		ex.getOut().setBody(doc);
		 // copy headers from IN to OUT to propagate them
		ex.getOut().setHeaders(ex.getIn().getHeaders());
	}
}
