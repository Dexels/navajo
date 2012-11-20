package com.dexels.navajo.tipi.testimpl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.headless.HeadlessApplicationInstance;
import com.dexels.navajo.tipi.headless.HeadlessTipiContext;
public class AbstractTipiTest  {

	private HeadlessTipiContext myContext = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(AbstractTipiTest.class);
	
	public HeadlessTipiContext getContext() {
		return myContext;
	}



	public void setContext(String definition, File tipiDir, List<TipiExtension> ed) throws Exception {
		setContext(definition, tipiDir, new String[] {},ed);
	}

	public void setContext(String definition, File tipiDir, String[] properties, List<TipiExtension> ed)
			throws Exception {
		
		myContext = (HeadlessTipiContext) HeadlessApplicationInstance
				.initialize(definition, tipiDir, properties, ed);
		logger.info("Resource loader set: " + tipiDir.getAbsolutePath());
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
			logger.error("Error: ",e);
		}
	}
}
