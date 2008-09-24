package com.dexels.navajo.events;

import com.dexels.navajo.events.types.AuditLogEvent;

import junit.framework.Assert;
import junit.framework.TestCase;

public class NavajoEventRegistryTest extends TestCase {

	static NavajoEventRegistry instance1;
	static NavajoEventRegistry instance2;
	static NavajoEventRegistry instance3;
	static NavajoEventRegistry instance4;
	
	static String message;
	
	protected void setUp() throws Exception {
		super.setUp();
		NavajoEventRegistry.clearInstance();
		message = null;
		instance1 = null;
		instance2 = null;
		instance3 = null;
		instance4 = null;
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		NavajoEventRegistry.clearInstance();
	}
	
	public void testClearInstance() throws Exception {
		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();
		NavajoEventRegistry.clearInstance();
		NavajoEventRegistry instanceagain = NavajoEventRegistry.getInstance();
		Assert.assertNotSame(instance, instanceagain);
		
	}
	
	public void testGetInstance() throws Exception {
		
		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();
		Assert.assertNotNull(instance);
		
		NavajoEventRegistry instanceagain = NavajoEventRegistry.getInstance();
		Assert.assertEquals(instance, instanceagain);
			
	}
	
	public void testGetInstanceMultipleThreads() throws Exception {
		
		Thread t1 = new Thread() {
			public void run() {
				instance1 = NavajoEventRegistry.getInstance();
				System.err.println("instance1 = " + instance1);
			}
		};
		
		Thread t2 = new Thread() {
			public void run() {
				instance2 = NavajoEventRegistry.getInstance();
				System.err.println("instance2 = " + instance2);
			}
		};
		
		Thread t3 = new Thread() {
			public void run() {
				instance3 = NavajoEventRegistry.getInstance();
				System.err.println("instance3 = " + instance3);
			}
		};
		
		Thread t4 = new Thread() {
			public void run() {
				instance4 = NavajoEventRegistry.getInstance();
				System.err.println("instance4 = " + instance4);
			}
		};
		
		t2.start();
		t1.start();
		t4.start();
		t3.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
	
		Assert.assertEquals(instance1, instance2);
		Assert.assertEquals(instance2, instance3);
		Assert.assertEquals(instance3, instance4);
	}
	
	public void testAddListener() {
		
		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();
		
		Assert.assertEquals(instance.getNumberOfRegisteredListeners(), 0);
		instance.addListener(AuditLogEvent.class, new NavajoListener() {

			public void onNavajoEvent(NavajoEvent ne) {
				System.err.println("Received AuditLogEvent!");
			} } 
		);
		
		Assert.assertEquals(instance.getNumberOfRegisteredListeners(), 1);
		
	}
	
	public void testPublishEvent() {
	
		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();
		instance.addListener(AuditLogEvent.class, new NavajoListener() {
			public void onNavajoEvent(NavajoEvent ne) {
				message = "Received AuditLogEvent!";
				System.err.println(message);
			} } 
		);
		instance.publishEvent(new AuditLogEvent("","",""));
		Assert.assertEquals(message, "Received AuditLogEvent!");
	}
	
	public void testPublishAsynchronousEvent() {
		
		final Object semaphore = new Object();
		
		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();
		instance.addListener(AuditLogEvent.class, new NavajoListener() {
			public void onNavajoEvent(NavajoEvent ne) {
				message = "Received AuditLogEvent!";
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.err.println(message);
				synchronized (semaphore) {
					semaphore.notify();
				}
			} } 
		);
		instance.publishAsynchronousEvent(new AuditLogEvent("","",""));
		// Should still be null....
		Assert.assertNull(message);
		
		synchronized (semaphore) {
			try {
				semaphore.wait(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Now it should have a value...
		Assert.assertEquals(message, "Received AuditLogEvent!");
	}

}
