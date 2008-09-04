/**
 * 
 */
package com.dexels.navajo.server;

import static org.junit.Assert.*;
import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.ConditionErrorHandler;
import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.client.ServerAsyncListener;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;

/**
 * @author arjen
 *
 */
public class BasicNavajoServerTests extends TestCase implements ServerAsyncListener  {

	ClientInterface myClient;
	
	public boolean finished = false;
	
	private Navajo received;
	private boolean whileRunningAssert = false;
	
	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		myClient = NavajoClientFactory.getClient();
		myClient.setServerUrl("localhost:8080/NavajoServer/Postman");
	}

	public void testAlive() throws Exception {
		Navajo n = myClient.doSimpleSend("navajo_ping");
		Assert.assertNotNull(n.getMessage("ping"));
	}
	
	public void testGarbage() throws Exception {
		Navajo n = myClient.doSimpleSend("navajo_pingetje");
		Assert.assertNull(n.getMessage("ping"));
	}
	
	public void testAsyncService() throws Exception {
		Navajo in = myClient.doSimpleSend("tests/InitAsync");
		myClient.doServerAsyncSend(in, "tests/ProcessAsyncTest", this, "test-client", 1000);
		while (!finished) {
			synchronized (myClient) {
		
			myClient.wait();
			}
		}
		Assert.assertNotNull(received.getMessage("Finished"));
	}
	
	public void testDisabledServer() throws Exception {
		// Disable server first....
		myClient.setRetryAttempts(1);
		myClient.doSimpleSend("navajo/InitDisableServer");
		
		// Call service.
		Navajo n = myClient.doSimpleSend("InitUnit");
		Assert.assertNotNull(n.getMessage("ConditionErrors"));
		Assert.assertNotNull(n.getProperty("ConditionErrors@0/Id"));
		Assert.assertEquals(n.getProperty("ConditionErrors@0/Id").getValue(), "4444");
		
		// Enable server again....
		myClient.doSimpleSend("navajo/InitEnableServer");
		n = myClient.doSimpleSend("InitUnit");
		n.write(System.err);
		Assert.assertNull(n.getMessage("ConditionErrors"));
	}
	
	public void testShutdown() throws Exception {
		// Async start sleep service to test proper termination...
		myClient.doAsyncSend(NavajoFactory.getInstance().createNavajo(), "tests/InitSleep", null, (ConditionErrorHandler) null);
		myClient.doSimpleSend("navajo/InitShutdownServer");
		// Call service.
		Navajo n = myClient.doSimpleSend("InitUnit");
		Assert.assertNotNull(n.getMessage("ConditionErrors"));
		Assert.assertNotNull(n.getProperty("ConditionErrors@0/Id"));
		Assert.assertEquals(n.getProperty("ConditionErrors@0/Id").getValue(), "4444");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	public void handleException(Exception e) {
		// TODO Auto-generated method stub
		
	}

	public void receiveServerAsync(Navajo n, String method, String serverId,
			String clientId) {
		System.err.println("Receive server async: " + method + ", serverId = " + serverId + ", clientId = " + clientId);
		try {
			n.write(System.err);
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		received = n;
		synchronized (myClient) {
			finished = true;
			myClient.notifyAll();
		}
		
	}

	public void serviceStarted(String id) {
		System.err.println("Service started: " + id);
	}

	public void setProgress(String id, int d) {
		System.err.println("Progress: " + id + " = " + d);
		
	}

}
