/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tests.scripting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

public class ScriptingTipi extends AbstractTipiTest {
	
	private final static Logger logger = LoggerFactory
			.getLogger(ScriptingTipi.class);
	@Before
	public void setUp() throws Exception {
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);

		setContext("init", new File("testsrc/tests/scripting"),elist);
		logger.info("Setup complete");
	}

	public void testTipi() {
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// }
		getContext().shutdown();
		// String xx = getContext().getInfoBuffer();
		// assertEquals("event1\nevent2\n0.99\n",xx);

	}

}
