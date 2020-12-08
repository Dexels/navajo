/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events;

import java.util.logging.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.events.types.AuditLogEvent;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;

public class NavajoEventRegistryTest {

	static NavajoEventRegistry instance1;
	static NavajoEventRegistry instance2;
	static NavajoEventRegistry instance3;
	static NavajoEventRegistry instance4;

	static String message;

	
	private static final Logger logger = LoggerFactory
			.getLogger(NavajoEventRegistryTest.class);
	
	@Before
	public void setUp() {
		DispatcherFactory.createDispatcher(new TestDispatcher(new TestNavajoConfig()));
		NavajoEventRegistry.clearInstance();
		message = null;
		instance1 = null;
		instance2 = null;
		instance3 = null;
		instance4 = null;

	}

	@After
	public void tearDown() throws Exception {
		NavajoEventRegistry.clearInstance();
	}

	@Test
	public void testClearInstance() throws Exception {
		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();
		NavajoEventRegistry.clearInstance();
		NavajoEventRegistry instanceagain = NavajoEventRegistry.getInstance();
		Assert.assertNotSame(instance, instanceagain);

	}

	@Test
	public void testGetInstance() throws Exception {

		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();
		Assert.assertNotNull(instance);

		NavajoEventRegistry instanceagain = NavajoEventRegistry.getInstance();
		Assert.assertEquals(instance, instanceagain);

	}

	@Test
	public void testGetInstanceMultipleThreads() throws Exception {

		Thread t1 = new Thread() {
			@Override
			public void run() {
				instance1 = NavajoEventRegistry.getInstance();
				System.err.println("instance1 = " + instance1);
			}
		};

		Thread t2 = new Thread() {
			@Override
			public void run() {
				instance2 = NavajoEventRegistry.getInstance();
				System.err.println("instance2 = " + instance2);
			}
		};

		Thread t3 = new Thread() {
			@Override
			public void run() {
				instance3 = NavajoEventRegistry.getInstance();
				System.err.println("instance3 = " + instance3);
			}
		};

		Thread t4 = new Thread() {
			@Override
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

	@Test
	public void testAddListener() {

		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();

		Assert.assertEquals(instance.getNumberOfRegisteredListeners(), 0);
		instance.addListener(AuditLogEvent.class, new NavajoListener() {

			@Override
			public void onNavajoEvent(NavajoEvent ne) {
				System.err.println("Received AuditLogEvent!");
			}
		});

		Assert.assertEquals(instance.getNumberOfRegisteredListeners(), 1);

	}

	@Test
	public void testPublishEvent() {

		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();
		instance.addListener(AuditLogEvent.class, new NavajoListener() {
			@Override
			public void onNavajoEvent(NavajoEvent ne) {
				message = "Received AuditLogEvent!";
				System.err.println(message);
			}
		});
		instance.publishEvent(new AuditLogEvent("", "", Level.INFO));
		Assert.assertEquals(message, "Received AuditLogEvent!");
	}

	@Test
	public void testPublishAsynchronousEvent() {

		final Object semaphore = new Object();

		NavajoEventRegistry instance = NavajoEventRegistry.getInstance();
		instance.addListener(AuditLogEvent.class, new NavajoListener() {
			@Override
			public void onNavajoEvent(NavajoEvent ne) {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error("Error: ", e);
				}
				message = "Received AuditLogEvent!";
				System.err.println(message);
				synchronized (semaphore) {
					semaphore.notify();
				}
			}
		});
		message = null;
		instance.publishAsynchronousEvent(new AuditLogEvent("", "", Level.INFO));
		// Should still be null....
		Assert.assertNull(message);

		synchronized (semaphore) {
			try {
				semaphore.wait(2000);
			} catch (InterruptedException e) {
				logger.error("Error: ", e);
			}
		}
		// Now it should have a value...
		Assert.assertEquals(message, "Received AuditLogEvent!");
	}

}
