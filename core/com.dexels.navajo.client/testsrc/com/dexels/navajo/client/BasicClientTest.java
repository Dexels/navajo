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
		System.err.println("=============================== TEST SETUP ============================================");
		myClient = NavajoClientFactory.getClient();
		// Use manual load balancing in order to fully control scenario's to enable/disable servers.
		myClient.setRetryAttempts(0);
		myClient.setServers(new String[]{"source.dexels.com:8183/PostmanLegacy"});

		myClient.setClientCertificate("SunX509","JKS", getClass().getClassLoader().getResourceAsStream("client.jks"), "password".toCharArray());
		myClient.setForceGzip(true);
		myClient.setHttps(true);
		System.err.println("=======================================================================================");
		myClient.setUsername("noot");
		myClient.setPassword("aap");
		Navajo reply = myClient.doSimpleSend("external/TestScript");
		reply.write(System.err);
	}

}
