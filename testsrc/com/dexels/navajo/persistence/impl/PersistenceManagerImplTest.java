package com.dexels.navajo.persistence.impl;


import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.persistence.Constructor;
import com.dexels.navajo.persistence.Persistable;
import com.dexels.navajo.persistence.PersistenceManagerFactory;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestNavajoConfig;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

class ConstructorTest implements Constructor {

	private String myId;
	
	public ConstructorTest(String myId) {
		this.myId = myId;
	}
	
	public Persistable construct() throws Exception {
		
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message msg = NavajoFactory.getInstance().createMessage(n, myId);
		n.addMessage(msg);
		
		return n;
		
	}
	
}

public class PersistenceManagerImplTest extends TestCase {

	private SharedStoreInterface si;
	private PersistenceManagerImpl pi = null;
	@Before
	public void setUp() throws Exception {
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
		si = SharedStoreFactory.getInstance();
		pi = (PersistenceManagerImpl) PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", null);
	}

	private void deleteFiles(File f) {
		if ( f.isFile() ) {
			f.delete();
		}
		if ( f.isDirectory() ) {
			File [] children = f.listFiles();
			for (int i = 0; i < children.length; i++) {
				deleteFiles(children[i]);
			}
			f.delete();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		File f = new File("/tmp/sharedstore");
		deleteFiles(f);
		SharedStoreFactory.clear();
	}
	
	public void testSimple() throws Exception {
		Navajo p = (Navajo) pi.get(new ConstructorTest("1234"), "aap.noot", "aap/noot", 10000, true);
		Assert.assertNotNull(p);
		if ( p != null ) {
			Assert.assertNotNull(p.getMessage("1234"));
		}
	}
	
	public void testClearCacheKey() throws Exception {
		Navajo p = (Navajo) pi.get(new ConstructorTest("1234"), "aap.noot-23232", "aap/noot", 10000, true);
		Assert.assertNotNull(p);
		p.write(System.err);
		pi.setKey("aap/noot");
		pi.clearCache();
		
		boolean o = si.exists("navajocache/aap", "noot");
		Assert.assertFalse(o);
		
	}
	
	public void testCached1() throws Exception {
		Navajo p = (Navajo) pi.get(new ConstructorTest("12345"), "aap.noot", "aap/noot", 10000, true);
		boolean b = pi.isCached("aap/noot", "");
		Assert.assertTrue(b);
	}
	
	public void testCached2() throws Exception {
		Navajo p = (Navajo) pi.get(new ConstructorTest("12345"), "aap.noot", "aap/noot", 10000, true);
		pi.setKey("aap/noot");
		pi.clearCache();
		boolean b = pi.isCached("aap/noot", "");
		Assert.assertFalse(b);
	}
	
	public void testRead() throws Exception {
		Navajo p = (Navajo) pi.get(new ConstructorTest("123456"), "aap.noot", "aap/noot", 10000, true);
		Navajo n = (Navajo) pi.read("aap.noot", "aap/noot", 213232);
		Assert.assertNotNull(n);
		if ( n != null ) {
			Assert.assertNotNull(n.getMessage("123456"));
		}
	}

}
