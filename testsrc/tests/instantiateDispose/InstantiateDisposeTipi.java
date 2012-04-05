package tests.instantiateDispose;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

public class InstantiateDisposeTipi extends AbstractTipiTest {

	@Before
	public void setUp() throws Exception {
		List<TipiExtension> elist = new ArrayList<TipiExtension>();
		TipiExtension ed = new TipiCoreExtension();
		ed.loadDescriptor();
		elist.add(ed);

		setContext("init", new File("testsrc/tests/instantiateDispose"),elist);
	}

	public void testTipi() throws InterruptedException {
		String xx = getContext().getInfoBuffer();
		assertEquals("instantiateOk\n", xx);

	}
}
