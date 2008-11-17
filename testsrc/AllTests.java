import junit.framework.*;
import tests.cascadinglisteners.*;
import tests.core.*;
import tests.eventinject.*;
import tests.instantiateDispose.*;


public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("All tipi tests");
		suite.addTest(new CoreTipi());
		suite.addTest(new InstantiateDisposeTipi());
		suite.addTest(new EventInjectTipi());
		suite.addTest(new CascadingListeners());
		suite.addTest(new NonCascadingListeners());
			return suite;
	}

}
