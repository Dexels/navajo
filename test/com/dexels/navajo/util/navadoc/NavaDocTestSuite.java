
package com.dexels.navajo.util.navadoc;

import junit.framework.*;

public class NavaDocTestSuite extends TestCase {

  public NavaDocTestSuite(String s) {
    super(s);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(com.dexels.navajo.util.navadoc.TestNavaDocConfigurator.class);
    suite.addTestSuite(com.dexels.navajo.util.navadoc.TestServicesList.class);
    suite.addTestSuite(com.dexels.navajo.util.navadoc.TestNavaDocTransformer.class);
    suite.addTestSuite(com.dexels.navajo.util.navadoc.TestNavaDoc.class);
    return suite;
  }
}
