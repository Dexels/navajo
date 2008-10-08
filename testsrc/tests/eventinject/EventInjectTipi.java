package tests.eventinject;

import java.io.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.testimpl.*;


public class EventInjectTipi extends AbstractTipiTest {
	public EventInjectTipi() {
		super("testTipi");
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setContext("tests/eventinject/init", new File("testsrc/tests/eventinject"));
	}

	public void testTipi() throws InterruptedException, TipiBreakException, TipiException{
//		Thread.sleep(500);
		System.err.println("USERDIR: "+System.getProperty("user.dir"));
		injectEvent("/init/button", "onActionPerformed");
		Thread.sleep(500);
		assertEquals("event1", getContext().expect());
		assertEquals("event2", getContext().expect());
		assertEquals("event3", getContext().expect());
		assertEquals("event2", getContext().expect());
		assertEquals("event3", getContext().expect());
		injectEvent("/init/hbuttonid2", "onActionPerformed");
		Thread.sleep(500);
		assertEquals("event3", getContext().expect());

		assertEquals(null, getContext().expect());

		//		injectEvent("/init/button", "onActionPerformed");
//		assertEquals("event1", getContext().expect());

	}


}
