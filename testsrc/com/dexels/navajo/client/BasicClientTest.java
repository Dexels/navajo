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
	public void setUp() throws Exception {
		System.err.println("=============================== TEST SETUP ============================================");
		myClient = NavajoClientFactory.getClient();
		// Use manual load balancing in order to fully control scenario's to enable/disable servers.
		myClient.setLoadBalancingMode(ClientInterface.LBMODE_MANUAL);
		myClient.setServers(new String[]{"localhost:4444/aaap"});
		myClient.setCurrentHost("localhost:4444/aaap");
		received = null;
		finished = false;
		System.err.println("=======================================================================================");
		myClient.setUsername("bert");
		myClient.setPassword("bert");
		myClient.doSimpleSend("Tralalala");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
