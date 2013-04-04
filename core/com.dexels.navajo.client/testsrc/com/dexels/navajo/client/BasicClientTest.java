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
	public void testSometing() throws Exception {
		System.err.println("=============================== TEST SETUP ============================================");
		myClient = NavajoClientFactory.getClient();
		// Use manual load balancing in order to fully control scenario's to enable/disable servers.
		myClient.setServers(new String[]{"atlas.dexels.com/sportlink/knvb/Postman"});
		myClient.setForceGzip(true);
		System.err.println("=======================================================================================");
		myClient.setUsername("iphone");
		myClient.setPassword("1phone");
		Navajo reply = myClient.doSimpleSend("external/iphone/InitLogin");
		reply.write(System.err);
	}

}
