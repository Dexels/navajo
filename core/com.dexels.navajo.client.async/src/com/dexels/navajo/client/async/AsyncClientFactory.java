/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncClientFactory {

    private static Class<ManualAsyncClient> asyncClientClass = null;

    
	private static final Logger logger = LoggerFactory.getLogger(AsyncClientFactory.class);

	
    private AsyncClientFactory() {
        // no instantiation
    }

    public static ManualAsyncClient getManualInstance() {
        synchronized (AsyncClientFactory.class) {
            try {
            	logger.info("Instantiation manual client instance to class: {}",asyncClientClass);
                ManualAsyncClient newInstance = asyncClientClass.getDeclaredConstructor().newInstance();
                newInstance.setCloseAfterUse(true);
                logger.info("Instance complete: {}", newInstance);
				return newInstance;
            } catch (Exception e) {
            	logger.error("Error: ", e);
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static void setInstance(Class<?> aClazz) {
    	logger.info("Setting manual client instance to class: {}", aClazz);
        asyncClientClass = (Class<ManualAsyncClient>) aClazz;
    }

}
