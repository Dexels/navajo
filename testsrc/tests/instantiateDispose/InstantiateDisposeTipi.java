package tests.instantiateDispose;

import java.io.*;

import com.dexels.navajo.tipi.testimpl.*;


public class InstantiateDisposeTipi extends AbstractTipiTest {
	public InstantiateDisposeTipi() {
		super("testTipi");
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setContext("tests/instantiateDispose/init", new File("testsrc/tests/instantiateDispose"));
	}

	
	public void testTipi() throws InterruptedException{
		Thread.sleep(500);
		String xx = getContext().getInfoBuffer();
		assertEquals(xx, "instantiateOk\n");

	}
}
