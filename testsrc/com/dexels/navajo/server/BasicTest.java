package com.dexels.navajo.server;

import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Navajo;

import junit.framework.TestCase;

public class BasicTest extends TestCase {

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
		myClient.setServers(new String[]{"localhost:8080/NavajoServer/Postman","localhost:8080/NavajoServer2/Postman"});
		myClient.setCurrentHost("localhost:8080/NavajoServer/Postman");
		received = null;
		finished = false;
		System.err.println("=======================================================================================");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
