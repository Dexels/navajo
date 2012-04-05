package tests.eventinject;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

public class EventInjectTipi extends AbstractTipiTest {

	@Before
	public void setUp() throws Exception {
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);

		setContext("init", new File("testsrc/tests/eventinject"),elist);
	}

	@Test
	public void testTipi() throws TipiBreakException, TipiException {
		// Thread.sleep(500);
		System.err.println("USERDIR: " + System.getProperty("user.dir"));
		injectEvent("/init/button", "onActionPerformed");
		try {
			Thread.sleep(300);
		} catch (InterruptedException e1) {
		}

		assertEquals(getContext().expect(), "event1");
		assertEquals(getContext().expect(), "event2");
		assertEquals(getContext().expect(), "event3");
		assertEquals(getContext().expect(), "event2");
		assertEquals(getContext().expect(), "event3");
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// }
		injectEvent("/init/hbuttonid2", "onActionPerformed");

		assertEquals("event3", getContext().expect());

		assertEquals(null, getContext().expect());

		// injectEvent("/init/button", "onActionPerformed");
		// assertEquals("event1", getContext().expect());

	}

}
