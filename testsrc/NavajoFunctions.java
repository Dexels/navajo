
import junit.framework.Test;
import junit.framework.TestSuite;

import com.dexels.navajo.functions.test.DateAdd;
import com.dexels.navajo.functions.test.StandardFunctionsTest;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

public class NavajoFunctions {

	public static Test suite() {
		TribeManagerFactory.useTestVersion();
		TestSuite suite = new TestSuite("Tests for Navajo Functions");
		//$JUnit-BEGIN$
		suite.addTestSuite(DateAdd.class);
		suite.addTestSuite(StandardFunctionsTest.class);
		//$JUnit-END$
		return suite;
	}

}
