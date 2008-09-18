package com.dexels.navajo;

import com.dexels.navajo.adapter.queue.RequestResponseQueueTest;
import com.dexels.navajo.events.NavajoEventRegistryTest;
import com.dexels.navajo.events.types.AuditLogEventTest;
import com.dexels.navajo.events.types.NavajoCompileScriptEventTest;
import com.dexels.navajo.events.types.NavajoEventMapTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllUnitTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.dexels.navajo.adapter.queue");
		//$JUnit-BEGIN$
		suite.addTestSuite(RequestResponseQueueTest.class);
		suite.addTestSuite(NavajoEventRegistryTest.class);
		suite.addTestSuite(AuditLogEventTest.class);
		suite.addTestSuite(NavajoCompileScriptEventTest.class);
		suite.addTestSuite(NavajoEventMapTest.class);
		//$JUnit-END$
		return suite;
	}

}
