package com.dexels.navajo.workflow;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.TribeMemberDownEvent;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.tribe.SharedStoreException;
import com.dexels.navajo.tribe.SharedStoreFactory;
import com.dexels.navajo.tribe.SharedStoreInterface;
import com.dexels.navajo.tribe.SharedStoreLock;
import com.dexels.navajo.util.AuditLog;

public final class WorkFlowManager extends GenericThread implements WorkFlowManagerMXBean, NavajoListener {

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
	private final HashSet<String> workflowInstances = new HashSet<String>();
	private final HashMap<String,WorkFlowDefinition> workflowDefinitions = new HashMap<String,WorkFlowDefinition>();
	private final static String id = "Navajo WorkFlow Manager";
	private final static String WORKFLOW_LOG_FILE = "workflow.log";
	public static final String VERSION = "$Id$";
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS dd-MM-yyyy");
	
	protected String workflowPath = null;
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
	private void reviveSavedWorkFlows(boolean init) {

		synchronized (semaphore_instances) {

			SharedStoreInterface ssi = SharedStoreFactory.getInstance();
			String [] files = ssi.getObjects(workflowPath);
			for (int i = 0; i < files.length; i++) {

				try {
					Object o = ssi.get(workflowPath, files[i]);

					if ( o instanceof WorkFlow ) {
						WorkFlow wf = (WorkFlow) o;
						if ( !workflowInstances.contains(wf.getMyId()) && !wf.isFinished() ) {  // Check whether I do not already own this workflow.
							if ( !wf.transientWorkFlow ) {
								if ( !wf.isKilled() ) {
									addWorkFlow(wf);
									wf.revive();
								}
							} else if ( init ) {
								AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, "Removing transient workflow: " + wf.getDefinition() );
								wf.setKill(true);
								//ssi.remove(workflowPath, files[i]);
							}
						}
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
					if (!f1.mkdirs()) {
						throw new ExceptionInInitializerError("Could not create workflow definition root");
					}
				}
				instance.configTimestamp = f1.lastModified();
			}
			
			// Create workflow persistence dir.
			if ( instance.workflowPath == null ) {
				instance.baseWorkflowPath = "workflows/instances";
				instance.workflowPath = instance.baseWorkflowPath + "/" + Dispatcher.getInstance().getNavajoConfig().getInstanceName();
				try {
					ssi.createParent(instance.workflowPath);
				} catch (SharedStoreException e) {
					AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, e.getMessage());
				}
			}
			
			WorkFlowDefinitionReader.initialize(new File(instance.workflowDefinitionPath), instance.workflowDefinitions);
			
			// Revive persisted workflows.
			instance.reviveSavedWorkFlows(true);
			
			// Start worker thread at last...
			instance.startThread(instance);
			
			// Register for events that I am interested in.
			NavajoEventRegistry.getInstance().addListener(TribeMemberDownEvent.class, instance);
			
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

			try {
				Object o = SharedStoreFactory.getInstance().get(baseWorkflowPath + "/" + fromServer, persistedWorkflows[i]);
				if ( o instanceof WorkFlow ) {
					WorkFlow wf = (WorkFlow) o;
					AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, "MOVING WORKFLOW: " + wf.getMyId() + " FROM SERVER " + fromServer);
					persistWorkFlow(wf);
				} else { // State dump
					SharedStoreFactory.getInstance().store(workflowPath, persistedWorkflows[i], o, false, false);
				}
				SharedStoreFactory.getInstance().remove(baseWorkflowPath + "/" + fromServer, persistedWorkflows[i]);
			} catch (SharedStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected WorkFlow getWorkFlowFromFile(String id) throws SharedStoreException {
		return (WorkFlow) SharedStoreFactory.getInstance().get( getInstance().workflowPath, id);
	}
	
	public boolean persistWorkFlow(WorkFlow wf) {

		if ( wf.isKilled() || wf.isFinished() ) {
			return false;
		}

		synchronized (semaphore_instances) {
			try {
				if ( !wf.isFinished() ) {
					System.err.println("PERSISTING workflow: " + wf.getMyId());
					SharedStoreFactory.getInstance().store(workflowPath, getUniqueWorkFlowId(wf), wf, false, false);
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	public void removePersistedWorkFlows(String definition) {
		//synchronized (semaphore_instances) {
			WorkFlow [] flows = getWorkflows(definition);
			for (int i = 0; i < flows.length; i++) {
				removeWorkFlow(flows[i]);
			}
		//}
	}
	
	private String getUniqueWorkFlowId(WorkFlow wf) {
		return wf.getMyId();
	}
		
	public void addWorkFlow(WorkFlow wf) {
		synchronized (semaphore_instances) {
			workflowInstances.add(getUniqueWorkFlowId(wf));
			WorkFlowDefinition wdf = workflowDefinitions.get(wf.getDefinition());
			if ( wdf != null ) {
				wdf.instances++;
			}
		}
	}
	
	
	/**
	 * Removes a WorkFlow instance completely.
	 * All persisted state dumps are also removed.
	 * 
	 * @param wf
	 */
	public void removeWorkFlow(WorkFlow wf) {
		synchronized (semaphore_instances) {
			System.err.println("wf.currentState = " + wf.currentState.getId());
			wf.currentState.removePersistedAccess();
			// Remove historic persisted states...
			for ( int i = 0; i < wf.historicStates.size(); i++ ) {
				//System.err.println("Removing historic state: " + wf.historicStates.get(i).getId());
				wf.historicStates.get(i).removePersistedAccess();
			}
			SharedStoreFactory.getInstance().remove(workflowPath, wf.getMyId());
			System.err.println(">>>>>>>>>>>>>>> REMOVE PERSISTED WORKFLOW: " + workflowPath + "/" + wf.getMyId());
			workflowInstances.remove(wf.getMyId());
			WorkFlowDefinition wdf = workflowDefinitions.get(wf.getDefinition());
			if ( wdf != null ) {
				wdf.instances--;
			}
		}
	}
	
	public void worker() {
        // Check whether definition has been added (only the tribal chief processes new workflow definitions).
		if ( isConfigModified() )  {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_WORKFLOW, "Workflow definition change detected");
			setConfigTimeStamp();
			WorkFlowDefinitionReader.initialize(new File(workflowDefinitionPath), workflowDefinitions);	
		}
		// Check whether new workflow instances have appeared in my instance space.
		reviveSavedWorkFlows(false);
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

		synchronized (semaphore_instances) {
			ArrayList<String> wfList = new ArrayList<String>(mng.workflowInstances);
			workflows = new WorkFlow[wfList.size()];
			for (int i = 0; i < wfList.size(); i++ ) {
				try {
					workflows[i] = getWorkFlowFromFile(wfList.get(i));
				} catch (SharedStoreException e) {
					//e.printStackTrace(System.err);
				}
			}
			return workflows.clone();
		}

	}
	
	/**
	 * Get all workflow instances specified by a specific name.
	 * 
	 * @param byName
	 * @return
	 */
	public final WorkFlow[] getWorkflows(String byName) {
		
		WorkFlowManager mng = WorkFlowManager.getInstance();
		ArrayList<WorkFlow> wfList = new ArrayList<WorkFlow>();
		
		synchronized (semaphore_instances) {
			Iterator<String> iter = mng.workflowInstances.iterator();
			while ( iter.hasNext() ) {
				String w = iter.next();
				WorkFlow wf;
				try {
					wf = getWorkFlowFromFile(w);
					if ( wf.getDefinition().equals(byName) ) {
						wfList.add(wf);
					}
				} catch (SharedStoreException e) {
				}
			}
			workflows = new WorkFlow[wfList.size()];
			workflows = (WorkFlow []) wfList.toArray(workflows);
			return workflows;
		}
		
	}

	/**
	 * Check whether a workflow with certain name exists.
	 * 
	 * @param name
	 * @param expression an expression to which the workflow state as to confirm
	 * @return
	 */
	public boolean existsWorkFlow(String name, String state, String expression) {

		WorkFlow [] flows = WorkFlowManager.getInstance().getWorkflows(name);
		for ( int i = 0; i < flows.length; i++ ) {

			if ( !flows[i].isFinished() && !flows[i].isKilled() ) {
				
				Navajo n = flows[i].getLocalNavajo();

				if ( state == null || flows[i].getCurrentState().getId().equals(state) ) {
					try {
						boolean b =  Condition.evaluate(expression, n);
						if ( b ) {
							return true;
						}
					} catch (Exception e) {
						e.printStackTrace(System.err);
						if ( n != null ) {
							//System.err.println("LOCAL WORKFLOW STATE IS FOR WFID " + flows[i].getMyId() + ", CURRENT STATE: " + flows[i].getCurrentState().getId() + 
							//		", IS FINISHED: " + flows[i].isFinished()  + ", IS KILLED = " + flows[i].isKilled());
							try {
								n.write(System.err);
							} catch (NavajoException e1) {

							}
						} else {
							//System.err.println("EMPTY LOCAL WORKFLOW STATE...");
						}
					}
				}
			}
		}

		return false;
	}
	
	public final boolean hasWorkflowId(String id) {
		if ( id == null || id.equals("") ) {
			return false;
		}
		synchronized (semaphore_instances) {
			return workflowInstances.contains(id);
		}
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public WorkFlow getWorkflow() throws UserException {
		if ( workflowId == null ) {
			throw new UserException(-1, "Set workflow id before retrieving workflow instance");
		}
		WorkFlowManager mng = WorkFlowManager.getInstance();
		synchronized (semaphore_instances) {
			ArrayList<String> wfList = new ArrayList<String>(mng.workflowInstances);
			for (int i = 0; i < wfList.size(); i++ ) {
				WorkFlow wf;
				try {
					wf = getWorkFlowFromFile(wfList.get(i));
					if ( wf != null && wf.getMyId().equals(workflowId) ) {
						return wf;
					}
				} catch (SharedStoreException e) {
					//e.printStackTrace(System.err);
				}
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
		return definitions.clone();
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

	protected static final synchronized void log(WorkFlow wf, Transition t, String errMsg, Exception ex) {

		String csvHeader = "SERVER;TIMESTAMP;WORKFLOWDEF;WORKFLOWID;USERNAME;STATE;TRANSITION;MESSAGE\n";

		if ( errMsg != null ) {
			errMsg = errMsg.replaceAll("\n", ".");
		}

		SharedStoreInterface si = SharedStoreFactory.getInstance();
		SharedStoreLock ssl = null;

		try {
			
			//ssl = si.lock("log", WORKFLOW_LOG_FILE, SharedStoreInterface.READ_WRITE_LOCK, true);
		
			if ( !si.exists("log", WORKFLOW_LOG_FILE)) {
				si.storeText("log", WORKFLOW_LOG_FILE, csvHeader, false, false);
			}

			StringBuffer contentLine = new StringBuffer();

			contentLine.append(
					Dispatcher.getInstance().getNavajoConfig().getInstanceName() + ";" +
					sdf.format(new java.util.Date()) + ";" + 
					wf.getDefinition() + ";" +
					wf.getMyId() + ";" +
					( wf.getCurrentState().getInitiatingAccess() != null ? wf.getCurrentState().getInitiatingAccess().getRpcUser() : "") + ";" +
					wf.getCurrentState().getId() + ";" +
					( t != null ? t.getDescription() : "") + ";" +
					errMsg +
			"\n"); 

			String logMsg = contentLine.toString();
			si.storeText("log", WORKFLOW_LOG_FILE, logMsg, true, false);

		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			if ( ssl != null ) {
				//si.release(ssl);
			}
		}
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

	/**
	 * This method is called by Navajo Event Mechanism.
	 * 
	 */
	public void onNavajoEvent(NavajoEvent ne) {
		System.err.println("In WorkFlowManager, event arrived: " + ne.getClass() );
		if ( ne instanceof TribeMemberDownEvent ) {
			instance.takeOverPersistedWorkFlows( ((TribeMemberDownEvent) ne).getTm().getMemberName() );
		} 
	}

	/**
	 * Clear all workflow instances.
	 */
	public void clearAllInstances() {
		WorkFlow [] wflows = getWorkflows();
		for ( int i = 0; i < wflows.length; i++ ) {
			getInstance().removeWorkFlow(wflows[i]);
		}
	}

	/**
	 * Clear all workflow instances of a specific definition.
	 */
	public void clearInstances(String definition) {
		WorkFlow [] wflows = getWorkflows(definition);
		for ( int i = 0; i < wflows.length; i++ ) {
			getInstance().removeWorkFlow(wflows[i]);
		}
	}
	

}
