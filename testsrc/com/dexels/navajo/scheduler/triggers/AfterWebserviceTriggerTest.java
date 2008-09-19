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
import com.dexels.navajo.server.TestDispatcher;
import com.dexels.navajo.server.TestNavajoConfig;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AfterWebserviceTriggerTest extends TestCase implements NavajoListener {

	boolean receivedEvent = false;
	
	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		receivedEvent = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testActivateTrigger() throws Exception {
		
		Task t = new Task("navajo_test","test","test",null,"navajo:navajo_ping",null);
		t.getTrigger().activateTrigger();
		Assert.assertNotNull(t);
		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, this);
		
		// Run ws navajo_ping.
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "navajo_ping", "test", "test", -1);
		doc.addHeader(h);
		
		Assert.assertFalse(t.isFinished());
		Navajo r = DispatcherFactory.getInstance().handle(doc, true);
		Thread.sleep(2000);
		
		Assert.assertTrue(t.isFinished());
		//System.err.println("r = " + r);
		Assert.assertTrue(receivedEvent);
	}

//	public void testRemoveTrigger() throws Exception {
//		
//		Task t = new Task(null,"","",null,"navajo:navajo_ping",null);
//		t.getTrigger().activateTrigger();
//		Assert.assertNotNull(t);
//		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, this);
//		
//		// Run ws navajo_ping.
//		Navajo doc = NavajoFactory.getInstance().createNavajo();
//		Header h = NavajoFactory.getInstance().createHeader(doc, "navajo_ping", "test", "test", -1);
//		doc.addHeader(h);
//		
//		Navajo r = DispatcherFactory.getInstance().handle(doc, true);
//		//System.err.println("r = " + r);
//		Assert.assertTrue(receivedEvent);
//		receivedEvent = false;
//		
//		t.getTrigger().removeTrigger();
//		
//	}
	
	public void onNavajoEvent(NavajoEvent ne) {
		System.err.println("ne " + ne);
		if ( ne instanceof NavajoResponseEvent ) {
			System.err.println(((NavajoResponseEvent) ne).getAccess().getRpcName());
			receivedEvent = true;
		}
	}
	
	

}
