package com.dexels.navajo.scheduler;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ListenerRunnerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		ListenerRunner.getInstance().kill();
	}

	public void testGetInstance() throws Exception {
		int MAXTHREADS = 100;
		
		final ListenerRunner [] instances = new ListenerRunner[MAXTHREADS];
		Thread [] threads = new Thread[MAXTHREADS];
		
		for (int i = 0; i < MAXTHREADS; i++) {
			final int index = i;
			threads[i] = new Thread() {
				public void run() {
					instances[index] = ListenerRunner.getInstance();
				}
			};
		}
		
		// Start 'm up.
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].start();
		}
		
		// Join.
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].join(10000);
		}
		
		// Asserts.
		Assert.assertNotNull(instances[0]);
		for (int i = 1; i < MAXTHREADS; i++) {
			Assert.assertNotNull(instances[i]);
			Assert.assertEquals(instances[i-1].hashCode(), instances[i].hashCode());
		}
	}

}
