package com.dexels.navajo.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public final class WorkFlowManager extends GenericThread implements WorkFlowManagerMXBean {

	/**
	 * Public fields for mappable.
	 */
	public WorkFlow [] workflows = null;
	public WorkFlowDefinition [] definitions = null;
	public WorkFlow workflow = null;
	public String workflowId = null;
	public String workflowDef = null;
	
	private static volatile WorkFlowManager instance = null;
	private static Object semaphore = new Object();
	private final ArrayList<WorkFlow> workflowInstances = new ArrayList<WorkFlow>();
	private final HashMap<String,WorkFlowDefinition> workflowDefinitions = new HashMap<String,WorkFlowDefinition>();
	private static String id = "Navajo WorkFlow Manager";
	public static String VERSION = "$Id$";
	
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
			
			// Start worker thread at last...
			instance.startThread(instance);
			
			AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, "Started workflow process $Id$");
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
		WorkFlowDefinition wdf = workflowDefinitions.get(wf.getDefinition());
		wdf.instances++;
	}
	
	public void removeWorkFlow(WorkFlow wf) {
		workflowInstances.remove(wf);
		WorkFlowDefinition wdf = workflowDefinitions.get(wf.getDefinition());
		wdf.instances--;
	}
	
	public void worker() {
        // Check whether definition has been added.
		if ( isConfigModified() ) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, "Workflow definition change detected");
			setConfigTimeStamp();
			WorkFlowDefinitionReader.initialize(new File(workflowDefinitionPath), workflowDefinitions);	
		}
	}

	/**
	 * Get all workflow instances.
	 * 
	 * @return
	 */
	public WorkFlow[] getWorkflows() {
		if ( workflowDef != null ) {
			return getWorkflows(workflowDef);
		}
		WorkFlowManager mng = WorkFlowManager.getInstance();
		ArrayList<WorkFlow> wfList = new ArrayList<WorkFlow>(mng.workflowInstances);
		workflows = new WorkFlow[wfList.size()];
		workflows = (WorkFlow []) wfList.toArray(workflows);
		return workflows;
	}
	
	/**
	 * Get all workflow instances specified by a specific name.
	 * 
	 * @param byName
	 * @return
	 */
	private final WorkFlow[] getWorkflows(String byName) {
		WorkFlowManager mng = WorkFlowManager.getInstance();
		ArrayList<WorkFlow> wfList = new ArrayList<WorkFlow>();
		Iterator<WorkFlow> iter = mng.workflowInstances.iterator();
		while ( iter.hasNext() ) {
			WorkFlow wf = iter.next();
			if ( wf.getDefinition().equals(byName) ) {
				wfList.add(wf);
			}
		}
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
		ArrayList<WorkFlow> wfList = new ArrayList<WorkFlow>(mng.workflowInstances);
		for (int i = 0; i < wfList.size(); i++ ) {
			if (wfList.get(i).getMyId().equals(workflowId) ) {
				return wfList.get(i);
			}
		}
		return null;
	}
	
	private final long getConfigTimeStamp() {
		if (  Dispatcher.getInstance() != null && Dispatcher.getInstance().getNavajoConfig() != null ) {
			java.io.File f = new java.io.File(workflowDefinitionPath);
			if ( f != null && f.exists() ) {
				return f.lastModified();
			}
		}
		return -1;
	}
	
	private final void setConfigTimeStamp() {
		configTimestamp = getConfigTimeStamp();
	}
	
	private final boolean isConfigModified() {
		if ( configTimestamp != getConfigTimeStamp() && getConfigTimeStamp() != -1 ) {
			return true;
		} else {
			return false;
		}
	}

	public WorkFlowDefinition[] getDefinitions() {
		WorkFlowManager mng = WorkFlowManager.getInstance();
		ArrayList<WorkFlowDefinition> wfList = new ArrayList<WorkFlowDefinition>(mng.workflowDefinitions.values());
		definitions = new WorkFlowDefinition[wfList.size()];
		definitions = wfList.toArray(definitions);
		return definitions;
	}

	public void setWorkflowDef(String workflowDef) {
		this.workflowDef = workflowDef;
	}

	public int getDefinitionCount() {
		return workflowDefinitions.size();
	}

	public int getInstanceCount() {
		return workflowInstances.size();
	}

	public void terminate() {
		try {
			JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
		} catch (Throwable e) {
		}
	}
	
	public String getId() {
		return id;
	}

	public String getVERSION() {
		return VERSION;
	}
	

}
