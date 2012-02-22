package tests.css;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tipi.TipiCoreExtension;
import tipi.TipiCssExtension;
import tipi.TipiExtension;


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

		TipiCssExtension tipiCss = new TipiCssExtension();
		tipiCss.loadDescriptor();
		elist.add(tipiCss);

		setContext("init", new File("testsrc/tests/css"),elist);
//		getContext().addOptionalInclude(tipiCss);
//		getContext().processRequiredIncludes(tipiCss);
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
