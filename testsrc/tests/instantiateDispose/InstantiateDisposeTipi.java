package tests.instantiateDispose;

import java.io.File;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

public class InstantiateDisposeTipi extends AbstractTipiTest {
	public InstantiateDisposeTipi() {
		super("testTipi");
	}

	protected void setUp() throws Exception {
		super.setUp();
		setContext("init", new File("testsrc/tests/instantiateDispose"));
	}

	public void testTipi() throws InterruptedException {
		String xx = getContext().getInfoBuffer();
		assertEquals("instantiateOk\n", xx);

	}
}
