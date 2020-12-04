/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events.types;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NavajoEventMapTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Assert.assertEquals(NavajoEventMap.navajoEvents.size(), 12);
		Assert.assertEquals(
				NavajoEventMap.getEventClass(NavajoEventMap.AUDITLOG_EVENT),
				AuditLogEvent.class);
		Assert.assertEquals(NavajoEventMap
				.getEventClass(NavajoEventMap.COMPILESCRIPT_EVENT),
				NavajoCompileScriptEvent.class);
		Assert.assertEquals(
				NavajoEventMap.getEventClass(NavajoEventMap.HEALTH_CHECK_EVENT),
				NavajoHealthCheckEvent.class);
		Assert.assertEquals(NavajoEventMap
				.getEventClass(NavajoEventMap.QUEUABLE_FAILURE_EVENT),
				QueuableFailureEvent.class);
		Assert.assertEquals(NavajoEventMap
				.getEventClass(NavajoEventMap.QUEUABLE_FINISHED_EVENT),
				QueuableFinishedEvent.class);
		Assert.assertEquals(
				NavajoEventMap.getEventClass(NavajoEventMap.REQUEST_EVENT),
				NavajoRequestEvent.class);
		Assert.assertEquals(
				NavajoEventMap.getEventClass(NavajoEventMap.RESPONSE_EVENT),
				NavajoResponseEvent.class);
		Assert.assertEquals(NavajoEventMap
				.getEventClass(NavajoEventMap.SERVER_TOO_BUSY_EVENT),
				ServerTooBusyEvent.class);
		Assert.assertEquals(NavajoEventMap
				.getEventClass(NavajoEventMap.TRIBEMEMBER_DOWN_EVENT),
				TribeMemberDownEvent.class);
		Assert.assertEquals(
				NavajoEventMap.getEventClass(NavajoEventMap.EXCEPTION_EVENT),
				NavajoExceptionEvent.class);
//		Assert.assertEquals(NavajoEventMap
//				.getEventClass(NavajoEventMap.FULL_ACCESS_LOG_STATISTICS),
//				AccessLogEvent.class);
	}

}
