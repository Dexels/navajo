package com.dexels.navajo.resource.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.resource.http.impl.ResourceComponent;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TestHttpResource {

	@Before
	public void setUp() throws Exception {
		HttpResourceFactory factory = new HttpResourceFactory();
		ResourceComponent component = new ResourceComponent();
		Map<String,Object> settings = new HashMap<String, Object>();
		settings.put("name", "junit.mail");
		settings.put("host", "smtp.google.com");
		settings.put("username", "dexels@gmail.com");
		settings.put("password", "seriously");
		settings.put("encrypt", true);
		component.activate(settings);
		factory.addHttpResource(component, settings);
		factory.activate();

		
	}

	@After
	public void tearDown() throws Exception {
		HttpResourceFactory.getInstance().deactivate();	
	}

	@Test
	public void testSimple() throws MappableException, UserException {
//		ResourceHttpAdapter rma = new ResourceMailAdapter();
//		rma.load(new Access());
//		rma.setRecipients("dexels@gmail.com");
//		rma.setSender("dexels@gmail.com");
//		rma.setSubject("Mail at "+new Date());	
//		rma.store();
	}
}
