/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
