package com.dexels.navajo.workflow;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.tribe.SharedStoreException;
import com.dexels.navajo.tribe.SharedStoreFactory;
import com.dexels.navajo.tribe.SharedStoreInterface;
import com.dexels.navajo.tribe.TribeManager;
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
	private static Object semaphore_instances = new Object();
	private final ArrayList<WorkFlow> workflowInstances = new ArrayList<WorkFlow>();
	private final HashMap<String,WorkFlowDefinition> workflowDefinitions = new HashMap<String,WorkFlowDefinition>();
	private static String id = "Navajo WorkFlow Manager";
	public static final String VERSION = "$Id$";
	
	private String workflowPath = null;
	private String baseWorkflowPath = null;
	private String workflowDefinitionPath = null;
	
	private long configTimestamp = -1;
	private final static Random rand = new Random(System.currentTimeMillis());
	
	protected static final String generateWorkflowId() {
		long l = rand.nextLong();
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

		synchronized (semaphore_instances) {

			SharedStoreInterface ssi = SharedStoreFactory.getInstance();
			String [] files = ssi.getObjects(workflowPath);

			for (int i = 0; i < files.length; i++) {

				try {
					WorkFlow wf = (WorkFlow) ssi.get(workflowPath, files[i]);
					if (!hasWorkflowId(wf.getMyId())) {
						addWorkFlow(wf);
						wf.revive();
					}
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
			// get handle to sharedstore interface.
			SharedStoreInterface ssi = SharedStoreFactory.getInstance();
			
			
			// Create workflow definitions dir.
			if ( instance.workflowDefinitionPath == null ) {
				instance.workflowDefinitionPath = Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/workflows/definitions/";
				File f1 = new File(instance.workflowDefinitionPath);
				if ( !f1.exists() ) {
					f1.mkdirs();
				}
				instance.configTimestamp = f1.lastModified();
			}
			
			// Create workflow persistence dir.
			if ( instance.workflowPath == null ) {
				instance.baseWorkflowPath = "/workflows/instances";
				instance.workflowPath = instance.baseWorkflowPath + "/" + Dispatcher.getInstance().getNavajoConfig().getInstanceName();
				ssi.createParent(instance.workflowPath);
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
	
	/**
	 * This method can be used to take over control of persisted workflow of another server.
	 * 
	 * @param fromServer
	 */
	public void takeOverPersistedWorkFlows(String fromServer) {
		AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, "TakeOverPersistedWorkFlows(" + fromServer + ")");
		String [] persistedWorkflows = SharedStoreFactory.getInstance().getObjects(baseWorkflowPath + "/" + fromServer);
		for (int i = 0; i < persistedWorkflows.length; i++) {
			WorkFlow wf;
			try {
				wf = (WorkFlow) SharedStoreFactory.getInstance().get(baseWorkflowPath + "/" + fromServer, persistedWorkflows[i]);
				AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, "MOVING WORKFLOW: " + wf.getMyId() + " FROM SERVER " + fromServer);
				persistWorkFlow(wf);
				SharedStoreFactory.getInstance().remove(baseWorkflowPath + "/" + fromServer, persistedWorkflows[i]);
			} catch (SharedStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	public boolean persistWorkFlow(WorkFlow wf) {
		synchronized (semaphore_instances) {
			try {
				SharedStoreFactory.getInstance().store(workflowPath, wf.getDefinition() + wf.getMyId(), wf, false, false);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	public void removePersistedWorkFlow(WorkFlow wf) {
		synchronized (semaphore_instances) {
			SharedStoreFactory.getInstance().remove(workflowPath, wf.getDefinition()+wf.getMyId());
		}
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
        // Check whether definition has been added (only the tribal chief processes new workflow definitions).
		if ( isConfigModified() &&  TribeManager.getInstance().getIsChief() )  {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, "Workflow definition change detected");
			setConfigTimeStamp();
			WorkFlowDefinitionReader.initialize(new File(workflowDefinitionPath), workflowDefinitions);	
		}
		// Check whether new workflow instances have appeared in my instance space.
		reviveSavedWorkFlows();
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

	public final boolean hasWorkflowId(String id) {
		if ( id == null || id.equals("") ) {
			return false;
		}
		for ( int i = 0; i < workflowInstances.size(); i++) {
			if ( workflowInstances.get(i).getMyId().equals(id)) {
				return true;
			}
		}
		return false;
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
