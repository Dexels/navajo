package com.dexels.navajo.events.types;

import java.util.logging.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuditLogEventTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		AuditLogEvent aul = new AuditLogEvent("subsystem", "msg", Level.INFO);
		Assert.assertEquals(aul.getSubSystem(), "subsystem");
		Assert.assertEquals(aul.getMessage(), "msg");
		Assert.assertEquals("INFO", aul.getLevel().getName());
	}

}
