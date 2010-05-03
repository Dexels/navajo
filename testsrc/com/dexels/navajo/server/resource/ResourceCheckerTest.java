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
		assertFalse(rc.isAvailable());
	}
	
	public void testWaitingTime() throws Exception {
		ResourceChecker rc = new ResourceChecker();
		rc.init(new CompiledTestScript());
		assertEquals(2000, rc.getWaitingTime());
	}

	public void testAvailable2() throws Exception {
		ResourceChecker rc = new ResourceChecker();
		rc.init(new CompiledTestScript2());
		assertTrue(rc.isAvailable());
	}
}
