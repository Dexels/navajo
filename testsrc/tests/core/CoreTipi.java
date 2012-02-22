package tests.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

public class CoreTipi extends AbstractTipiTest {
	public CoreTipi() {
		super("testTipi");
	}

	protected void setUp() throws Exception {
		super.setUp();
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);
		
		setContext("init", new File("testsrc/tests/core"),elist);
		System.err.println("Setup complete");
	}

	public void testTipi() {
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// }
		getContext().shutdown();
		String xx = getContext().getInfoBuffer();
		assertEquals("event1\nevent2\n0.99\n", xx);
		System.err.println("Test ok: "+xx);
	}

}
