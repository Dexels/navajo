/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.impl.apache.test;



import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.client.impl.apache.ApacheNavajoClientImpl;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.runtime.config.TestConfig;

/**
 * Tests to be run an an active cluster of Navajo Instances.
 * 
 * @author arjen
 *
 */
public class BasicClientTest {

	protected static ClientInterface myClient;


	@BeforeClass
	public static void setup() {
		myClient = new ApacheNavajoClientImpl();
		myClient.setAllowCompression(true);
		myClient.setServerUrls(new String[] {TestConfig.NAVAJO_TEST_SERVER.getValue()});
		myClient.setUsername(TestConfig.NAVAJO_TEST_USER.getValue());
		myClient.setPassword(TestConfig.NAVAJO_TEST_PASS.getValue());
		NavajoClientFactory.setDefaultClient(myClient);

	}
	
	@Before
	public void getClient() {
	}
	/**
	 * @throws ClientException 
	 * @throws java.lang.Exception
	 */
	@Test(timeout=10000) @Ignore
	public void testSomething() throws ClientException  {
		long total = 0;
		long start = System.currentTimeMillis();
		Navajo reply = myClient.doSimpleSend(null, "club/InitSearchClubs");
		reply.write(System.err);
		reply.getProperty("ClubSearch/SearchName").setAnyValue("veld%");
		Navajo reply2 = myClient.doSimpleSend(reply,"club/ProcessSearchClubs");
		reply2.write(System.err);
		long time = System.currentTimeMillis() - start;
		total += time;
		System.err.println("Time: " + (total) );
	}

}
