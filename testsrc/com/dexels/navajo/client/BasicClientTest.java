package com.dexels.navajo.client;

import junit.framework.TestCase;

import com.dexels.navajo.document.Navajo;

/**
 * Tests to be run an an active cluster of Navajo Instances.
 * 
 * @author arjen
 *
 */
public class BasicClientTest extends TestCase {

	protected ClientInterface myClient;

	protected boolean finished = false;

	protected Navajo received = null;

	/**
	 * @throws java.lang.Exception
	 */
	public void testSometing() throws Exception {
		System.err.println("=============================== TEST SETUP ============================================");
		myClient = NavajoClientFactory.getClient();
		// Use manual load balancing in order to fully control scenario's to enable/disable servers.
		myClient.setLoadBalancingMode(ClientInterface.LBMODE_MANUAL);
		myClient.setServers(new String[]{"atlas.dexels.com/sportlink/knvb/Postman"});
		myClient.setForceGzip(true);
		received = null;
		finished = false;
		System.err.println("=======================================================================================");
		myClient.setUsername("iphone");
		myClient.setPassword("1phone");
		Navajo reply = myClient.doSimpleSend("external/iphone/InitLogin");
		reply.write(System.err);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
