/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tests.cascadinglisteners;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

public class NonCascadingListeners extends AbstractTipiTest {

	@Before
	public void setUp() throws Exception {
		// new String[]{"-DcascadingLoading=aap"}
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);

		setContext("init", new File("testsrc/tests/cascadinglisteners"),
				new String[] { "-DnoCascadedLoading=true" },elist);
	}

	@Test
	public void testTipi() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		String xx = getContext().getInfoBuffer();
		assertEquals("loaded2\nloaded4\n", xx);

	}

}
