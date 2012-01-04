package com.dexels.navajo.client;

import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;

/**
 * @author arjen
 *
 */
public class BasicNavajoServerTests extends BasicClientTest {

	
	private final static Logger logger = LoggerFactory
			.getLogger(BasicNavajoServerTests.class);

	public void testAlive() throws Exception {
		Navajo n = myClient.doSimpleSend("navajo_ping");
		Assert.assertNotNull(n.getMessage("ping"));
	}
	
	public void testGarbage() throws Exception {
		Navajo n = myClient.doSimpleSend("navajo_pingetje");
		Assert.assertNull(n.getMessage("ping"));
	}
	
	public void testAsyncService() throws Exception {
		logger.warn("Async deprecated");

	}
	
	public void testAsyncServiceWithSuddenlyUnavailableTribalMember() throws Exception {
		logger.warn("Async deprecated");
	}
	
	public void testDisabledServer() throws Exception {
		// Disable server first....
		myClient.setRetryAttempts(1);
		myClient.doSimpleSend("navajo/InitDisableServer");
		
		// Call service.
		Navajo n = myClient.doSimpleSend("tests/InitUnit");
		Assert.assertNotNull(n.getMessage("ConditionErrors"));
		Assert.assertNotNull(n.getProperty("ConditionErrors@0/Id"));
		Assert.assertEquals(n.getProperty("ConditionErrors@0/Id").getValue(), "4444");
		
		// Enable server again....
		myClient.doSimpleSend("navajo/InitEnableServer");
		n = myClient.doSimpleSend("tests/InitUnit");
		n.write(System.err);
		Assert.assertNull(n.getMessage("ConditionErrors"));
	}
	
	public void testDisabledServerWithStaticLoadBalancing() throws Exception {
		// Disable server first....
		myClient.setLoadBalancingMode(ClientInterface.LBMODE_STATIC_MINLOAD);
		myClient.setCurrentHost("localhost:8080/NavajoServer/Postman");
		myClient.setRetryAttempts(2);
		myClient.doSimpleSend("navajo/InitDisableServer");
		
		// Call service.
		Navajo n = myClient.doSimpleSend("tests/InitUnit");
		Assert.assertNull(n.getMessage("ConditionErrors"));
		Assert.assertNotNull(n.getMessage("UnitMessage"));
		
		// Enable server again....
		myClient.setCurrentHost("localhost:8080/NavajoServer/Postman");
		myClient.doSimpleSend("navajo/InitEnableServer");
		
		n = myClient.doSimpleSend("tests/InitUnit");
		Assert.assertNull(n.getMessage("ConditionErrors"));
		Assert.assertNotNull(n.getMessage("UnitMessage"));
	}
	
	public void testDisabledServerWithDynamicLoadBalancing() throws Exception {
		// Disable server first....
		myClient.setLoadBalancingMode(ClientInterface.LBMODE_DYNAMIC_MINLOAD);
		myClient.setCurrentHost("localhost:8080/NavajoServer/Postman");
		myClient.setRetryAttempts(2);
		myClient.doSimpleSend("navajo/InitDisableServer");
		
		// Call service.
		Navajo n = myClient.doSimpleSend("tests/InitUnit");
		Assert.assertNull(n.getMessage("ConditionErrors"));
		Assert.assertNotNull(n.getMessage("UnitMessage"));
		
		// Enable server again....
		myClient.setCurrentHost("localhost:8080/NavajoServer/Postman");
		myClient.doSimpleSend("navajo/InitEnableServer");
		
		n = myClient.doSimpleSend("tests/InitUnit");
		Assert.assertNull(n.getMessage("ConditionErrors"));
		Assert.assertNotNull(n.getMessage("UnitMessage"));
	}
	
	public void testDisabledServerWithManualLoadBalancing() throws Exception {
		// Disable server first....
		myClient.setLoadBalancingMode(ClientInterface.LBMODE_MANUAL);
		myClient.setCurrentHost("localhost:8080/NavajoServer/Postman");
		myClient.setRetryAttempts(2);
		myClient.doSimpleSend("navajo/InitDisableServer");
		
		// Call service.
		Navajo n = myClient.doSimpleSend("tests/InitUnit");
		Assert.assertNotNull(n.getMessage("ConditionErrors"));
		Assert.assertNull(n.getMessage("UnitMessage"));
		
		// Call service on different server manually.
		myClient.setCurrentHost("localhost:8080/NavajoServer2/Postman");
		n = myClient.doSimpleSend("tests/InitUnit");
	    Assert.assertNull(n.getMessage("ConditionErrors"));
	    Assert.assertNotNull(n.getMessage("UnitMessage"));
			
		// Enable server again....
		myClient.setCurrentHost("localhost:8080/NavajoServer/Postman");
		myClient.doSimpleSend("navajo/InitEnableServer");
		
		n = myClient.doSimpleSend("tests/InitUnit");
		Assert.assertNull(n.getMessage("ConditionErrors"));
		Assert.assertNotNull(n.getMessage("UnitMessage"));
		
	}
	
	public void testShutdown() throws Exception {
		// Async start sleep service to test proper termination...
		logger.warn("Async is deprecated");
//		myClient.doAsyncSend(NavajoFactory.getInstance().createNavajo(), "tests/InitSleep", null, (ConditionErrorHandler) null);
//		myClient.doSimpleSend("navajo/InitShutdownServer");
//		// Call service.
//		Navajo n = myClient.doSimpleSend("tests/InitUnit");
//		Assert.assertNotNull(n.getMessage("ConditionErrors"));
//		Assert.assertNotNull(n.getProperty("ConditionErrors@0/Id"));
//		Assert.assertEquals(n.getProperty("ConditionErrors@0/Id").getValue(), "4444");
	}
	
	public void handleException(Exception e) {
		// TODO Auto-generated method stub
		
	}


	public void serviceStarted(String id) {
		System.err.println("Service started: " + id);
	}

	public void setProgress(String id, int d) {
		System.err.println("Progress: " + id + " = " + d);
		
	}

	public static void main(String [] args) throws Exception {
		BasicNavajoServerTests b = new BasicNavajoServerTests();
		b.setUp();
		b.testAsyncService();
		
	}
}
