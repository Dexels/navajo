package com.dexels.navajo.server.enterprise.statistics;

import java.lang.reflect.Method;
import java.util.Map;

public class StatisticsRunnerFactory {

	private static volatile StatisticsRunnerInterface instance = null;
	private static Object semaphore = new Object();
	
	public static final StatisticsRunnerInterface getInstance(String storePath, Map parameters, String storeClass) {

		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class c = Class.forName("com.dexels.navajo.server.statistics.StatisticsRunner");
						StatisticsRunnerInterface dummy = (StatisticsRunnerInterface) c.newInstance();
						Method m = c.getMethod("getInstance", new Class[]{String.class, Map.class, String.class});
						instance = (StatisticsRunnerInterface) m.invoke(dummy, new Object[]{storePath, parameters, storeClass});
					} catch (Exception e) {
						System.err.println("WARNING: StatisticsRunnner not available");
						instance = new DummyStatisticsRunner();
					}	
				}
				
				return instance;
			}
		}
	}
	
}
