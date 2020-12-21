/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.dexels.navajo.events.NavajoEventRegistryTest;
import com.dexels.navajo.events.types.AuditLogEventTest;
import com.dexels.navajo.events.types.CacheExpiryEventTest;
import com.dexels.navajo.events.types.NavajoCompileScriptEventTest;
import com.dexels.navajo.events.types.NavajoEventMapTest;
import com.dexels.navajo.mapping.bean.DomainObjectMapperTest;
import com.dexels.navajo.mapping.bean.ServiceMapperTest;
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
	DomainObjectMapperTest.class, 
	ServiceMapperTest.class, 
	SQLFieldDependencyTest.class
	})  
public class Navajo {

	
}
