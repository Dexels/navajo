package tests.core;

import java.io.*;

import com.dexels.navajo.tipi.testimpl.*;


public class CoreTipi extends AbstractTipiTest {
	public CoreTipi() {
		super("testTipi");
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setContext("tests/core/init", new File("testsrc/tests/core"));
	}

	public void testTipi() throws InterruptedException{
		Thread.sleep(500);
		String xx = getContext().getInfoBuffer();
		assertEquals(xx, "event1\nevent2\n0.99\n");

	}


}
