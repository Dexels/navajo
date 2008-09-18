package com.dexels.navajo.events.types;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AuditLogEventTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test() {
		AuditLogEvent aul = new AuditLogEvent("subsystem", "msg", "level");
		Assert.assertEquals(aul.getSubSystem(), "subsystem");
		Assert.assertEquals(aul.getMessage(), "msg");
		Assert.assertEquals(aul.getLevel(), "level");
	}

}
