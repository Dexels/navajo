package com.dexels.navajo.scheduler;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.scheduler.triggers.AfterWebserviceTrigger;
import com.dexels.navajo.scheduler.triggers.IllegalTrigger;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.TestNavajoConfig;

public class TaskTest extends TestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTask() {
		Task t = new Task();
		Assert.assertNull(t.getTrigger());
	}

	@Test
	public void testTaskStringStringStringAccessStringNavajo() {
		// Case I
		boolean failure = false;
		try {
			Task t = new Task("webservice", null, "password", null, "", null);
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalTask e) {
			failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case II
		failure = false;
		try {
			Task t = new Task("webservice", "username", null, null, "", null);
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalTask e) {
			failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case III
		failure = false;
		try {
			Task t = new Task("webservice", null, null, null, "", null);
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalTask e) {
			failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case IV: Illegal trigger.
		failure = false;
		try {
			Task t = new Task(null, null, null, null, "", null);
		} catch (IllegalTrigger e) {
			failure = true;
		} catch (IllegalTask e) {
			//failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case V: Null trigger.
		failure = false;
		try {
			Task t = new Task(null, null, null, null, null, null);
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failure = true;
		} catch (IllegalTask e) {
			//failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case VI: Valid trigger.
		failure = true;
		try {
			Task t = new Task(null, null, null, null, "navajo:aap", null);
			failure = false;
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalTask e) {
			failure = true;
		}
		Assert.assertFalse(failure);
	}

	@Test
	public void testGetTrigger() throws Exception {
		Task t = new Task(null, null, null, null, "navajo:aap", null);
		Assert.assertNotNull(t.getTrigger());
		Assert.assertEquals(t.getTrigger().getClass(), AfterWebserviceTrigger.class);
	}

	@Test
	public void testSetTrigger() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWebservice() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClientId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTaskDescription() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetWebservice() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsername() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetUsername() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetClientId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTaskDescription() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetInactive() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsActive() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsRunning() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetNavajo() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRequest() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNavajo() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testRun() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTriggerDescription() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetInstance() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsFinished() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFinished() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFinishedTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFinishedTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStartTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetStartTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetErrorMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetErrorMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWorkflowDefinition() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetWorkflowDefinition() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWorkflowId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetWorkflowId() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsProxy() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetProxy() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsKeepRequestResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetKeepRequestResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPersisted() {
		fail("Not yet implemented");
	}

	@Test
	public void testNeedsPersistence() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPersisted() {
		fail("Not yet implemented");
	}

}
