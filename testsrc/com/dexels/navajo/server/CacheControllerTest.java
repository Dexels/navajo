package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;


import junit.framework.Assert;
import junit.framework.TestCase;

public class CacheControllerTest extends TestCase {

	private String sampleConfig = 
		"<tml>" + "" +
		"<message name=\"Cache\">" +
        "  <message name=\"Entries\" type=\"array\">" + 
		"    <message name=\"Entries\">" + 
        "       <property name=\"Webservice\" value=\"MyTestWebservice\"/>" + 
        "       <property name=\"Timeout\" value=\"6000000\"/>" + 
        "       <property name=\"UserCache\" value=\"false\"/>" +
        "       <property name=\"PersistenceKeys\" value=\"/Request/MyRequestPropertyKey\"/>" + 
        "       <property name=\"CacheKeys\" value=\"/Response/MyResultPropertyKey\"/>" +
        "    </message>" + 
        "    <message name=\"Entries\">" + 
        "       <property name=\"Webservice\" value=\"MyTestWebservice2\"/>" + 
        "       <property name=\"Timeout\" value=\"6000000\"/>" + 
        "       <property name=\"UserCache\" value=\"true\"/>" +
        "       <property name=\"PersistenceKeys\" value=\"/Request/MyRequestPropertyKey\"/>" + 
        "       <property name=\"CacheKeys\" value=\"/Response/MyResultPropertyKey\"/>" +
        "    </message>" + 
        "    <message name=\"Entries\">" + 
        "       <property name=\"Webservice\" value=\"MyTestWebservice3\"/>" + 
        "       <property name=\"Timeout\" value=\"6000000\"/>" + 
        "       <property name=\"UserCache\" value=\"false\"/>" +
        "       <property name=\"PersistenceKeys\" value=\"/Request/MyRequestPropertyKeytje\"/>" + 
        "       <property name=\"CacheKeys\" value=\"/Response/MyResultPropertyKeytje\"/>" +
        "    </message>" + 
        "  </message>" +
        "</message>" + 
        "</tml>";
	
	/**
	 * Create a sample request Navajo:
	 * <tml>
	 *     <message name="Request"> 
	 *        <property name="MyRequestPropertyKey" value="AAP"/>
	 *     </message>
	 * </tml>
	 * 
	 * @return
	 * @throws Exception
	 */
	private Navajo createTestNavajo() throws Exception {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(doc, "Request");
		doc.addMessage(m);
		Property p = NavajoFactory.getInstance().createProperty(doc, "MyRequestPropertyKey", Property.STRING_PROPERTY, "AAP", 0, "", "");
		m.addProperty(p);
		return doc;
	}
	
	public void createCacheConfig() {
		Navajo config = NavajoFactory.getInstance().createNavajo(new StringReader(sampleConfig));
		try {
			DispatcherFactory.getInstance().getNavajoConfig().writeConfig("cache.xml", config);
			System.err.println("WROTE CACHE CONFIG.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
		createCacheConfig();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		CacheController.getInstance().kill();
		new File("/tmp/cache.xml").delete();
	}

	public void testGetInstance() throws Exception {
		int MAXTHREADS = 100;

		final CacheController [] instances = new CacheController[MAXTHREADS];
		Thread [] threads = new Thread[MAXTHREADS];

		for (int i = 0; i < MAXTHREADS; i++) {
			final int index = i;
			threads[i] = new Thread() {
				public void run() {
					instances[index] = CacheController.getInstance();
				}
			};
		}

		// Start 'm up.
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].start();
		}

		// Join.
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].join(10000);
		}

		// Asserts.
		Assert.assertNotNull(instances[0]);
		for (int i = 1; i < MAXTHREADS; i++) {
			Assert.assertNotNull(instances[i]);
			Assert.assertEquals(instances[i-1].hashCode(), instances[i].hashCode());
		}
	}

	public void testGetCacheKey() throws Exception {
		CacheController cc = CacheController.getInstance();
		Navajo n = createTestNavajo();
		// standard cached service, not for specific user:
		Assert.assertEquals("MyTestWebservice_AAP", cc.getCacheKey("demo", "MyTestWebservice", n));
		// user specific cache:
		Assert.assertEquals("MyTestWebservice2_demo_AAP", cc.getCacheKey("demo", "MyTestWebservice2", n));
		// undefined web service:
		Assert.assertEquals("-1", cc.getCacheKey("demo", "MyTestWebservice4", n));
		// unknown cache key property, generated key is used:
		Assert.assertTrue(cc.getCacheKey("demo", "MyTestWebservice3", n).startsWith("MyTestWebservice3_"));
		
	}

	public void testGetExpirationInterval() {
		CacheController cc = CacheController.getInstance();
		Assert.assertEquals(6000000, cc.getExpirationInterval("MyTestWebservice"));
		// Not defined WS:
		Assert.assertEquals(-1, cc.getExpirationInterval("MyTestWebservice6"));
	}

	public void testGetServiceKeys() {
		CacheController cc = CacheController.getInstance();
		Assert.assertEquals("/Response/MyResultPropertyKey", cc.getServiceKeys("MyTestWebservice"));
		// Not defined WS:
		Assert.assertNull(cc.getServiceKeys("dsdss"));
	}

	public void testCachedEntries() {
		CacheController cc = CacheController.getInstance();
		Assert.assertEquals(3, cc.cachedEntries());
	}

}
