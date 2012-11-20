package tests.css;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiCoreExtension;
import tipi.TipiCssExtension;
import tipi.TipiExtension;


public class CoreTipi extends AbstractTipiTest {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CoreTipi.class);
	
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
		logger.info("Setup complete");
	}

	public void testTipi() {
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// }
		getContext().shutdown();
		String xx = getContext().getInfoBuffer();
		assertEquals("event1\nevent2\n0.99\n", xx);
		logger.info("Test ok: "+xx);
	}

}
