package com.dexels.navajo.jabber;

import com.dexels.navajo.scheduler.triggers.Trigger;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestJabberTrigger extends TestCase {

	public void setUp() {
		
	}
	
	public void testJabberTrigger() throws Exception {
		Trigger.registerTrigger("jabber", com.dexels.navajo.jabber.JabberTrigger.class);
		Trigger t = Trigger.parseTrigger("jabber:type=[presence(unavailable)|message],from=*");
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), Class.forName("com.dexels.navajo.jabber.JabberTrigger"));	
		System.err.println(t.getDescription());
	}
}
