package com.dexels.navajo.scheduler;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.TestNavajoConfig;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TaskRunnerTest extends TestCase {

	private String sampleConfig = 
		"<tml><message name=\"tasks\" type=\"array\">" +
        "<message index=\"0\" name=\"tasks\" type=\"array_element\">" +
        "<property direction=\"out\" name=\"id\" value=\"aap\"  type=\"string\"/>" +
        "<property direction=\"out\" name=\"username\" value=\"testuser\"  type=\"string\"/>" +
        "<property direction=\"out\" name=\"password\" value=\"testpassword\"  type=\"string\"/>" +
        "<property direction=\"out\" name=\"webservice\" value=\"navajo_test\"  type=\"string\"/>" +
        "<property direction=\"out\" name=\"proxy\" value=\"false\"  type=\"boolean\"/>" +
        "<property direction=\"out\" name=\"keeprequestresponse\" value=\"false\"  type=\"boolean\"/>" +
        "<property direction=\"out\" name=\"trigger\" value=\"time:*|*|20|47|*\"  type=\"string\"/>" +
        "<property direction=\"out\" name=\"workflowdef\"  type=\"string\"/>" +
        "<property direction=\"out\" name=\"workflowid\"  type=\"string\"/>" +
        "<property direction=\"out\" name=\"taskdescription\" value=\"\"  type=\"string\"/>" +
        "<property direction=\"out\" name=\"clientid\" value=\"\"  type=\"string\"/>" +
        "<property direction=\"out\" name=\"persisted\" value=\"true\"  type=\"boolean\"/>" +
      "</message>" +
   "</message></tml>";
	
	private void createTaskConfig() {
		Navajo config = NavajoFactory.getInstance().createNavajo(new StringReader(sampleConfig));
		try {
			DispatcherFactory.getInstance().getNavajoConfig().writeConfig("tasks.xml", config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public static void main(String [] args) throws Exception {
		TaskRunnerTest test = new TaskRunnerTest();
		test.setUp();
		test.createTaskConfig();
		Navajo config = DispatcherFactory.getInstance().getNavajoConfig().readConfig("tasks.xml");
		test.testAddTaskTaskInterfaceForWorkflowTask();
	}

	public void testWorker() {
		TaskRunner tr = TaskRunner.getInstance();
		tr.worker();
		Assert.assertEquals(1, tr.getTaskListSize());
	}
	
	public void testGetTasks() {
		TaskRunner tr = TaskRunner.getInstance();
		tr.worker();
		Map tasks = tr.getTasks();
		Assert.assertNotNull(tasks.get("aap"));
		Task t = tr.getTasks().get("aap");
		Assert.assertEquals("navajo_test", t.getWebservice());
	}

	/**
	 * TODO: WRITE LOTS OF TESTS FOR ADDING TASKS...
	 */
	public void testAddTaskTaskInterfaceForWorkflowTask() throws Exception {
		TaskRunner tr = TaskRunner.getInstance();
		tr.worker();
		Task t = new Task("navajo_ping", "testuser", "testpassword", null, "navajo:navajo_test", null);
		t.setWorkflowDefinition("testworkflow");
		t.setWorkflowId("23232");
		tr.addTask(t);
		
		// Verify that task is NOT written to tasks.xml:
		Navajo config = DispatcherFactory.getInstance().getNavajoConfig().readConfig("tasks.xml");
		Message newTask = config.getMessage("/tasks@1");
		Assert.assertNull(newTask);
		
		// Verify that trigger is up and running.
		Assert.assertTrue(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("navajo_test"));
		
		// Clean up.
		tr.removeTask(t);
		Assert.assertFalse(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("navajo_test"));
		
	}
	
	public void testAddTaskTaskInterfaceForUserTask() throws Exception {
		TaskRunner tr = TaskRunner.getInstance();
		tr.worker();
		Task t = new Task("navajo_ping", "testuser", "testpassword", null, "navajo:navajo_test", null);
		tr.addTask(t);
		
		// Verify that task is written to tasks.xml:
		Navajo config = DispatcherFactory.getInstance().getNavajoConfig().readConfig("tasks.xml");
		Message newTask = config.getMessage("/tasks@1");
		newTask.write(System.err);
		
		// Verify that trigger is up and running.
		Assert.assertTrue(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("navajo_test"));
		
		// Clean up.
		tr.removeTask(t);
		Assert.assertFalse(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("navajo_test"));
	}
	
	public void testAddTaskTaskInterfaceForUserTaskMultipleThreads() throws Exception {
		final TaskRunner tr = TaskRunner.getInstance();
		tr.worker();
		final Task t = new Task("navajo_ping", "testuser", "testpassword", null, "navajo:navajo_test", null);
		final Task t2 = new Task("navajo_hello", "testuser", "testpassword", null, "navajo:navajo_ping", null);
		final Task t3 = new Task("aap", "testuser", "testpassword", null, "navajo:noot", null);
		
		new Thread() {
			public void run() {
				tr.addTask(t);
			}
		}.start();
		
		new Thread() {
			public void run() {
				tr.addTask(t2);
			}
		}.start();
		
		new Thread() {
			public void run() {
				tr.addTask(t3);
			}
		}.start();
		
		// Verify that tasks are written to tasks.xml:
		int allPresent = 0;
		Navajo config = DispatcherFactory.getInstance().getNavajoConfig().readConfig("tasks.xml");
		Message tasks = config.getMessage("/tasks");
		// Check presence of all three tasks...
		for (int i = 0; i < tasks.getArraySize(); i++) {
			Message m = tasks.getMessage(i);
			if ( m.getProperty("webservice").getValue().equals("navajo_ping")) {
				allPresent++;
			}
			if ( m.getProperty("webservice").getValue().equals("navajo_hello")) {
				allPresent++;
			}
			if ( m.getProperty("webservice").getValue().equals("aap")) {
				allPresent++;
			}
		}
		Assert.assertEquals(3, allPresent);
		
		// Verify that trigger is up and running.
		Assert.assertTrue(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("navajo_test"));
		Assert.assertTrue(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("navajo_ping"));
		Assert.assertTrue(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("noot"));
		
		// Clean up.
		tr.removeTask(t);
		tr.removeTask(t2);
		tr.removeTask(t3);
		Assert.assertFalse(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("navajo_test"));
		Assert.assertFalse(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("navajo_ping"));
		Assert.assertFalse(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("noot"));
	}
	
	public void testTerminate() {
		fail("Not yet implemented");
	}

	public void testTaskRunner() {
		fail("Not yet implemented");
	}

	public void testContainsTask() {
		fail("Not yet implemented");
	}

	public void testWriteTaskInput() {
		fail("Not yet implemented");
	}

	public void testWriteTaskOutput() {
		fail("Not yet implemented");
	}

	public void testGetTaskOutput() {
		fail("Not yet implemented");
	}

	public void testReadTaskInput() {
		fail("Not yet implemented");
	}

	public void testRemoveTaskInput() {
		fail("Not yet implemented");
	}

	public void testRemoveTaskOutput() {
		fail("Not yet implemented");
	}

	public void testReadConfig() {
		fail("Not yet implemented");
	}

	public void testGetInstance() {
		fail("Not yet implemented");
	}

	public void testGetTaskListSize() {
		fail("Not yet implemented");
	}

	public void testGetConfigLock() {
		fail("Not yet implemented");
	}

	public void testReleaseConfigLock() {
		fail("Not yet implemented");
	}

	public void testWriteTaskConfig() {
		fail("Not yet implemented");
	}

	public void testRemoveTaskStringBoolean() {
		fail("Not yet implemented");
	}

	public void testGetFinishedTasks() {
		fail("Not yet implemented");
	}

	public void testLog() {
		fail("Not yet implemented");
	}

	public void testUpdateTask() {
		fail("Not yet implemented");
	}

	public void testAddTaskStringTaskBoolean() {
		fail("Not yet implemented");
	}

	public void testAddTaskListener() {
		fail("Not yet implemented");
	}

	public void testRemoveTaskListener() {
		fail("Not yet implemented");
	}

	public void testFireAfterTaskEvent() {
		fail("Not yet implemented");
	}

	public void testFireBeforeTaskEvent() {
		fail("Not yet implemented");
	}

	public void testRemoveTaskTaskInterface() {
		fail("Not yet implemented");
	}

}
