package tests.cascadinglisteners;

import java.io.*;

import com.dexels.navajo.tipi.testimpl.*;


public class CascadingListeners extends AbstractTipiTest {
	public CascadingListeners() {
		super("testTipi");
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setContext("tests/cascadinglisteners/init", new File("testsrc/tests/cascadinglisteners"));
	}

	public void testTipi() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String xx = getContext().getInfoBuffer();
		assertEquals(xx, "loaded\n");

	}


}
