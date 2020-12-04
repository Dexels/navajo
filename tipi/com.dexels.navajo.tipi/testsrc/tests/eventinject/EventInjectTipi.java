/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tests.eventinject;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

public class EventInjectTipi extends AbstractTipiTest {

	
	private final static Logger logger = LoggerFactory
			.getLogger(EventInjectTipi.class);
	
	@Before
	public void setUp() throws Exception {
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);

		setContext("init", new File("testsrc/tests/eventinject"),elist);
	}

	@Test
	public void testTipi() throws TipiBreakException, TipiException {
		// Thread.sleep(500);
		logger.info("USERDIR: " + System.getProperty("user.dir"));
		injectEvent("/init/button", "onActionPerformed");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
		}

		assertEquals(getContext().expect(), "event1");
		assertEquals(getContext().expect(), "event2");
		assertEquals(getContext().expect(), "event3");
		assertEquals(getContext().expect(), "event2");
		assertEquals(getContext().expect(), "event3");
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// }
		injectEvent("/init/hbuttonid2", "onActionPerformed");

		assertEquals("event3", getContext().expect());

		assertEquals(null, getContext().expect());

		// injectEvent("/init/button", "onActionPerformed");
		// assertEquals("event1", getContext().expect());

	}

}
