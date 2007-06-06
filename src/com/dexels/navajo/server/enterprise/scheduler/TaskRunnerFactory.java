package com.dexels.navajo.server.enterprise.scheduler;

import java.lang.reflect.Method;

public class TaskRunnerFactory {

	private static volatile TaskRunnerInterface instance = null;
	private static Object semaphore = new Object();
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	
	public static final TaskRunnerInterface getInstance() {

		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class c = Class.forName("com.dexels.navajo.scheduler.TaskRunner");
						TaskRunnerInterface dummy = (TaskRunnerInterface) c.newInstance();
						Method m = c.getMethod("getInstance", null);
						instance = (TaskRunnerInterface) m.invoke(dummy, null);
					} catch (Exception e) {
						System.err.println("WARNING: Scheduler not available");
						instance = new DummyTaskRunner();
					}	
				}
				
				return instance;
			}
		}
	}
}
