package com.dexels.navajo.server.enterprise.integrity;

import java.lang.reflect.Method;

import navajocore.Version;

import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerFactory {

	private static volatile WorkerInterface instance = null;
	private static Object semaphore = new Object();
	private final static Logger logger = LoggerFactory
			.getLogger(WorkerFactory.class);

	private static WorkerInterface getOSGiIntegrityWorker() {
		ServiceReference<WorkerInterface> sr = Version.getDefaultBundleContext().getServiceReference(WorkerInterface.class);
		if(sr==null) {
			logger.warn("No JabberWorker implementation found");
			return null;
		}
		WorkerInterface result = Version.getDefaultBundleContext().getService(sr);
		Version.getDefaultBundleContext().ungetService(sr);
		return result;
	}

	
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	
	public static final WorkerInterface getInstance() {

		if(Version.osgiActive()) {
			return getOSGiIntegrityWorker();
		}
		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class<? extends WorkerInterface> c = (Class<? extends WorkerInterface>) Class.forName("com.dexels.navajo.integrity.TribalWorker");
						WorkerInterface dummy = c.newInstance();
						Method m = c.getMethod("getInstance", (Class[])null);
						instance = (WorkerInterface) m.invoke(dummy, (Object[])null);
					} catch (Exception e) {
						logger.warn("Could not instantiate integrity worker: ",e);
						instance = new DummyWorker();
					}	
				}
				
				return instance;
			}
		}
	}
}
