
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.dexels.navajo.events.NavajoEventRegistryTest;
import com.dexels.navajo.events.types.AuditLogEventTest;
import com.dexels.navajo.events.types.CacheExpiryEventTest;
import com.dexels.navajo.events.types.NavajoCompileScriptEventTest;
import com.dexels.navajo.events.types.NavajoEventMapTest;
import com.dexels.navajo.function.FunctionsTest;
import com.dexels.navajo.mapping.compiler.meta.SQLFieldDependencyTest;
import com.dexels.navajo.sharedstore.SharedStoreInterfaceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	NavajoEventRegistryTest.class, 
	AuditLogEventTest.class, 
	NavajoCompileScriptEventTest.class, 
	AuditLogEventTest.class, 
	CacheExpiryEventTest.class, 
	NavajoEventMapTest.class, 
	SharedStoreInterfaceTest.class, 
	SQLFieldDependencyTest.class,
	FunctionsTest.class
	})  
public class Navajo {

	
}
