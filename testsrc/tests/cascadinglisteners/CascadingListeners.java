package tests.cascadinglisteners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

public class CascadingListeners extends AbstractTipiTest {
	public CascadingListeners() {
		super("testTipi");
	}

	protected void setUp() throws Exception {
		super.setUp();
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);

		setContext("init", new File("testsrc/tests/cascadinglisteners"),elist);
	}

	public void testTipi() {
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		String xx = getContext().getInfoBuffer();
		assertEquals("loaded\n", xx);

	}

}
