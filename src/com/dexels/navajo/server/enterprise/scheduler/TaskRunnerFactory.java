package com.dexels.navajo.server.enterprise.scheduler;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.dexels.navajo.util.AuditLog;

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
					AuditLog.log("INIT", "WARNING: Clock not available", Level.WARNING);
				}	
				try {
					Class c = Class.forName("com.dexels.navajo.scheduler.TaskRunner");
					TaskRunnerInterface dummy = (TaskRunnerInterface) c.newInstance();
					Method m = c.getMethod("getInstance", null);
					instance = (TaskRunnerInterface) m.invoke(dummy, null);
				} catch (Exception e) {
					//e.printStackTrace(System.err);
					AuditLog.log("INIT", "WARNING: Scheduler not available", Level.WARNING);
					instance = new DummyTaskRunner();
				}	
				try {
					Class c = Class.forName("com.dexels.navajo.workflow.WorkFlowManager");
					Object dummy = c.newInstance();
					Method m = c.getMethod("getInstance", null);
					m.invoke(dummy, null);
				} catch (Exception e) {
					//e.printStackTrace(System.err);
					AuditLog.log("INIT", "WARNING: Clock not available", Level.WARNING);
				}	
				try {
					Class c = Class.forName("com.dexels.navajo.scheduler.ListenerRunner");
					Object dummy = c.newInstance();
					Method m = c.getMethod("getInstance", null);
					m.invoke(dummy, null);
				} catch (Exception e) {
					//e.printStackTrace(System.err);
					AuditLog.log("INIT", "WARNING: Listener Runner not available", Level.WARNING);
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
			AuditLog.log("INIT", "WARNING: Scheduler not available", Level.WARNING);
			return new DummyTask();
		}	
	}
}
