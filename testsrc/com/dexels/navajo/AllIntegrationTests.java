package com.dexels.navajo;

import com.dexels.navajo.server.BasicNavajoServerTests;
import com.dexels.navajo.server.NavajoScriptingTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllIntegrationTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Integration tests for com.dexels.navajo");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(NavajoScriptingTests.class);
		suite.addTestSuite(BasicNavajoServerTests.class);
		
		//$JUnit-END$
		return suite;
	}

}
