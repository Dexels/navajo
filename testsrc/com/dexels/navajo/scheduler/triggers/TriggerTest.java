package com.dexels.navajo.scheduler.triggers;

import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.server.Access;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TriggerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testParseTrigger0() {
		boolean failure = false;
		try {
			Trigger t = Trigger.parseTrigger("");
		} catch (Exception e) {
			failure = true;
		}
		Assert.assertTrue(failure);
		
		failure = false;
		try {
			Trigger t = Trigger.parseTrigger(null);
		} catch (Exception e) {
			failure = true;
		}
		Assert.assertTrue(failure);
		
		failure = false;
		try {
			Trigger t = Trigger.parseTrigger("bogustrigger:bogus");
		} catch (Exception e) {
			failure = true;
		}
		Assert.assertTrue(failure);
		
		failure = false;
		try {
			Trigger t = Trigger.parseTrigger("navajobogus:bogus");
		} catch (Exception e) {
			failure = true;
		}
		Assert.assertTrue(failure);
	}
	
	public void testParseTriggerNavajoAfter() throws Exception {
		// Navajo after WS trigger.
		Trigger t = Trigger.parseTrigger("navajo:aap");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), AfterWebserviceTrigger.class);		
	}
	
	public void testParseTriggerNavajoBefore() throws Exception {
		// Navajo after WS trigger.
		Trigger t = Trigger.parseTrigger("beforenavajo:aap");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), BeforeWebserviceTrigger.class);		
	}
	
	public void testParseTriggerTimeTrigger() throws Exception {
		Trigger t = Trigger.parseTrigger("time:2008|06|17|12|00|00");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), TimeTrigger.class);		
	}
	
	public void testParseTriggerOffsetTimeTrigger() throws Exception {
		Trigger t = Trigger.parseTrigger("offsettime:20h");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), TimeTrigger.class);	
		
		t = Trigger.parseTrigger("offsettime:20m");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), TimeTrigger.class);	
		
		t = Trigger.parseTrigger("offsettime:20d");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), TimeTrigger.class);	
	}

	public void testParseTriggerAfterTask() throws Exception {
		Trigger t = Trigger.parseTrigger("aftertask:343");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), AfterTaskTrigger.class);	
	}
	
	public void testParseTriggerJabber() throws Exception {
		Trigger t = Trigger.parseTrigger("jabber:type=[presence(unavailable)|message],from=*");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), JabberTrigger.class);	
	}
	
	public void testParseTriggerServerEvent() throws Exception {
		Trigger t = Trigger.parseTrigger("serverevent:shutdown");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), NavajoEventTrigger.class);
	}
	
	public void testParseTriggerImmediate() throws Exception {
		Trigger t = Trigger.parseTrigger("immediate:");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), ImmediateTrigger.class);
	}
	
	public void testSetSwapInOut() throws Exception {
		Trigger t = Trigger.parseTrigger("navajo:aap");
		t.setSwapInOut(true);
		Assert.assertTrue(t.swapInOut());
		t.setSwapInOut(false);
		Assert.assertFalse(t.swapInOut());
	}

	public void testSetAccess() throws Exception {
		Trigger t = Trigger.parseTrigger("navajo:aap");
		Access a = new Access();
		t.setAccess(a);
		Assert.assertNotNull(t.getAccess());
		Assert.assertEquals(a.hashCode(), t.getAccess().hashCode());
	}

	public void testGetOwnerHost() throws Exception {
		Trigger t = Trigger.parseTrigger("navajo:aap");
		Assert.assertNull(t.getOwnerHost());
	}

	public void testGetListenerIdNullTask() throws Exception {
		Trigger t = Trigger.parseTrigger("navajo:aap");
		Assert.assertEquals("com.dexels.navajo.scheduler.triggers.AfterWebserviceTrigger-null", t.getListenerId());
	}
	
	public void testGetListenerIdWithTask() throws Exception {
		Trigger t = Trigger.parseTrigger("navajo:aap");
		Task task = new Task();
		task.setId("12345");
		t.setTask(task);
		Assert.assertEquals("com.dexels.navajo.scheduler.triggers.AfterWebserviceTrigger-12345", t.getListenerId());
	}

	public void testClone() throws Exception {
		Trigger t = Trigger.parseTrigger("navajo:aap");
		Trigger t2 = t.clone();
		
		Assert.assertEquals(t2.getClass(), AfterWebserviceTrigger.class);
		Assert.assertEquals(t.getDescription(), t2.getDescription());
		Assert.assertNotSame(t.hashCode(), t2.hashCode());
		
	}

}
