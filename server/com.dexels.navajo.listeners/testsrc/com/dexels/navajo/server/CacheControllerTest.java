package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;

public class CacheControllerTest  {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CacheControllerTest.class);
	private String sampleConfig = 
		"<tml>" + "" +
		"<message name=\"Cache\">" +
        "  <message name=\"Entries\" type=\"array\">" + 
		"    <message name=\"Entries\">" + 
        "       <property name=\"Webservice\" value=\"MyTestWebservice\"/>" + 
        "       <property name=\"Timeout\" value=\"6000000\"/>" + 
        "       <property name=\"UserCache\" value=\"false\"/>" +
        "       <property name=\"PersistenceKeys\" value=\"/Request/MyRequestPropertyKey\"/>" + 
        "       <property name=\"CacheKeys\" value=\"/Request/MyResultPropertyKey\"/>" +
        "    </message>" + 
        "    <message name=\"Entries\">" + 
        "       <property name=\"Webservice\" value=\"MyTestWebservice2\"/>" + 
        "       <property name=\"Timeout\" value=\"6000000\"/>" + 
        "       <property name=\"UserCache\" value=\"true\"/>" +
        "       <property name=\"PersistenceKeys\" value=\"/Request/MyRequestPropertyKey\"/>" + 
        "       <property name=\"CacheKeys\" value=\"/Request/MyResultPropertyKey\"/>" +
        "    </message>" + 
        "    <message name=\"Entries\">" + 
        "       <property name=\"Webservice\" value=\"MyTestWebservice3\"/>" + 
        "       <property name=\"Timeout\" value=\"6000000\"/>" + 
        "       <property name=\"UserCache\" value=\"false\"/>" +
        "       <property name=\"PersistenceKeys\" value=\"/Request/MyRequestPropertyKeytje\"/>" + 
        "       <property name=\"CacheKeys\" value=\"/Request/MyResultPropertyKeytje\"/>" +
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
	protected Navajo createTestNavajo(int i) throws Exception {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(doc, "Request");
		doc.addMessage(m);
		Property p = NavajoFactory.getInstance().createProperty(doc, "MyRequestPropertyKey", Property.STRING_PROPERTY, "AAP-"+i, 0, "", "");
		m.addProperty(p);
		return doc;
	}
	
	protected Navajo createTestNavajo() throws Exception {
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
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
	
	@Before
	public void setUp() throws Exception {
		new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
		// injected the dispatcher
		DispatcherFactory.getInstance().setUseAuthorisation(false);
		createCacheConfig();
	}

	@After
	public void tearDown() throws Exception {
		CacheController.getInstance().kill();
		new File("/tmp/cache.xml").delete();
	}

	@Test public void testGetInstance() throws Exception {
		int MAXTHREADS = 100;

		final CacheController [] instances = new CacheController[MAXTHREADS];
		Thread [] threads = new Thread[MAXTHREADS];

		for (int i = 0; i < MAXTHREADS; i++) {
			final int index = i;
			threads[i] = new Thread() {
				@Override
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

	@Test public void testGetCacheKey() throws Exception {
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

	@Test public void testGetExpirationInterval() {
		CacheController cc = CacheController.getInstance();
		Assert.assertEquals(6000000, cc.getExpirationInterval("MyTestWebservice"));
		// Not defined WS:
		Assert.assertEquals(-1, cc.getExpirationInterval("MyTestWebservice6"));
	}

	@Test public void testGetServiceKeys() {
		CacheController cc = CacheController.getInstance();
		Assert.assertEquals("/Request/MyResultPropertyKey", cc.getServiceKeys("MyTestWebservice"));
		// Not defined WS:
		Assert.assertNull(cc.getServiceKeys("dsdss"));
	}

	@Test public void testCachedEntries() {
		CacheController cc = CacheController.getInstance();
		Assert.assertEquals(3, cc.cachedEntries());
	}

}
