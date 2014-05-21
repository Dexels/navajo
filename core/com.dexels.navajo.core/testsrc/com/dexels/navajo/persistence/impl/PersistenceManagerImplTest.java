package com.dexels.navajo.persistence.impl;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.persistence.Constructor;
import com.dexels.navajo.persistence.Persistable;
import com.dexels.navajo.persistence.PersistenceManagerFactory;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestNavajoConfig;
import com.dexels.navajo.sharedstore.SharedMemoryStore;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

class ConstructorTest implements Constructor {

	private String myId;

	public ConstructorTest(String myId) {
		this.myId = myId;
	}
	
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();
   

	@Override
	public Persistable construct() throws Exception {

		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message msg = NavajoFactory.getInstance().createMessage(n, myId);
		n.addMessage(msg);

		return n;

	}

}

public class PersistenceManagerImplTest {

	private SharedStoreInterface si;
	private PersistenceManagerImpl pi = null;

	@Before
	public void setUp() throws Exception {
		final TestNavajoConfig navajoConfig = new TestNavajoConfig();
		final Dispatcher injectedDispatcher = new Dispatcher(navajoConfig);
//		DispatcherFactory. new DispatcherFactory(injectedDispatcher);
		DispatcherFactory.setInstance(injectedDispatcher);
		injectedDispatcher.setUseAuthorisation(false);
		si = new SharedMemoryStore();
		pi = new PersistenceManagerImpl();
		pi.init();
		navajoConfig.setMyPersistenceManager(pi);
		pi.setSharedStore(si);
	}

//	private void deleteFiles(File f) {
//		if (f.isFile()) {
//			f.delete();
//		}
//		if (f.isDirectory()) {
//			File[] children = f.listFiles();
//			for (int i = 0; i < children.length; i++) {
//				deleteFiles(children[i]);
//			}
//			f.delete();
//		}
//	}

	public void tearDown() throws Exception {
//		File f = new File("/tmp/sharedstore");
//		deleteFiles(f);
//		si.
//		SharedStoreFactory.clear();
	}

	@Test
	public void testSimple() throws Exception {
		Navajo p = (Navajo) pi.get(new ConstructorTest("1234"), "aap.noot",
				"aap/noot", 10000, true);
		Assert.assertNotNull(p);
		Assert.assertNotNull(p.getMessage("1234"));
	}

	@Test
	public void testClearCacheKey() throws Exception {
		Navajo p = (Navajo) pi.get(new ConstructorTest("1234"),
				"aap.noot-23232", "aap/noot", 10000, true);
		Assert.assertNotNull(p);
		p.write(System.err);
		pi.setKey("aap/noot");
		pi.clearCache();

		boolean o = si.exists("navajocache/aap", "noot");
		Assert.assertFalse(o);

	}

	@Test
	public void testCached1() throws Exception {
		pi.get(new ConstructorTest("12345"), "aap.noot", "aap/noot", 10000,
				true);
		boolean b = pi.isCached("aap/noot", "");
		Assert.assertTrue(b);
	}

	@Test
	public void testCached2() throws Exception {
		pi.get(new ConstructorTest("12345"), "aap.noot", "aap/noot", 10000,
				true);
		pi.setKey("aap/noot");
		pi.clearCache();
		boolean b = pi.isCached("aap/noot", "");
		Assert.assertFalse(b);
	}

	@Test
	public void testRead() throws Exception {
		pi.get(new ConstructorTest("123456"), "aap.noot", "aap/noot", 10000,
				true);
		Navajo n = (Navajo) pi.read("aap.noot", "aap/noot", 213232);
		Assert.assertNotNull(n);
		Assert.assertNotNull(n.getMessage("123456"));
	}

}
