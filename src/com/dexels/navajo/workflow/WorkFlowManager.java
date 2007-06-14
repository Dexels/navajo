package com.dexels.navajo.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import com.dexels.navajo.adapter.queue.NavajoObjectInputStream;
import com.dexels.navajo.adapter.queue.QueuedAdapter;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.scheduler.TaskRunner;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.enterprise.queue.Queable;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class WorkFlowManager extends GenericThread implements WorkFlowManagerMXBean {

	private static volatile WorkFlowManager instance = null;
	private static Object semaphore = new Object();
	private final ArrayList workflowInstances = new ArrayList();
	private static String id = "Navajo WorkFlow Manager";
	
	private String workflowPath = null;
	
	protected static final String generateWorkflowId() {
		long l = new Random().nextLong();
		return "workflow-"+l;
	}
	
	public WorkFlowManager() {
		super(id);
	}
	
	/**
	 * This method revives all persist workflows after starting the manager.
	 *
	 */
	private void reviveSavedWorkFlows() {
		File workflows = new File(workflowPath);
		File [] files = workflows.listFiles();

		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if ( f.isFile() ) {
				
				ObjectInputStream ois;
				try {
					ois = new ObjectInputStream(new FileInputStream(f));
					WorkFlow wf = (WorkFlow) ois.readObject();
					System.err.println("Resurected workflow: " + ( wf.getDefinition() + "-" + wf.getMyId() ) );
					ois.close();
					addWorkFlow(wf);
					wf.revive();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
			
			// Create workflow persistence dir.
			if ( instance.workflowPath == null ) {
				instance.workflowPath = Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/workflows";
				File f1 = new File(instance.workflowPath);
				f1.mkdir();
			}
			
			// Revive persisted workflows.
			instance.reviveSavedWorkFlows();
			
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Started workflow process $Id$");
			return instance;
		}
	}
	
	public boolean persistWorkFlow(WorkFlow wf) {
		
		try {
			File f =  new File(workflowPath, wf.getDefinition()+wf.getMyId());
			System.err.println("Persisted workflow: " + wf.getDefinition()+wf.getMyId() );
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(wf);
			oos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void removePersistedWorkFlow(WorkFlow wf) {
		File f =  new File(workflowPath, wf.getDefinition()+wf.getMyId());
		f.delete();
		System.err.println("Removed workflow: " + wf.getDefinition()+wf.getMyId() );
	}
	
	public void addWorkFlow(WorkFlow wf) {
		workflowInstances.add(wf);
	}
	
	public void removeWorkFlow(WorkFlow wf) {
		workflowInstances.remove(wf);
	}
	
	public void worker() {
		// Do something usefull.
	}
}
