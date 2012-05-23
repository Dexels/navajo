package com.dexels.navajo.events.types;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NavajoCompileScriptEventTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		NavajoCompileScriptEvent ncse = new NavajoCompileScriptEvent(
				"webservice");
		Assert.assertEquals(ncse.getWebservice(), "webservice");
		Assert.assertNotNull(ncse.getEventNavajo());
		if (ncse.getEventNavajo() != null) {
			Assert.assertNotNull(ncse.getEventNavajo().getMessage("__event__"));
			Assert.assertNotNull(ncse.getEventNavajo().getMessage("__event__")
					.getProperty("Webservice"));
			Assert.assertEquals(ncse.getEventNavajo().getMessage("__event__")
					.getProperty("Webservice").getValue(), "webservice");
		}
	}

}
