/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CacheExpiryEventTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		CacheExpiryEvent ncse = new CacheExpiryEvent("webservice", "apenoot");
		assertEquals("webservice", ncse.getWebservice());
		assertEquals("apenoot", ncse.getKey());
		assertNotNull(ncse.getEventNavajo());
		if (ncse.getEventNavajo() != null) {
			assertNotNull(ncse.getEventNavajo().getMessage("__event__"));
			assertNotNull(ncse.getEventNavajo().getMessage("__event__")
					.getProperty("Webservice"));
			assertEquals(ncse.getEventNavajo().getMessage("__event__")
					.getProperty("Webservice").getValue(), "webservice");
			assertNotNull(ncse.getEventNavajo().getMessage("__event__")
					.getProperty("Key"));
			assertEquals(ncse.getEventNavajo().getMessage("__event__")
					.getProperty("Key").getValue(), "apenoot");
		}
	}

}
