package com.dexels.navajo.client;

import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Navajo;

import junit.framework.TestCase;

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
		myClient.setServers(new String[]{"spiritus.dexels.nl:8080/NavajoServerContext/Postman"});
		myClient.setCurrentHost("spiritus.dexels.nl:8080/NavajoServerContext/Postman");
		received = null;
		finished = false;
		System.err.println("=======================================================================================");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
