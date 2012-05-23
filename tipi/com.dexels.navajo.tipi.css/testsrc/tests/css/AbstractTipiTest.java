package tests.css;

import java.io.File;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.headless.HeadlessApplicationInstance;
import com.dexels.navajo.tipi.headless.HeadlessTipiContext;

public class AbstractTipiTest extends TestCase {

	private HeadlessTipiContext myContext = null;

	public HeadlessTipiContext getContext() {
		return myContext;
	}

	public AbstractTipiTest(String name) {
		super(name);
	}

	public void setContext(String definition, File tipiDir, List<TipiExtension> ed) throws Exception {
		setContext(definition, tipiDir, new String[] {},ed);
	}

	public void setContext(String definition, File tipiDir, String[] properties, List<TipiExtension> ed)
			throws Exception {
		
		myContext = (HeadlessTipiContext) HeadlessApplicationInstance
				.initialize(definition, tipiDir, properties, ed);
		System.err.println("Resource loader set: " + tipiDir.getAbsolutePath());
	}

	public void injectEvent(String componentPath, String eventName)
			throws TipiException {
		getContext().getTipiComponentByPath(componentPath).performTipiEvent(
				eventName, null, true);
	}

	public void injectEvent(String componentPath, String eventName,
			Map<String, Object> eventParams, boolean sync) throws TipiException {
		getContext().getTipiComponentByPath(componentPath).performTipiEvent(
				eventName, eventParams, sync);

	}

	public void doTestTipi(String expectInfoBuffer, int waitingTime) {
		try {
			// setName("Monkey");
			// HeadlessTipiContext xxx = (HeadlessTipiContext)
			// HeadlessApplicationInstance.initialize(definition,tipiDir);
			Thread.sleep(waitingTime);
			String xx = myContext.getInfoBuffer();
			assertEquals(xx, expectInfoBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
