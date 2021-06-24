/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.queue;

import java.lang.reflect.Method;

import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import navajocore.Version;


public class RequestResponseQueueFactory {

	private static volatile RequestResponseQueueInterface instance = null;
	private static Object semaphore = new Object();
	
	private static final Logger logger = LoggerFactory
			.getLogger(RequestResponseQueueFactory.class);
	
	public static RequestResponseQueueInterface getInstance() {
		if(Version.osgiActive()) {
			return getOSGiRequestResponseQueue();
		}
		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						
						@SuppressWarnings("unchecked")
						Class<RequestResponseQueueInterface> c = (Class<RequestResponseQueueInterface>) Class.forName("com.dexels.navajo.adapter.queue.RequestResponseQueue");
						RequestResponseQueueInterface dummy = c.getDeclaredConstructor().newInstance();
						Method m = c.getMethod("getInstance", (Class[])null);
						instance = (RequestResponseQueueInterface) m.invoke(dummy,(Object[]) null);
					} catch (Exception e) {
						logger.error("Error getting requestresponse queue:",e);
						instance = new DummyRequestResponseQueue();
					}	
				}
				
				return instance;
			}
		}
		
	}
	
	private static RequestResponseQueueInterface getOSGiRequestResponseQueue() {
		ServiceReference<RequestResponseQueueInterface> sr = Version.getDefaultBundleContext().getServiceReference(RequestResponseQueueInterface.class);
		if(sr==null) {
			logger.warn("No RequestResponseQueueInterface implementation found");
			return null;
		}
		RequestResponseQueueInterface result = Version.getDefaultBundleContext().getService(sr);
		Version.getDefaultBundleContext().ungetService(sr);
		return result;
	}

}
