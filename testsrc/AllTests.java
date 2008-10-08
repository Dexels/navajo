import junit.framework.*;
import tests.core.*;
import tests.eventinject.*;
import tests.instantiateDispose.*;


public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("All tipi tests");
		suite.addTest(new CoreTipi());
		suite.addTest(new InstantiateDisposeTipi());
		suite.addTest(new EventInjectTipi());
			return suite;
	}

}
