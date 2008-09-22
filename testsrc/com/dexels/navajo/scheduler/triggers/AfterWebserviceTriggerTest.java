package com.dexels.navajo.scheduler.triggers;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.scheduler.TaskListener;
import com.dexels.navajo.scheduler.TaskRunner;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.TestNavajoConfig;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AfterWebserviceTriggerTest extends TestCase implements TaskListener, NavajoListener {

	boolean receivedTask = false;
	boolean receivedWS = false;
	boolean sleepABit = false;
	
	private String monitorTask = null;
	
	// Semaphores.
	static final Object waitForTaskEvent = new Object();
	static final Object waitForWSEvent = new Object();
	
	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
		receivedTask = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	private void waitForEvents() throws Exception {
		Thread t1 = new Thread() {
			public void run() {
				synchronized (waitForTaskEvent) {
					try {
						waitForTaskEvent.wait(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t1.start();
		Thread t2 = new Thread() {
			public void run() {
				synchronized (waitForWSEvent) {
					try {
						waitForWSEvent.wait(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t2.start();
		t1.join();
		t2.join();
	}
	
	/**
	 * TODO: NOT WORKING YET!!!!!
	 * @throws Exception
	 */
	public void testActivateTriggerWithoutWebservice() throws Exception {
		
		Task t = new Task(null,"test","test",null,"navajo:navajo_ping",null);
		t.setId("task-1");
		t.getTrigger().activateTrigger();
		monitorTask = t.getId();
		System.err.println("t = " + t.getTrigger() + ", ws = " + t.getWebservice());
		Assert.assertNotNull(t);
//		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, this);
		TaskRunner.getInstance().addTaskListener(this);
		
		// Create navajo_ping call.
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "navajo_ping", "test", "test", -1);
		doc.addHeader(h);
		
		// Once...
		receivedTask = false;
		// To test synchronous character, set sleepABit to true in order to force sleep in Task listener.
		sleepABit = true;
		Navajo r = DispatcherFactory.getInstance().handle(doc, true);
		// Because we do not define a webservice in out task, this trigger should be handled completely synchronously.
		Assert.assertTrue(receivedTask);
		
		// Twice...
		// To test synchronous character, set sleepABit to true in order to force sleep in Task listener.
		sleepABit = true;
		receivedTask = false;
		r = DispatcherFactory.getInstance().handle(doc, true);
		// Because we do not define a webservice in out task, this trigger should be handled completely synchronously.
		Assert.assertTrue(receivedTask);
	}
	
	public void testActivateTriggerWithWebservice() throws Exception {
		
		Task t = new Task("navajo_test","test","test",null,"navajo:navajo_ping",null);
		t.setId("task-2");
		t.getTrigger().activateTrigger();
		monitorTask = t.getId();
		System.err.println("t = " + t.getTrigger() + ", ws = " + t.getWebservice());
		Assert.assertNotNull(t);
		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, this);
		TaskRunner.getInstance().addTaskListener(this);
		
		// Run ws navajo_ping.
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "navajo_ping", "test", "test", -1);
		doc.addHeader(h);
		
		
		// Once...
		System.err.println("================================== ONCE ===============================================================");
		receivedTask = false;
		receivedWS = false;
		sleepABit = true;
		
		Navajo r = DispatcherFactory.getInstance().handle(doc, true);
		// Test asynchronous character by immediately asserting that trigger occurred...
		Assert.assertFalse(receivedTask && receivedWS);
		waitForEvents();
		Assert.assertTrue(receivedTask && receivedWS);
		
		// Twice...
		System.err.println("================================== TWICE ===============================================================");
		receivedTask = false;
		receivedWS = false;
		sleepABit = true;
		r = DispatcherFactory.getInstance().handle(doc, true);
		// Test asynchronous character by immediately asserting that trigger occurred...
		Assert.assertFalse(receivedTask && receivedWS);
		waitForEvents();
		Assert.assertTrue(receivedTask && receivedWS);
	}

	public void testRemoveTrigger() throws Exception {
		
		Task t = new Task("navajo_test","test","test",null,"navajo:navajo_ping",null);
		t.setId("task-3");
		monitorTask = t.getId();
		t.getTrigger().activateTrigger();
		Assert.assertNotNull(t);
		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, this);
		TaskRunner.getInstance().addTaskListener(this);
		
		// Run ws navajo_ping.
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "navajo_ping", "test", "test", -1);
		doc.addHeader(h);
		
		// Once...
		receivedTask = false;
		receivedWS = false;
		Navajo r = DispatcherFactory.getInstance().handle(doc, true);
		//System.err.println("r = " + r);
		waitForEvents();
		Assert.assertTrue(receivedTask && receivedWS);
		
		// Twice...
		receivedTask = false;
		receivedWS = false;
		r = DispatcherFactory.getInstance().handle(doc, true);
		System.err.println("Task = " + t.getTrigger().isSingleEvent() );
		waitForEvents();
		Assert.assertTrue(receivedTask && receivedWS);
		
		
		// Remove trigger...
		receivedTask = false;
		receivedWS = false;
		t.getTrigger().removeTrigger();
		r = DispatcherFactory.getInstance().handle(doc, true);
		waitForEvents();
		Assert.assertFalse(receivedTask && receivedWS);
		
	}
	
	public static void main(String [] args) throws Exception {
		AfterWebserviceTriggerTest t = new AfterWebserviceTriggerTest();
		t.setUp();
		t.testRemoveTrigger();
	}

	public void afterTask(Task t, Navajo response) {
		// TODO Auto-generated method stub
		System.err.println("********************************* IN AFTER TASK: " + t.getId());
		try {
			if ( sleepABit ) {
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( t.getId().equals(monitorTask) ) {
			receivedTask = true;
			System.err.println("receivedTask = " + receivedTask + "*******************************************");
			synchronized (waitForTaskEvent) {
				waitForTaskEvent.notify();
			}
		}
	}

	public boolean beforeTask(Task t, Navajo request) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onNavajoEvent(NavajoEvent ne) {
		System.err.println("-------------------------------- in onNavajoEvent(): " + ((NavajoResponseEvent) ne).getAccess().getRpcName());
		// Sleep a bit to test asynchronicity...
		try {
			if ( sleepABit ) {
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( ((NavajoResponseEvent) ne).getAccess().getRpcName().equals("navajo_test") ) {
			receivedWS = true;
			System.err.println("receivedWS = " + receivedWS + "--------------------------------------------------");
			synchronized (waitForWSEvent) {
				waitForWSEvent.notify();
			}
		}
	}
	

}
