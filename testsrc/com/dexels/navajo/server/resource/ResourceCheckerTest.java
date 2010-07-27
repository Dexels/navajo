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
		ResourceChecker rc = new ResourceChecker(new CompiledTestScript());
		assertNotNull(rc.getServiceAvailability());
		assertFalse(rc.getServiceAvailability().isAvailable());
	}
	
	public void testWaitingTime() throws Exception {
		ResourceChecker rc = new ResourceChecker(new CompiledTestScript());
		assertNotNull(rc.getServiceAvailability());
		assertEquals(2000, rc.getServiceAvailability().getWaitingTime());
	}

	public void testAvailable2() throws Exception {
		ResourceChecker rc = new ResourceChecker(new CompiledTestScript2());
		assertNotNull(rc);
		assertTrue(rc.getServiceAvailability().isAvailable());
	}
	
	public void testWithServiceDependencies() throws Exception {
		ResourceChecker rc4 = new ResourceChecker(new CompiledTestScript4());
		ResourceCheckerManager.addCheckedService("CompiledTestScript4", rc4);
		
		ResourceChecker rc3 = new ResourceChecker(new CompiledTestScript3());
		ResourceCheckerManager.addCheckedService("CompiledTestScript3", rc3);
		assertNotNull(rc3);
		// Since rc4 depends on id5 resource which has status VERYBUSY, we expect rc3 also
		// to have status VERYBUSY since this is the highest health level.
		assertEquals(ServiceAvailability.STATUS_DEAD, rc3.getServiceAvailability().getHealth());
		// Also isAvailable should be false, since health status is DEAD.
		assertEquals(false, rc3.getServiceAvailability().isAvailable());
	}
	
	public void testManager() throws Exception {
		
	}
}
