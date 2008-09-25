package com.dexels.navajo.server;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.test.TestNavajoConfig;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DispatcherTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testHandle1() throws Exception {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "navajo_ping", "test", "test", -1);
		doc.addHeader(h);
		
		Navajo r = DispatcherFactory.getInstance().handle(doc, true);
		Assert.assertNotNull(r);
		Assert.assertNotNull(r.getMessage("ping"));
		
	}

}
