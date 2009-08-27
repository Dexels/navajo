

import junit.framework.Test;
import junit.framework.TestSuite;

import com.dexels.navajo.events.NavajoEventRegistryTest;
import com.dexels.navajo.events.types.AuditLogEventTest;
import com.dexels.navajo.events.types.CacheExpiryEventTest;
import com.dexels.navajo.events.types.NavajoCompileScriptEventTest;
import com.dexels.navajo.events.types.NavajoEventMapTest;
import com.dexels.navajo.mapping.bean.DomainObjectMapperTest;
import com.dexels.navajo.mapping.bean.ServiceMapperTest;
import com.dexels.navajo.mapping.compiler.meta.SQLFieldDependencyTest;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterfaceTest;

public class Navajo {

	public static Test suite() {
		TribeManagerFactory.useTestVersion();
		TestSuite suite = new TestSuite("Tests for Navajo Kernel");
		//$JUnit-BEGIN$
		suite.addTestSuite(NavajoEventRegistryTest.class);
		suite.addTestSuite(AuditLogEventTest.class);
		suite.addTestSuite(NavajoCompileScriptEventTest.class);
		suite.addTestSuite(CacheExpiryEventTest.class);
		suite.addTestSuite(NavajoEventMapTest.class);
		suite.addTestSuite(SharedStoreInterfaceTest.class);
		suite.addTestSuite(DomainObjectMapperTest.class);
		suite.addTestSuite(ServiceMapperTest.class);
		//suite.addTestSuite(CacheControllerTest.class); THIS ONE IS TESTED IN THE PERSISTENCEMANAGERIMPL...
		suite.addTestSuite(SQLFieldDependencyTest.class);
		//$JUnit-END$
		return suite;
	}

}
