package com.dexels.navajo.server.enterprise.scheduler;

import java.lang.reflect.Method;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.util.AuditLog;

public class TaskRunnerFactory {

	private static volatile TaskRunnerInterface instance = null;
	private static Object semaphore = new Object();
	

	private final static Logger logger = LoggerFactory
			.getLogger(TaskRunnerFactory.class);
	
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
					Method m = c.getMethod("getInstance",(Class[])null);
					ClockInterface myClock = (ClockInterface) m.invoke(dummy, (Object[])null);
				} catch (Exception e) {

					AuditLog.log("INIT", "WARNING: Clock not available", Level.WARNING);
				}	
				try {
					Class c = Class.forName("com.dexels.navajo.scheduler.TaskRunner");
					TaskRunnerInterface dummy = (TaskRunnerInterface) c.newInstance();
					Method m = c.getMethod("getInstance",(Class[]) null);
					instance = (TaskRunnerInterface) m.invoke(dummy, (Object[])null);
				} catch (ClassNotFoundException e) {
					logger.warn("Task runner not available from classpath");
					instance = new DummyTaskRunner();
				} catch (Exception e) {
					logger.error("Task runner not available from classpath",e);
					AuditLog.log("INIT", "WARNING: Task runner not available from classpath", Level.WARNING);
					instance = new DummyTaskRunner();
				}	
				try {
					Class c = Class.forName("com.dexels.navajo.workflow.WorkFlowManager");
					Object dummy = c.newInstance();
					Method m = c.getMethod("getInstance", (Class[])null);
					m.invoke(dummy, (Object[])null);
				} catch (ClassNotFoundException e) {
					logger.warn("Workflow manager not available");
				} catch (Exception e) {
					logger.error("Error: ", e);
					AuditLog.log("INIT", "WARNING: Workflow not available", Level.WARNING);
				}	
				try {
					Class c = Class.forName("com.dexels.navajo.scheduler.ListenerRunner");
					Object dummy = c.newInstance();
					Method m = c.getMethod("getInstance", (Class[])null);
					m.invoke(dummy, (Object[])null);
				} catch (ClassNotFoundException e) {
					logger.warn("Listener Runner not available from classpath");
				} catch (Exception e) {
					logger.error("Error: ", e);
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
