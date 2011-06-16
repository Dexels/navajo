package tests.scripting;

import java.io.File;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

public class ScriptingTipi extends AbstractTipiTest {
	public ScriptingTipi() {
		super("testTipi");
	}

	protected void setUp() throws Exception {
		super.setUp();
		setContext("init", new File("testsrc/tests/scripting"));
		System.err.println("Setup complete");
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
