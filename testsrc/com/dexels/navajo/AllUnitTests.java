package com.dexels.navajo;

import com.dexels.navajo.adapter.queue.RequestResponseQueueTest;
import com.dexels.navajo.events.NavajoEventRegistryTest;
import com.dexels.navajo.events.types.AuditLogEventTest;
import com.dexels.navajo.events.types.NavajoCompileScriptEventTest;
import com.dexels.navajo.events.types.NavajoEventMapTest;
import com.dexels.navajo.integrity.WorkerTest;
import com.dexels.navajo.mapping.bean.DomainObjectMapperTest;
import com.dexels.navajo.mapping.bean.ServiceMapperTest;
import com.dexels.navajo.mapping.compiler.meta.SQLFieldDependencyTest;
import com.dexels.navajo.persistence.impl.PersistenceManagerImplTest;
import com.dexels.navajo.scheduler.ListenerRunnerTest;
import com.dexels.navajo.scheduler.ListenerStoreTest;
import com.dexels.navajo.scheduler.TaskRunnerTest;
import com.dexels.navajo.scheduler.TaskTest;
import com.dexels.navajo.scheduler.triggers.TriggerTest;
import com.dexels.navajo.server.CacheControllerTest;
import com.dexels.navajo.sharedstore.SharedStoreInterfaceTest;

import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.TestSuite;

public class AllUnitTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Navajo Kernel");
		//$JUnit-BEGIN$
		suite.addTestSuite(RequestResponseQueueTest.class);
		suite.addTestSuite(NavajoEventRegistryTest.class);
		suite.addTestSuite(AuditLogEventTest.class);
		suite.addTestSuite(NavajoCompileScriptEventTest.class);
		suite.addTestSuite(NavajoEventMapTest.class);
		suite.addTestSuite(WorkerTest.class);
		suite.addTestSuite(TaskTest.class);
		suite.addTestSuite(TriggerTest.class);
		suite.addTestSuite(ListenerRunnerTest.class);
		suite.addTestSuite(ListenerStoreTest.class);
		suite.addTestSuite(SharedStoreInterfaceTest.class);
		suite.addTestSuite(TaskRunnerTest.class);
		suite.addTestSuite(DomainObjectMapperTest.class);
		suite.addTestSuite(ServiceMapperTest.class);
		//suite.addTestSuite(CacheControllerTest.class); THIS ONE IS TESTED IN THE PERSISTENCEMANAGERIMPL...
		suite.addTestSuite(PersistenceManagerImplTest.class);
		suite.addTestSuite(SQLFieldDependencyTest.class);
		//$JUnit-END$
		return suite;
	}

}
