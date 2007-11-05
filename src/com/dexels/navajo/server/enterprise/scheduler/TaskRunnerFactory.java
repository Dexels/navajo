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
		} 

		synchronized (semaphore) {

			if ( instance == null ) {
			
				try {
					Class c = Class.forName("com.dexels.navajo.scheduler.Clock");
					ClockInterface dummy = (ClockInterface) c.newInstance();
					Method m = c.getMethod("getInstance", null);
					ClockInterface myClock = (ClockInterface) m.invoke(dummy, null);
				} catch (Exception e) {
					//e.printStackTrace(System.err);
					System.err.println("WARNING: Clock not available");
				}	
				try {
					Class c = Class.forName("com.dexels.navajo.scheduler.TaskRunner");
					TaskRunnerInterface dummy = (TaskRunnerInterface) c.newInstance();
					Method m = c.getMethod("getInstance", null);
					instance = (TaskRunnerInterface) m.invoke(dummy, null);
				} catch (Exception e) {
					//e.printStackTrace(System.err);
					System.err.println("WARNING: Scheduler not available");
					instance = new DummyTaskRunner();
				}	
				try {
					Class c = Class.forName("com.dexels.navajo.workflow.WorkFlowManager");
					Object dummy = c.newInstance();
					Method m = c.getMethod("getInstance", null);
					m.invoke(dummy, null);
				} catch (Exception e) {
					//e.printStackTrace(System.err);
					System.err.println("WARNING: Clock not available");
				}	
				try {
					Class c = Class.forName("com.dexels.navajo.scheduler.ListenerRunner");
					Object dummy = c.newInstance();
					Method m = c.getMethod("getInstance", null);
					m.invoke(dummy, null);
				} catch (Exception e) {
					//e.printStackTrace(System.err);
					System.err.println("WARNING: Listener Runner not available");
				}	
				
			}

			return instance;
		}

	}
	
	public static final TaskInterface getTaskInstance() {
		try {
		Class c = Class.forName("com.dexels.navajo.scheduler.Task");
		TaskInterface tif = (TaskInterface) c.newInstance();
		return tif;
		} catch (Exception e) {
			System.err.println("WARNING: Scheduler not available");
			return new DummyTask();
		}	
	}
}
