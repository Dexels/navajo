package com.dexels.navajo.workflow;

import java.util.ArrayList;
import java.util.Random;

import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.scheduler.TaskRunner;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class WorkFlowManager extends GenericThread implements WorkFlowManagerMXBean {

	private static volatile WorkFlowManager instance = null;
	private static Object semaphore = new Object();
	private final ArrayList workflowInstances = new ArrayList();
	private static String id = "Navajo WorkFlow Manager";
	
	protected static final String generateWorkflowId() {
		long l = new Random().nextLong();
		return "workflow-"+l;
	}
	
	public WorkFlowManager() {
		super(id);
	}
	
	public static WorkFlowManager getInstance() {

		if (instance!=null) {
			return instance;
		}

		synchronized (semaphore) {
			if ( instance != null ) {
				return instance;
			}

			instance = new WorkFlowManager();	
			JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, "WorkFlowManager");
			instance.startThread(instance);

			// Create dummy start transition for demo workflow.
			try {
				Transition.createStartTransition("start", "navajo:InitNavajoDemo", "", "demo");
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Started workflow process $Id$");
			return instance;
		}
	}
	
	public void addWorkFlow(WorkFlow wf) {
		workflowInstances.add(wf);
	}
	
	public void worker() {
		// Do something usefull.
	}
}
