package com.dexels.navajo.server.enterprise.statistics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import navajo.Version;

import com.dexels.navajo.util.AuditLog;

public class StatisticsRunnerFactory {

	private static volatile StatisticsRunnerInterface instance = null;
	private static Object semaphore = new Object();
	private final static Logger logger = LoggerFactory.getLogger(StatisticsRunnerFactory.class);
	
	@SuppressWarnings("unchecked")
	public static final StatisticsRunnerInterface getInstance(String storePath, Map parameters, String storeClass) {

		if ( instance != null ) {
			return instance;
		} else {
			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						instance = getStatisticsRunnerInstanceOSGi(storePath, parameters, storeClass);
						if(instance!=null) {
							logger.info("Acquiring statistics runner from OSGi services succeeded");
							return instance;
						}
						logger.info("Falling back to old school mode.");
						instance = getStatisticsRunnerInstance(storePath, parameters,
								storeClass);
					} catch (Exception e) {
						AuditLog.log("INIT", "WARNING: StatisticsRunnner not available", e,Level.WARNING);
						instance = new DummyStatisticsRunner();
					}	
				}
				
				return instance;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static StatisticsRunnerInterface getStatisticsRunnerInstance(String storePath,
			Map parameters, String storeClass) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		Class<StatisticsRunnerInterface> c = (Class<StatisticsRunnerInterface>) Class.forName("com.dexels.navajo.server.statistics.StatisticsRunner");
		StatisticsRunnerInterface dummy = c.newInstance();
		Method m = c.getMethod("getInstance", new Class[]{String.class, Map.class, String.class});
		StatisticsRunnerInterface result  = (StatisticsRunnerInterface) m.invoke(dummy, new Object[]{storePath, parameters, storeClass});
		return result;
	}
	
	private static StatisticsRunnerInterface getStatisticsRunnerInstanceOSGi(String storePath,
			Map parameters, String storeClass) throws InvalidSyntaxException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		BundleContext bc = Version.getDefaultBundleContext();
		if(bc==null) {
			return null;
		}
		Collection<ServiceReference<StatisticsRunnerInterface>> c = bc.getServiceReferences(StatisticsRunnerInterface.class, "(threadClass=com.dexels.navajo.server.statistics.StatisticsRunner)");
		if(c.isEmpty()) {
			return null;
		}
		ServiceReference<StatisticsRunnerInterface> srir = c.iterator().next();
		StatisticsRunnerInterface sri = bc.getService(srir);
		if(sri==null) {
			return null;
		}
		sri.initialize(storePath, parameters, storeClass);
		return sri;
	}
	
	public static final void shutdown() {
		if(instance==null) {
			return;
		}
		instance = null;
	}
	
}
