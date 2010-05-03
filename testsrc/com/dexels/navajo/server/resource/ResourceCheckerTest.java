package com.dexels.navajo.server.resource;

import junit.framework.TestCase;

public class ResourceCheckerTest extends TestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAvailable() throws Exception {
		ResourceChecker rc = new ResourceChecker();
		rc.init(new CompiledTestScript());
		assertNotNull(rc.getServiceAvailability());
		assertFalse(rc.getServiceAvailability().isAvailable());
	}
	
	public void testWaitingTime() throws Exception {
		ResourceChecker rc = new ResourceChecker();
		rc.init(new CompiledTestScript());
		assertNotNull(rc.getServiceAvailability());
		assertEquals(2000, rc.getServiceAvailability().getWaitingTime());
	}

	public void testAvailable2() throws Exception {
		ResourceChecker rc = new ResourceChecker();
		rc.init(new CompiledTestScript2());
		assertNotNull(rc.getServiceAvailability());
		assertTrue(rc.getServiceAvailability().isAvailable());
	}
}
