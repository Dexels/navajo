package com.dexels.navajo.server.enterprise.scheduler;

import java.lang.reflect.Method;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.version.Version;

public class TaskRunnerFactory {

	private static volatile TaskRunnerInterface instance = null;
	private static Object semaphore = new Object();
	

	private final static Logger logger = LoggerFactory
			.getLogger(TaskRunnerFactory.class);
	
	public static void setInstance(TaskRunnerInterface instance) {
		TaskRunnerFactory.instance = instance;
	}
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	
	public static final TaskRunnerInterface getInstance() {

		if ( instance != null ) {
			return instance;
		}

		if(Version.osgiActive()) {
			logger.warn("No TaskRunner interface bound, Tasks will won't be available.");
			return null;
		}
		synchronized (semaphore) {

			return createTaskRunner();
		}
	}
	@SuppressWarnings("unchecked")
	private static TaskRunnerInterface createTaskRunner() {
		if ( instance == null ) {
			logger.warn("OSGi compatibility problem: This part will fail in OSGi.");
			try {
				Class<ClockInterface> c = (Class<ClockInterface>) Class.forName("com.dexels.navajo.scheduler.Clock");
				ClockInterface dummy = c.getDeclaredConstructor().newInstance();
				Method m = c.getMethod("getInstance",(Class[])null);
				m.invoke(dummy, (Object[])null);
			} catch (Exception e) {

				AuditLog.log("INIT", "WARNING: Clock not available", Level.WARNING);
			}	
			try {
				Class<TaskRunnerInterface> c = (Class<TaskRunnerInterface>) Class.forName("com.dexels.navajo.scheduler.TaskRunner");
				TaskRunnerInterface dummy = c.getDeclaredConstructor().newInstance();
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
				Class<?> c = Class.forName("com.dexels.navajo.workflow.WorkFlowManager");
				Object dummy = c.getDeclaredConstructor().newInstance();
				Method m = c.getMethod("getInstance", (Class[])null);
				m.invoke(dummy, (Object[])null);
			} catch (ClassNotFoundException e) {
				logger.warn("Workflow manager not available");
			} catch (Exception e) {
				logger.error("Error: ", e);
				AuditLog.log("INIT", "WARNING: Workflow not available", Level.WARNING);
			}	
			try {
				Class<GenericThread> c = (Class<GenericThread>) Class.forName("com.dexels.navajo.scheduler.ListenerRunner");
				Object dummy = c.getDeclaredConstructor().newInstance();
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
	
	@SuppressWarnings("unchecked")
	public static final TaskInterface getTaskInstance() {
		try {
		Class<TaskInterface> c = (Class<TaskInterface>) Class.forName("com.dexels.navajo.scheduler.Task");
		TaskInterface tif = (TaskInterface) c.getDeclaredConstructor().newInstance();
		return tif;
		} catch (Exception e) {
			AuditLog.log("INIT", "WARNING: Scheduler not available", Level.WARNING);
			return new DummyTask();
		}	
	}
}
