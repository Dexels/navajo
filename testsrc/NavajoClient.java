

import junit.framework.Test;
import junit.framework.TestSuite;

import com.dexels.navajo.client.BasicNavajoServerTests;
import com.dexels.navajo.client.NavajoScriptingTests;

public class NavajoClient {

	public static Test suite() {
		TestSuite suite = new TestSuite("Integration tests for com.dexels.navajo");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(NavajoScriptingTests.class);
//		suite.addTestSuite(testClass)
		suite.addTestSuite(BasicNavajoServerTests.class);
		
		//$JUnit-END$
		return suite;
	}

}
