package com.dexels.navajo.client.impl.apache.test;



import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dexels.config.runtime.TestConfig;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.client.impl.apache.ApacheNavajoClientImpl;
import com.dexels.navajo.document.Navajo;

/**
 * Tests to be run an an active cluster of Navajo Instances.
 * 
 * @author arjen
 *
 */
public class BasicClientTest {

	protected static ClientInterface myClient;


	@BeforeClass
	public static void setup() {
		myClient = new ApacheNavajoClientImpl();
		myClient.setAllowCompression(true);
//		myClient.setForceGzip(true);
		myClient.setServerUrls(new String[] {TestConfig.NAVAJO_TEST_SERVER.getValue()});
		myClient.setUsername(TestConfig.NAVAJO_TEST_USER.getValue());
		myClient.setPassword(TestConfig.NAVAJO_TEST_PASS.getValue());
		NavajoClientFactory.setDefaultClient(myClient);

	}
	
	@Before
	public void getClient() {
	}
	/**
	 * @throws java.lang.Exception
	 */
	@Test(timeout=10000)
	public void testSomething() throws Exception {
//		System.err.println("=============================== TEST SETUP ============================================");
//		myClient = NavajoClientFactory.getClient();
//		// Use manual load balancing in order to fully control scenario's to enable/disable servers.
//		myClient.setRetryAttempts(0);
//		myClient.setServers(new String[]{"source.dexels.com:8183/PostmanLegacy"});
////		myClient.setServers(new String[]{"source.dexels.com/test/Postman"});
//		
////		myClient.setClientCertificate("SunX509","JKS", getClass().getClassLoader().getResourceAsStream("client.jks"), "password".toCharArray());
//		myClient.setHttps(true);
//		System.err.println("=======================================================================================");
//		myClient.setUsername("noot");
//		myClient.setPassword("aap");
//		myClient = NavajoClientFactory.getClient();
//		myClient.setAllowCompression(false);
//		myClient.setUsername("ROOT");
//		myClient.setPassword("ROOT");
//		myClient.setServerUrl("http://localhost:9090/stream/KNVB");
		long total = 0;
		long start = System.currentTimeMillis();
		Navajo reply = myClient.doSimpleSend(null, "club/InitSearchClubs");
		reply.write(System.err);
		reply.getProperty("ClubSearch/SearchName").setAnyValue("veld%");
		Navajo reply2 = myClient.doSimpleSend(reply,"club/ProcessSearchClubs");
		reply2.write(System.err);
		long time = System.currentTimeMillis() - start;
		total += time;
		System.err.println("Time: " + (total) );
	}

}
