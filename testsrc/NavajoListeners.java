
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.dexels.navajo.persistence.impl.PersistenceManagerImplTest;
import com.dexels.navajo.server.CacheControllerTest;
import com.dexels.navajo.sharedstore.SharedStoreInterfaceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CacheControllerTest.class, 
	PersistenceManagerImplTest.class, 
	SharedStoreInterfaceTest.class,
	PersistenceManagerImplTest.class})  
public class NavajoListeners {
//
//	public static Test suite() {
//		TribeManagerFactory.useTestVersion();
//		TestSuite suite = new TestSuite("Tests for Navajo Listeners");
//		//$JUnit-BEGIN$
//		suite.addTestSuite(CacheControllerTest.class);
//		suite.addTestSuite(PersistenceManagerImplTest.class);
//		suite.addTestSuite(SharedStoreInterfaceTest.class);
//		suite.addTestSuite(PersistenceManagerImplTest.class);
//		//$JUnit-END$
//		return suite;
//	}

}
