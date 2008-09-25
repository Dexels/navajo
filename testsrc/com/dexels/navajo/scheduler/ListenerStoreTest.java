package com.dexels.navajo.scheduler;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;

import junit.framework.Assert;
import junit.framework.TestCase;

class TestListener implements Serializable, Listener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 754028799338765085L;
	private String id = "TestListener";
	
	public void setListenerId(String s) {
		id = s;
	}
	
	public String getListenerId() {
		return id;
	}

	public String getOwnerHost() {
		return "OwnerHost";
	}

	public Navajo perform() {
		System.err.println("DO SOMETHING");
		return NavajoFactory.getInstance().createNavajo();
	}
}

public class ListenerStoreTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		ListenerStore.getInstance().terminate();
	}

	public void testGetInstance() throws Exception {
		
		int MAXTHREADS = 100;

		final ListenerStore [] instances = new ListenerStore[MAXTHREADS];
		Thread [] threads = new Thread[MAXTHREADS];

		for (int i = 0; i < MAXTHREADS; i++) {
			final int index = i;
			threads[i] = new Thread() {
				public void run() {
					instances[index] = ListenerStore.getInstance();
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

	public void testAddListener() {
		ListenerStore instance = ListenerStore.getInstance();
		instance.addListener(new TestListener());
		// Check if listener exists.
		Listener myStoredListener =  instance.getListeners("TestListener")[0];
		Assert.assertNotNull(myStoredListener);
		Assert.assertEquals("OwnerHost", myStoredListener.getOwnerHost());
		Assert.assertNotNull(myStoredListener.perform());
	}

	public void testRemoveListener() {
		ListenerStore instance = ListenerStore.getInstance();
		TestListener tl = new TestListener();
		instance.addListener(tl);
		Listener [] all = instance.getListeners("TestListener");
		Assert.assertEquals(1, all.length);
		instance.removeListener(tl);
		all = instance.getListeners("TestListener");
		Assert.assertEquals(0, all.length);
	}

	public void testGetListeners() {
		// All the same name...
		ListenerStore instance = ListenerStore.getInstance();
		TestListener [] tl = new TestListener[10];
		for (int i = 0; i < 10; i++) {
			tl[i] = new TestListener();
			instance.addListener(tl[i]);
		}
		Listener [] all = instance.getListeners("TestListener");
		// Only 1 listener is effectively inserted..
		Assert.assertEquals(1, all.length);
		instance.removeListener(all[0]);
		
		// All different names, but same prefix...
		for (int i = 0; i < 10; i++) {
			tl[i] = new TestListener();
			tl[i].setListenerId("TestListener-"+i);
			instance.addListener(tl[i]);
		}
		all = instance.getListeners("TestListener");
		Assert.assertEquals(10, all.length);
		// remove 'm...
		for (int i = 0; i < 10; i++) {
			instance.removeListener(tl[i]);
		}
		
		// All different names, but different prefix...
		for (int i = 0; i < 10; i++) {
			tl[i] = new TestListener();
			tl[i].setListenerId(i+"-TestListener");
			instance.addListener(tl[i]);
		}
		all = instance.getListeners("TestListener");
		Assert.assertEquals(0, all.length);
		all = instance.getListeners("0-TestListener");
		Assert.assertEquals(1, all.length);
	}

	public void testActivate() {
		ListenerStore instance = ListenerStore.getInstance();
		TestListener [] tl = new TestListener[10];
		for (int i = 0; i < 10; i++) {
			tl[i] = new TestListener();
			tl[i].setListenerId("TestListener-"+i);
			instance.addListener(tl[i]);
		}
		
		// Activate some listeners...
		for (int i = 0; i < 10; i = i+2) {
			instance.activate(tl[i]);
		}
		
		String [] all = instance.getActivatedListeners();
		Assert.assertEquals(5, all.length);
		int allpresent = 0;
		for (int i = 0; i < all.length; i++) {
			if ( all[i].startsWith("TestListener-0") ) {
				allpresent++;
			}
			if ( all[i].startsWith("TestListener-2") ) {
				allpresent++;
			}
			if ( all[i].startsWith("TestListener-4") ) {
				allpresent++;
			}
			if ( all[i].startsWith("TestListener-6") ) {
				allpresent++;
			}
			if ( all[i].startsWith("TestListener-8") ) {
				allpresent++;
			}
			// Assert that hashcode has been added to activated listener.
			Assert.assertTrue(all[i].length() > "TestListener-0-".length());
		}
		Assert.assertEquals(5, allpresent);
	}

	public void testActivateMultipleThreads() throws Exception {
		
		int MAXTHREADS = 100;
		Thread [] threads = new Thread[MAXTHREADS];
		
		final ListenerStore instance = ListenerStore.getInstance();
		final TestListener [] tl = new TestListener[MAXTHREADS];
		
		
		for (int i = 0; i < MAXTHREADS; i++) {
			final int index = i;
			threads[i] = new Thread() {
				public void run() {
					tl[index] = new TestListener();
					tl[index].setListenerId("TestListener-"+index);
					instance.addListener(tl[index]);
				}
			};
		}
		
		// Fire threads..
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].start();
		}
		
		// Join...
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].join(20000);
		}
		
		Assert.assertEquals(MAXTHREADS, instance.getListeners("TestListener").length);
		
		// Activate some listeners...
		for (int i = 0; i < MAXTHREADS; i = i+2) {
			final int index = i;
			threads[i] = new Thread() {
				public void run() {
					instance.activate(tl[index]);
				}
			};
		}
		
		// Fire threads...
		for (int i = 0; i < MAXTHREADS; i = i+2) {
			threads[i].start();
		}

		// Join...
		for (int i = 0; i < MAXTHREADS; i = i+2) {
			threads[i].join(20000);
		}
		
		
		String [] all = instance.getActivatedListeners();
		Assert.assertEquals(MAXTHREADS/2, all.length);
		int allpresent = 0;
		for (int j = 0; j < MAXTHREADS; j = j + 2) {
			for (int i = 0; i < all.length; i++) {
				System.err.println(all[i]);
				if ( all[i].startsWith("TestListener-" + j + "-") ) {
					allpresent++;
				}
				// Assert that hashcode has been added to activated listener.
				Assert.assertTrue(all[i].length() > "TestListener-0-".length());
			}
		}
		Assert.assertEquals(MAXTHREADS/2, allpresent);
	}
	
//	public void testPerformActivatedListeners() {
//		fail("Not yet implemented");
//	}

}
