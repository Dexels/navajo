package tests.cascadinglisteners;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;
public class CascadingListeners extends AbstractTipiTest {

	@Before
	public void setUp() throws Exception {
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);

		setContext("init", new File("testsrc/tests/cascadinglisteners"),elist);
	}

	@Test
	public void testTipi() {
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// logger.error("Error: ",e);
		// }
		String xx = getContext().getInfoBuffer();
		assertEquals("loaded\n", xx);

	}

}
