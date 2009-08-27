
import junit.framework.*;

public class NavajoDocument extends TestCase {

  public NavajoDocument(String s) {
    super(s);
  }

  public static junit.framework.Test suite() {
    TestSuite suite = new TestSuite();
//	System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
	    suite.addTestSuite(TestProperty.class);
    suite.addTestSuite(TestSelection.class);
    suite.addTestSuite(TestMessage.class);
    suite.addTestSuite(TestNavajo.class);
    return suite;
  }
}
