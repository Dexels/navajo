package com.dexels.navajo.events.types;

import junit.framework.Assert;
import junit.framework.TestCase;

public class NavajoCompileScriptEventTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test() {
		NavajoCompileScriptEvent ncse = new NavajoCompileScriptEvent("webservice");
		Assert.assertEquals(ncse.getWebservice(), "webservice");
		Assert.assertNotNull(ncse.getEventNavajo());
		if ( ncse.getEventNavajo() != null ) {
			Assert.assertNotNull(ncse.getEventNavajo().getMessage("__event__"));
			Assert.assertNotNull(ncse.getEventNavajo().getMessage("__event__").getProperty("Webservice"));
			Assert.assertEquals(ncse.getEventNavajo().getMessage("__event__").getProperty("Webservice").getValue(), "webservice");
		}
	}

}
