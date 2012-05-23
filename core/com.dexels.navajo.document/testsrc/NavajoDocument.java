
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestProperty.class, 
	TestSelection.class, 
	TestMessage.class,
	TestNavajo.class})  
public class NavajoDocument  {

  public NavajoDocument(String s) {
  }
  
//  public static junit.framework.Test suite() {
//    TestSuite suite = new TestSuite();
////	System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
//	    suite.addTestSuite(TestProperty.class);
//    suite.addTestSuite(TestSelection.class);
//    suite.addTestSuite(TestMessage.class);
//    suite.addTestSuite(TestNavajo.class);
//    return suite;
//  }
}
