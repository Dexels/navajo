package tests.cascadinglisteners;

import java.io.File;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;


public class CascadingListeners extends AbstractTipiTest {
	public CascadingListeners() {
		super("testTipi");
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setContext("init", new File("testsrc/tests/cascadinglisteners"));
	}

	public void testTipi() {
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		String xx = getContext().getInfoBuffer();
		assertEquals( "loaded\n",xx);

	}


}
