package com.dexels.navajo.server.enterprise.statistics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

import navajo.Version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
					logger.info("Getting statistics runner interface with storePath: {} and parameters: {}",storeClass,parameters);
					try {
						instance = getStatisticsRunnerInstanceOSGi(storePath, parameters, storeClass);
						if(instance!=null) {
							logger.info("Acquiring statistics runner from OSGi services succeeded");
							return instance;
						}
						logger.info("Falling back to old school mode.");
						instance = getStatisticsRunnerInstance(storePath, parameters,
								storeClass);
					} catch (ClassNotFoundException e) {
						logger.warn("Statistics runner not available from classpath.");
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
		BundleContext bc = null;
		try {
			bc = Version.getDefaultBundleContext();
		} catch (Throwable t) {
			logger.warn("Could not get OSGi instance for statistics runnner. No OSGi?");
			return null;
		}
		if(bc==null) {
			return null;
		}
		ServiceReference[] c = bc.getServiceReferences(StatisticsRunnerInterface.class.getName(), "(threadClass=com.dexels.navajo.server.statistics.StatisticsRunner)");
		if(c== null ||  c.length==0) {
			return null;
		}
		ServiceReference srir = c[0];
		StatisticsRunnerInterface sri = (StatisticsRunnerInterface)bc.getService(srir);
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
