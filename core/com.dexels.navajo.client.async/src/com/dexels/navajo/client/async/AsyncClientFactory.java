package com.dexels.navajo.client.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncClientFactory {

    private static Class<ManualAsyncClient> asyncClientClass = null;

    
	private final static Logger logger = LoggerFactory.getLogger(AsyncClientFactory.class);

	
    private AsyncClientFactory() {
        // no instantiation
    }

    public static ManualAsyncClient getManualInstance() {
        synchronized (AsyncClientFactory.class) {
            try {
            	logger.info("Instantiation manual client instance to class: "+asyncClientClass);
                ManualAsyncClient newInstance = asyncClientClass.getDeclaredConstructor().newInstance();
                newInstance.setCloseAfterUse(true);
                logger.info("Instance complete: "+newInstance);
				return newInstance;
            } catch (Throwable e) {
            	logger.error("Error: ", e);
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static void setInstance(Class<?> aClazz) {
    	logger.info("Setting manual client instance to class: "+aClazz);
        asyncClientClass = (Class<ManualAsyncClient>) aClazz;
    }

}
