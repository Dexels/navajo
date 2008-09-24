package com.dexels.navajo.persistence.impl;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.persistence.Constructor;
import com.dexels.navajo.persistence.Persistable;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.persistence.PersistenceManagerFactory;
import com.dexels.navajo.server.CacheController;
import com.dexels.navajo.server.CacheControllerTest;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.TestDispatcher;
import com.dexels.navajo.server.TestNavajoConfig;

import junit.framework.Assert;

class TestConstructor1 implements Constructor {

	private Persistable myPersistable;
	
	public TestConstructor1(Persistable p) {
		myPersistable = p;
	}
	
	public Persistable construct() throws Exception {
		Navajo n = (Navajo) myPersistable;
		Property p = NavajoFactory.getInstance().createProperty((Navajo) myPersistable, "MyResultPropertyKey", 
				Property.STRING_PROPERTY, n.getProperty("/Request/MyRequestPropertyKey").getValue(), 0, "", "out");
		n.getMessage("Request").addProperty(p);
		return myPersistable;
	}
	
}

class TestConstructor2 implements Constructor {

	private Persistable myPersistable;
	
	public TestConstructor2(Persistable p) {
		myPersistable = p;
	}
	
	public Persistable construct() throws Exception {
		Navajo n = (Navajo) myPersistable;
		Property p = NavajoFactory.getInstance().createProperty((Navajo) myPersistable, "DuplicateProperty", 
				Property.STRING_PROPERTY, n.getProperty("/Request/MyRequestPropertyKey").getValue(), 0, "", "out");
		n.getMessage("Request").addProperty(p);
		return myPersistable;
	}
	
}

public class PersistenceManagerImplTest extends CacheControllerTest {

	
	protected void setUp() throws Exception {
		
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		PersistenceManagerImpl pm =  (PersistenceManagerImpl) PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
		pm.clearCache();
	}

	public static void main(String [] args) throws Exception {
		PersistenceManagerImplTest pt = new PersistenceManagerImplTest();
		pt.setUp();
		pt.testIsCached();
		pt.tearDown();
	}
	
	public void testGetConstructorStringStringLongBoolean() throws Exception {
		
		Navajo tp = createTestNavajo();
		
		TestConstructor1 tc1 = new TestConstructor1(tp);
		TestConstructor2 tc2 = new TestConstructor2(tp);
		
		PersistenceManager pm =  PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
		
		Navajo tp2 = (Navajo) pm.get(tc1,  "TestKey" , "MyTestWebservice", 434343,  true);
		Navajo tp3 = (Navajo) pm.get(tc2,  "TestKey" , "MyTestWebservice", 434343,  true);
	
		tp2.write(System.err);
		tp3.write(System.err);
		
		Assert.assertNotNull(tp2);
		Assert.assertNotNull(tp3);
		// Even though tp3 use TestConstructor2 which does contain /Request/DuplicateProperty, the cached entry, which
		// does not contain /Request/DuplicateProperty is returned:
		Assert.assertNull(tp3.getProperty("/Request/DuplicateProperty"));
		Assert.assertNotNull(tp2.getMessage("Request"));
		Assert.assertNotNull(tp3.getMessage("Request"));
		
		Navajo tp4 = (Navajo) pm.get(tc2,  "TestKey2" , "MyTestWebservice", 434343,  true);
		// With different cache key TestConstructor2 IS used and /Request/DuplicateProperty must be present:
		Assert.assertNotNull(tp4.getProperty("/Request/DuplicateProperty"));
		
		Navajo tp5 = (Navajo) pm.get(tc2,  "TestKey" , "MyTestWebservice2", 434343,  true);
		// With different webservice and same key TestKey TestConstructor2 IS NOT used and /Request/DuplicateProperty must NOT be present:
		Assert.assertNull(tp5.getProperty("/Request/DuplicateProperty"));
		
	}

