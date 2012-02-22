package tests.cascadinglisteners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

public class NonCascadingListeners extends AbstractTipiTest {
	public NonCascadingListeners() {
		super("testTipi");
	}

	protected void setUp() throws Exception {
		super.setUp();
		// new String[]{"-DcascadingLoading=aap"}
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);

		setContext("init", new File("testsrc/tests/cascadinglisteners"),
				new String[] { "-DnoCascadedLoading=true" },elist);
	}

	public void testTipi() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		String xx = getContext().getInfoBuffer();
		assertEquals("", xx);

	}

}
