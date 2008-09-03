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
import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

/**
 * @author arjen
 *
 */
public class NavajoTest extends TestCase {

	ClientInterface myClient;
	
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
	
	public void testDisabledServer() throws Exception {
		// Disable server first....
		myClient.doSimpleSend("navajo/InitDisableServer");
		
		// Call service.
		Navajo n = myClient.doSimpleSend("InitUnit");
		Assert.assertNotNull(n.getMessage("ConditionErrors"));
		Assert.assertNotNull(n.getProperty("ConditionErrors@0/Id"));
		Assert.assertEquals(n.getProperty("ConditionErrors@0/Id").getValue(), "4444");
		
		// Enable server again....
		myClient.doSimpleSend("navajo/InitEnableServer");
		n = myClient.doSimpleSend("InitUnit");
		Assert.assertNull(n.getMessage("ConditionErrors"));
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

}
