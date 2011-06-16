import junit.framework.Test;
import junit.framework.TestSuite;
import tests.cascadinglisteners.CascadingListeners;
import tests.cascadinglisteners.NonCascadingListeners;
import tests.core.CoreTipi;
import tests.eventinject.EventInjectTipi;
import tests.instantiateDispose.InstantiateDisposeTipi;

public class Tipi {

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
