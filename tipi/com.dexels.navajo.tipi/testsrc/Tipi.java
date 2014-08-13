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