	public void testWrite() throws Exception {
		int MAXTHREADS = 1000;
		final PersistenceManagerImpl pm =  (PersistenceManagerImpl) PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
		pm.init();
		Thread [] threads = new Thread[MAXTHREADS];
		final Navajo [] navajos = new Navajo[MAXTHREADS];
		for ( int i = 0; i < MAXTHREADS; i++ ) {
			navajos[i] = createTestNavajo(i);
		}
		
		// With all the same TestKie(!)
		for ( int i = 0; i < MAXTHREADS; i++ ) {
			final int index = i;
			threads[i] = new Thread() {
				public void run() {
					pm.write(navajos[index], "TestKie", "MyTestWebservice");
				}
			};
		}
		
		// Start threads.
		for ( int i = 0; i < MAXTHREADS; i++ ) {
			threads[i].start();
		}
		
		// Join.
		for ( int i = 0; i < MAXTHREADS; i++ ) {
			threads[i].join(10000);
		}
		
		// The 1 cached Navajo must have some value:
		Navajo n = (Navajo) pm.read("TestKie", "MyTestWebservice", 231432432);
		Assert.assertNotNull(n);
		Assert.assertNotNull(n.getProperty("/Request/MyRequestPropertyKey"));
	
	}
	
	public void testWriteMultipleKeys() throws Exception {
		int MAXTHREADS = 1000;
		final PersistenceManagerImpl pm =  (PersistenceManagerImpl) PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
		pm.init();
		Thread [] threads = new Thread[MAXTHREADS];
		final Navajo [] navajos = new Navajo[MAXTHREADS];
		for ( int i = 0; i < MAXTHREADS; i++ ) {
			navajos[i] = createTestNavajo(i);
		}
		
		// With all the same TestKie(!)
		for ( int i = 0; i < MAXTHREADS; i++ ) {
			final int index = i;
			threads[i] = new Thread() {
				public void run() {
					pm.write(navajos[index], "TestKie-"+index, "MyTestWebservice");
				}
			};
		}
		
		// Start threads.
		for ( int i = 0; i < MAXTHREADS; i++ ) {
			threads[i].start();
		}
		
		// Join.
		for ( int i = 0; i < MAXTHREADS; i++ ) {
			threads[i].join(10000);
		}
		
		// All the cached Navajo must be present and have their unique values:
		for ( int i = 0; i < MAXTHREADS; i++ ) {
			Navajo n = (Navajo) pm.read("TestKie-"+i, "MyTestWebservice", 231432432);
			Assert.assertNotNull(n);
			Assert.assertNotNull(n.getProperty("/Request/MyRequestPropertyKey"));
			Assert.assertEquals("AAP-"+i, n.getProperty("/Request/MyRequestPropertyKey").getValue());
		}
	
	}

//	public void testSetConfiguration() {
//		fail("Not yet implemented");
//	}
//
	public void testGetSetKey() {
		final PersistenceManagerImpl pm =  (PersistenceManagerImpl) PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
		pm.setKey("aap");
		Assert.assertEquals("aap", pm.getKey());
		boolean exception = false;
		try {
			pm.setKey(null);
		} catch (Throwable t) {
			exception = true;
		}
		// Assert that exception occurred when trying to set null value.
		Assert.assertTrue(exception);
	}

	public void testIsCached() throws Exception {
		final PersistenceManagerImpl pm =  (PersistenceManagerImpl) PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
		pm.init();
		
		TestConstructor1 tc1 = new TestConstructor1(createTestNavajo());
		Navajo tp2 = (Navajo) pm.get(tc1,  "TestKietje" , "MyTestWebservice", 434343,  true);
		
		Assert.assertTrue(pm.isCached("MyTestWebservice", "AAP"));
		Assert.assertFalse(pm.isCached("MyTestWebservice", "NOOT"));
		Assert.assertFalse(pm.isCached("MyTestWebservice2", "AAP"));
		
	}

	public void testSetDoClear() throws Exception {
		final PersistenceManagerImpl pm =  (PersistenceManagerImpl) PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
		pm.init();
		
		TestConstructor1 tc1 = new TestConstructor1(createTestNavajo());
		Navajo tp2 = (Navajo) pm.get(tc1,  "TestKietje" , "MyTestWebservice", 434343,  true);
		
		Assert.assertTrue(pm.isCached("MyTestWebservice", "AAP"));
		pm.setKey("TestKietje");
		pm.setDoClear(true);
		Assert.assertFalse(pm.isCached("MyTestWebservice", "AAP"));
		
	}

//	public void testGetHitratio() {
//		fail("Not yet implemented");
//	}
//
//	public void testSetServiceKeyValues() {
//		fail("Not yet implemented");
//	}
//
//	public void testGetConstructorStringLongBoolean() {
//		fail("Not yet implemented");
//	}
//
//	public void testClearCache() {
//		fail("Not yet implemented");
//	}

}
