package com.dexels.navajo.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class WorkFlowManager extends GenericThread implements WorkFlowManagerMXBean {

	/**
	 * Public fields for mappable.
	 */
	public WorkFlow [] workflows = null;
	public WorkFlow workflow = null;
	public String workflowId = null;
	
	private static volatile WorkFlowManager instance = null;
	private static Object semaphore = new Object();
	private final ArrayList workflowInstances = new ArrayList();
	private final HashMap workflowDefinitions = new HashMap();
	private static String id = "Navajo WorkFlow Manager";
	
	private String workflowPath = null;
	private String workflowDefinitionPath = null;
	
	private long configTimestamp = -1;
	
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

			// Create workflow definitions dir.
			if ( instance.workflowDefinitionPath == null ) {
				instance.workflowDefinitionPath = Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/workflows/definitions/";
				File f1 = new File(instance.workflowDefinitionPath);
				f1.mkdirs();
				instance.configTimestamp = f1.lastModified();
			}
			
			// Create workflow persistence dir.
			if ( instance.workflowPath == null ) {
				instance.workflowPath = Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/workflows/instances/";
				File f1 = new File(instance.workflowPath);
				f1.mkdirs();
			}
			
			WorkFlowDefinitionReader.initialize(new File(instance.workflowDefinitionPath), instance.workflowDefinitions);
			
			// Revive persisted workflows.
			instance.reviveSavedWorkFlows();
			
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Started workflow process $Id$");
			return instance;
		}
	}
	
	public boolean persistWorkFlow(WorkFlow wf) {
		
		try {
			File f =  new File(workflowPath, wf.getDefinition()+wf.getMyId());
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
	}
	
	public void addWorkFlow(WorkFlow wf) {
		workflowInstances.add(wf);
	}
	
	public void removeWorkFlow(WorkFlow wf) {
		workflowInstances.remove(wf);
	}
	
	public void worker() {
        // Check whether definition has been added.
		if ( isConfigModified() ) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, "Workflow definition change detected");
			WorkFlowDefinitionReader.initialize(new File(workflowDefinitionPath), workflowDefinitions);
			setConfigTimeStamp();
		}
	}

	public WorkFlow[] getWorkflows() {
		WorkFlowManager mng = WorkFlowManager.getInstance();
		ArrayList wfList = new ArrayList(mng.workflowInstances);
		workflows = new WorkFlow[wfList.size()];
		workflows = (WorkFlow []) wfList.toArray(workflows);
		return workflows;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public WorkFlow getWorkflow() throws UserException {
		if ( workflowId == null ) {
			throw new UserException(-1, "Set workflow id before retrieving workflow instance");
		}
		WorkFlowManager mng = WorkFlowManager.getInstance();
		ArrayList wfList = new ArrayList(mng.workflowInstances);
		for (int i = 0; i < wfList.size(); i++ ) {
			if ( ((WorkFlow) wfList.get(i)).getMyId().equals(workflowId) ) {
				return (WorkFlow) wfList.get(i);
			}
		}
		return null;
	}
	
	private long getConfigTimeStamp() {
		if (  Dispatcher.getInstance() != null && Dispatcher.getInstance().getNavajoConfig() != null ) {
			java.io.File f = new java.io.File(workflowDefinitionPath);
			if ( f != null && f.exists() ) {
				return f.lastModified();
			}
		}
		return -1;
	}
	
	private void setConfigTimeStamp() {
		configTimestamp = getConfigTimeStamp();
	}
	
	private boolean isConfigModified() {
		if ( configTimestamp != getConfigTimeStamp() && getConfigTimeStamp() != -1 ) {
			return true;
		} else {
			return false;
		}
	}
	
}
