package com.dexels.navajo.events.types;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ClearCacheEventTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test() {
		ClearCacheEvent ncse = new ClearCacheEvent("webservice", "apenoot");
		assertEquals("webservice", ncse.getWebservice());
		assertEquals("apenoot", ncse.getKey());
		assertNotNull(ncse.getEventNavajo());
		if ( ncse.getEventNavajo() != null ) {
			assertNotNull(ncse.getEventNavajo().getMessage("__event__"));
			assertNotNull(ncse.getEventNavajo().getMessage("__event__").getProperty("Webservice"));
			assertEquals(ncse.getEventNavajo().getMessage("__event__").getProperty("Webservice").getValue(), "webservice");
			assertNotNull(ncse.getEventNavajo().getMessage("__event__").getProperty("Key"));
			assertEquals(ncse.getEventNavajo().getMessage("__event__").getProperty("Key").getValue(), "apenoot");
		}
	}
	
}
