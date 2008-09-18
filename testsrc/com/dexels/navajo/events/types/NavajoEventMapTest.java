package com.dexels.navajo.events.types;

import junit.framework.Assert;
import junit.framework.TestCase;

public class NavajoEventMapTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test() {
		Assert.assertEquals(NavajoEventMap.navajoEvents.size(), 9);
		Assert.assertEquals(NavajoEventMap.getEventClass(NavajoEventMap.AUDITLOG_EVENT), AuditLogEvent.class);
		Assert.assertEquals(NavajoEventMap.getEventClass(NavajoEventMap.COMPILESCRIPT_EVENT), NavajoCompileScriptEvent.class);
		Assert.assertEquals(NavajoEventMap.getEventClass(NavajoEventMap.HEALTH_CHECK_EVENT), NavajoHealthCheckEvent.class);
		Assert.assertEquals(NavajoEventMap.getEventClass(NavajoEventMap.QUEUABLE_FAILURE_EVENT), QueuableFailureEvent.class);
		Assert.assertEquals(NavajoEventMap.getEventClass(NavajoEventMap.QUEUABLE_FINISHED_EVENT), QueuableFinishedEvent.class);
		Assert.assertEquals(NavajoEventMap.getEventClass(NavajoEventMap.REQUEST_EVENT), NavajoRequestEvent.class);
		Assert.assertEquals(NavajoEventMap.getEventClass(NavajoEventMap.RESPONSE_EVENT), NavajoResponseEvent.class);
		Assert.assertEquals(NavajoEventMap.getEventClass(NavajoEventMap.SERVER_TOO_BUSY_EVENT), ServerTooBusyEvent.class);
		Assert.assertEquals(NavajoEventMap.getEventClass(NavajoEventMap.TRIBEMEMBER_DOWN_EVENT), TribeMemberDownEvent.class);
	}

}
