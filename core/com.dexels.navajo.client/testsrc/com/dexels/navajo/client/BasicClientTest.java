package com.dexels.navajo.client;



import org.junit.Test;

import com.dexels.navajo.document.Navajo;

/**
 * Tests to be run an an active cluster of Navajo Instances.
 * 
 * @author arjen
 *
 */
public class BasicClientTest {

	protected ClientInterface myClient;



	/**
	 * @throws java.lang.Exception
	 */
	@Test
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
		myClient = NavajoClientFactory.getClient();
		myClient.setAllowCompression(false);
		myClient.setUsername("");
		myClient.setPassword("");
		myClient.setServerUrl("http://localhost:9090/stream/KNVB");
		long total = 0;
		long start = System.currentTimeMillis();
		Navajo reply = myClient.doSimpleSend("club/InitSearchClubs");
		reply.write(System.err);
		reply.getProperty("ClubSearch/SearchName").setAnyValue("vel");
		Navajo reply2 = myClient.doSimpleSend(reply,"club/ProcessSearchClubs");
		reply2.write(System.err);
		long time = System.currentTimeMillis() - start;
		total += time;
		System.err.println("Time: " + (time) );
	}

}
