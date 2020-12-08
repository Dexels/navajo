/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tests.cascadinglisteners.CascadingListeners;
import tests.cascadinglisteners.NonCascadingListeners;
import tests.core.CoreTipi;
import tests.eventinject.EventInjectTipi;
import tests.instantiateDispose.InstantiateDisposeTipi;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CoreTipi.class, 
	InstantiateDisposeTipi.class,
	EventInjectTipi.class,
	InstantiateDisposeTipi.class,
	CascadingListeners.class,
	NonCascadingListeners.class
	
	})
public class Tipi {

	
}
