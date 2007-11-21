
import junit.framework.*;

public class NavajoTestSuite extends TestCase {

  public NavajoTestSuite(String s) {
    super(s);
  }

  public static junit.framework.Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(TestProperty.class);
    suite.addTestSuite(TestSelection.class);
    suite.addTestSuite(TestMessage.class);
    suite.addTestSuite(TestNavajo.class);
    return suite;
  }
}
