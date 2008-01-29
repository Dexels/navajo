/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package com.dexels.navajo.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.scheduler.TaskInterface;
import com.dexels.navajo.server.enterprise.scheduler.TaskRunnerInterface;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.tribe.SharedStoreFactory;
import com.dexels.navajo.tribe.SharedStoreInterface;
import com.dexels.navajo.tribe.SharedStoreLock;
import com.dexels.navajo.tribe.TribeManager;
import com.dexels.navajo.util.AuditLog;

/**
 * The TaskRunner controls the set of tasks and offers logging functionality to tasks.
 * 
 * Tasks can be scheduled from the Dispatcher if the header of the requests contains a definition to do this:
 * 
 * <header>
 *   <transaction rpc_name="[some webservice]" rpc_usr="" rpc_pwd="" rpc_schedule="time:yyyy|MM|dd|HH|mm"/>
 * </header>
 * 
 * @author arjen
 *
 */
public class TaskRunner extends GenericThread implements TaskRunnerMXBean, TaskRunnerInterface {
	

	private static final String VERSION = "$Id$";
	
	// Maximum number of tasks.
	private int maxSize = 100000;
	private static volatile TaskRunner instance = null;
	private final Map<String,Task> tasks = Collections.synchronizedMap(new HashMap<String,Task>());
	private final ArrayList<TaskListener> taskListeners = new ArrayList<TaskListener>();
	private long configTimestamp = -1;
	
		
	private final static String TASK_CONFIG = "tasks.xml";
	private final static String TASK_INPUT_DIR = "tasks";
	private final static String TASK_LOG_FILE = "tasks.log";
	
	private static Object semaphore = new Object();
	private static Object initialization = new Object();
	private static Object semaphore2 = new Object();
	private static String id = "Navajo TaskRunner";
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	
	private boolean configIsBeingUpdated = false;
	private static volatile boolean beingInitialized = false;
	private Random rand = null;
	
	public TaskRunner() {
		super(id);
		// Check for existence of tasks directory.
		rand = new Random(System.currentTimeMillis());
	}
	
	protected boolean containsTask(String id) {
		return tasks.containsKey(id);
	}
	
	/**
	 * Write the Navajo request input for a task.
	 * 
	 * @param t
	 */
	protected void writeTaskInput(Task t) {
		
		// TODO: Make sure that the task input gets written in a shared store.
		
		if ( t.getNavajo() == null ) {
			return;
		}
		try {
			SharedStoreInterface si = SharedStoreFactory.getInstance();
			OutputStream os = si.getOutputStream(TASK_INPUT_DIR, t.getId() + "_request.xml", false);
			t.getNavajo().write(os);
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Write the Navajo request output for a task.
	 * 
	 * @param t
	 */
	protected void writeTaskOutput(Task t, boolean singleEvent, long timeStamp) {
		if ( t.getResponse() == null ) {
			return;
		}
		try {
			SharedStoreInterface si = SharedStoreFactory.getInstance();
			String name = ( !singleEvent ? t.getId() + "_" + timeStamp + "_response.xml" : t.getId() + "_response.xml" );
			OutputStream os = si.getOutputStream(TASK_INPUT_DIR, name, false);
			t.getResponse().write(os);
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected static Navajo getTaskOutput(String id) {
		try {
			SharedStoreInterface si = SharedStoreFactory.getInstance();
			InputStream is = si.getStream(TASK_INPUT_DIR, id + "_response.xml");
			Navajo n = NavajoFactory.getInstance().createNavajo(is);
			is.close();
			return n;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}
	
	/**
	 * Read the Navajo request input for a task.
	 * 
	 * @param t
	 */
	protected void readTaskInput(Task t) {
		try {
			SharedStoreInterface si = SharedStoreFactory.getInstance();
			InputStream is = si.getStream(TASK_INPUT_DIR, t.getId() + "_request.xml");
			Navajo n = NavajoFactory.getInstance().createNavajo(is);
			is.close();
			t.setNavajo(n);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Input navajo does not exist for task: " + t.getId());
			return;
		}
	}
	
	/**
	 * Removes the Navajo request input for a task.
	 * 
	 * @param t
	 */
	protected void removeTaskInput(Task t) {
		try {
			SharedStoreInterface si = SharedStoreFactory.getInstance();
			si.remove(TASK_INPUT_DIR, t.getId() + "_request.xml");
		} catch (Exception e) {
			
		}	
	}
	
	/**
	 * Removes the Navajo request input for a task.
	 * 
	 * @param t
	 */
	protected void removeTaskOutput(Task t) {
		try {
			SharedStoreInterface si = SharedStoreFactory.getInstance();
			si.remove(TASK_INPUT_DIR, t.getId() + "_response.xml");
		} catch (Exception e) {
			
		}	
	}
	
	private final boolean needsPersistence(Task t) {
		return ( !t.getTrigger().isSingleEvent() && ( t.getWorkflowDefinition() == null || t.getWorkflowDefinition().equals("") ) );
	}
	
	private long getConfigTimeStamp() {
		if (  Dispatcher.getInstance() != null && Dispatcher.getInstance().getNavajoConfig() != null ) {
			java.io.File f = new java.io.File(Dispatcher.getInstance().getNavajoConfig().getConfigPath() + "/" + TASK_CONFIG);
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
		if ( configTimestamp != getConfigTimeStamp() ) {
			return true;
		} else {
			return false;
		}
	}
	
	private void clearTaskMap(String taskId) {
		synchronized (semaphore) {
			if ( containsTask(taskId)) {
				Task t = (Task) tasks.get(taskId);
				t.setRemove(true);
				tasks.remove(taskId);
			}
		}
	}
	
	protected Navajo readConfig(boolean init) {
		// Read task configuration file to read predefined tasks config/tasks.xml
		
		HashSet<String> currentTasks = new HashSet<String>();
		
		Navajo taskDoc = null;
		try {

			taskDoc = Dispatcher.getInstance().getNavajoConfig().readConfig(TASK_CONFIG);
			setConfigTimeStamp();
			
			if ( taskDoc != null ) {
				
				Message allTasksMsg = taskDoc.getMessage("tasks");
				ArrayList<Message> allTasks = taskDoc.getMessages("tasks");
				for (int i = 0; i < allTasks.size(); i++) {
					Message m = allTasks.get(i);
					String id = m.getProperty("id").getValue();
					String webservice = m.getProperty("webservice").getValue();
					Boolean proxy = ( m.getProperty("proxy") != null ? (Boolean) m.getProperty("proxy").getTypedValue() : Boolean.FALSE);
					String username = m.getProperty("username").getValue();
					String password = m.getProperty("password").getValue();
					String trigger = m.getProperty("trigger").getValue();
					String workflowdef =  (  m.getProperty("workflowdef") != null ?  m.getProperty("workflowdef").getValue() : "");
					String workflowid = (  m.getProperty("workflowid") != null ? m.getProperty("workflowid").getValue() : "");
					Boolean keeprequestresponse = ( m.getProperty("keeprequestresponse") != null ? 
							(Boolean) m.getProperty("keeprequestresponse").getTypedValue() : Boolean.FALSE );

					Access newAcces = new Access(-1, -1, -1, username, webservice, "Taskrunner", "127.0.0.1", "localhost", false, null);

					// Do not add tasks from previous workflow instances when server is started. Workflow instance tasks
					// are added through revival of workflow.
					if ( !init || workflowid == null || workflowid.equals("") ) {
						try {
							// Create a new task and activate its trigger.
							Task t = new Task(webservice, username, password, newAcces, trigger, null);
							t.setWorkflowDefinition(workflowdef);
							t.setWorkflowId(null);
							t.setProxy(proxy.booleanValue());
							t.setId(id);
							t.setKeepRequestResponse(keeprequestresponse.booleanValue());
							readTaskInput(t);
							currentTasks.add(t.getId());
							// Remove previous version of this task.
							clearTaskMap(id);
							addTask(id, t, false);
						} catch (IllegalTrigger it) {
							//it.printStackTrace(System.err);
							AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Problem adding task: " + it.getMessage());
						}
					} else {
						System.err.println("REMOVING WORKFLOW TASK.................................................");
						allTasksMsg.removeMessage(m);
					}
				}
				
				// Write task definition file back (in case of workflow tasks that were skipped).
				if ( init ) {
					Dispatcher.getInstance().getNavajoConfig().writeConfig(TASK_CONFIG, taskDoc);
				}
			}

		} catch (Throwable e) {
			e.printStackTrace(System.err);
		} 
		
		// Check deleted tasks...
		Iterator<Task> iters = tasks.values().iterator();
		while ( iters.hasNext() ) {
			Task t = iters.next();
			if ( needsPersistence(t) ) {
				if ( !currentTasks.contains(t.getId()) ) {
					System.err.println("Removing removed task from tasks.xml: " + t.getId() + " with trigger " + t.getTriggerDescription() + ", workflow definition:" + t.getWorkflowDefinition());
					t.setRemove(true);
				}
			}
		}
		return taskDoc;
	}
	
	public static TaskRunner getInstance() {
		
		if (instance!=null) {
			return instance;
		}
		
		synchronized (initialization) {
			if ( instance != null ) {
				return instance;
			}
			beingInitialized = true;
			
			instance = new TaskRunner();	
			try {
				JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
			} catch (Throwable t) {
				// Then but not.
			}
			//instance.readConfig(true); Read config in worker loop...
			instance.startThread(instance);
			
			// Read Listener store for already registrered task listeners....
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Started task scheduler process $Id$");
			
			beingInitialized = false;
			
			return instance;
		}
	}
	
	public final void worker() {
		synchronized (initialization) {
			// Check whether tasks.xml has gotten updated
//			System.err.println("IN WORKER, isConfigModified = " + isConfigModified() + ", CHIEF = " +  TribeManager.getInstance().getIsChief() +
//			   ", configIsBeingUpdated = " + instance.configIsBeingUpdated + ", beingInitialized = " + beingInitialized);
			if ( isConfigModified() &&   TribeManager.getInstance().getIsChief() && !instance.configIsBeingUpdated && !beingInitialized ) {
				AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Task configuration is modified, re-initializing");
				readConfig(false);
			}
		}
	}
	
	public int getTaskListSize() {
		return tasks.size();
	}
	
	protected static SharedStoreLock getConfigLock() {

		SharedStoreInterface si = SharedStoreFactory.getInstance();
		SharedStoreLock ssl = si.lock("config", "tasks.xml", SharedStoreInterface.READ_WRITE_LOCK, true);
		return ssl;		

	}
	
	protected static void releaseConfigLock(SharedStoreLock ssl) {


		SharedStoreInterface si = SharedStoreFactory.getInstance();
		si.release(ssl);

	}
	
	protected void writeTaskConfig(Navajo taskDoc) {

		configIsBeingUpdated = true;
		try {
			Dispatcher.getInstance().getNavajoConfig().writeConfig(TASK_CONFIG, taskDoc);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		// Set configtimestamp.
		setConfigTimeStamp();
		configIsBeingUpdated = false;
	}
	
	public void removeTask(String id) {

		if ( !tasks.containsKey(id) ) {
			return;
		}

		// Remove task from configuration file config/tasks.xml
		synchronized (semaphore) {
			Task t = (Task) tasks.get(id);
			tasks.remove(id);
			t.setRemove(true);
		}
	}
	
	private Message containsTask(Message allTasks, String id) throws Exception {
		ArrayList<Message> list = allTasks.getAllMessages();
		for (int i = 0; i < list.size(); i++) {
			if ( ((Message) list.get(i)).getProperty("id").getValue().equals(id) ) {
				return (Message) list.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Task> getFinishedTasks(String username, String fromDate) {
		
		SharedStoreInterface si = SharedStoreFactory.getInstance();
		
		ArrayList<Task> result = new ArrayList<Task>();
		BufferedReader fr = null;
		try {
			fr = new BufferedReader( new InputStreamReader(si.getStream("log", TASK_LOG_FILE) ) ) ;
			String line = null;
			while ( ( line = fr.readLine() ) != null ) {
				String [] tokens = line.split(";");
				String id = tokens[0];
				String webservice = tokens[1];
				String user = tokens[2];
				String trigger = tokens[3];
//				String taskDesc = tokens[4];
//				String clientId = tokens[5];
				String singleEvent = tokens[6];
				String status  = tokens[7];
				String starttime = tokens[8];
				String endtime = tokens[9];
				String errorMsg = ( tokens.length > 10 ? tokens[10] : "");
								
				if ( username == null || username.equals(user)) {
					try {
						Task t = new Task(webservice, user, "", null, trigger, null);
						//t.setTrigger(trigger);
						t.setId(id);
						t.setFinished(true);
						t.setStartTime(sdf.parse(starttime));
						t.setFinishedTime(sdf.parse(endtime));
						if ( "true".equals(singleEvent)) {
							t.getTrigger().setSingleEvent(true);
						}
						t.setStatus(status);
						t.setErrorMessage(errorMsg);
						// Check whether request/response is avaible.
						System.err.println("About to get taskOutput");
						Navajo out = getTaskOutput(id);
						if ( out != null ) {
							t.setResponse(out);
							t.setKeepRequestResponse(true);
						}
						result.add(t);
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if ( fr != null ) {
				try {
					fr.close();
				} catch (IOException e) {
				}
			}
		}
		
		return result;
	}
	
	protected final synchronized void log(Task t, Navajo result, boolean error, String errMsg, java.util.Date startedat) {

		String csvHeader = "ID;WEBSERVICE;USERNAME;TRIGGER;TASKDESCRIPTION;CLIENTID;SINGLEEVENT;STATUS;STARTTIME;ENDTIME;ERRORMESSAGE\n";

		if ( errMsg != null ) {
			errMsg = errMsg.replaceAll("\n", ".");
		}

		SharedStoreInterface si = SharedStoreFactory.getInstance();
		SharedStoreLock ssl = null;

		try {
			
			ssl = si.lock("log", TASK_LOG_FILE, SharedStoreInterface.READ_WRITE_LOCK, true);
		
			if ( !si.exists("log", TASK_LOG_FILE)) {
				si.storeText("log", TASK_LOG_FILE, csvHeader, false, false);
			}

			StringBuffer contentLine = new StringBuffer();

			contentLine.append(t.getId() + ";" + 
					t.getWebservice() + ";" + 
					t.getUsername() + ";" + 
					t.getTrigger().getDescription() + ";" + 
					t.getTaskDescription() + ";" +
					t.getClientId() + ";" +
					t.getTrigger().isSingleEvent() + ";" +
					(error ? "error" : "ok") + ";" +
					sdf.format(startedat) + ";" + 
					sdf.format(new java.util.Date()) + ";" + 
					( error ? errMsg : "") + 
			"\n"); 

			String logMsg = contentLine.toString();
			si.storeText("log", TASK_LOG_FILE, logMsg, true, false);

		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			if ( ssl != null ) {
				si.release(ssl);
			}
		}
	}
	
	public void updateTask(String id, Task t) {
		if (! tasks.containsKey(id ))  {
			return;
		}
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Updating task: " + id);
		// Remove first.
		removeTask(id);
		// Add later.
		addTask(id, t, false);
	}
	
	private final String generateTaskId() {
		long l = rand.nextLong() + System.currentTimeMillis();
		return "scheduled_task"+l;
	}
	
	public boolean addTask(TaskInterface t) {
		return addTask(generateTaskId(),(Task) t, false);
	}
	
	public boolean addTask(String id, Task t, boolean overwrite) {

		synchronized (semaphore) {

			if ( tasks.containsKey(id) ) {
				if ( !overwrite ) {
					t.setRemove(true);
					return false;
				} else {
					// Don't write config yet, wait until config is written back entirely
					removeTask(id);
				}
			}

			if ( tasks.size() == maxSize ) {
				AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Task pool is full");
				t.setRemove(true);
				return false;
			}
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Adding task: " + id + ", workflow definition: " + t.getWorkflowDefinition());
			tasks.put(id, t);
			t.setId(id);
			t.getTrigger().activateTrigger();
			t.getTrigger().setTask(t);

			// Add to task configuration file config/tasks.xml.

			Navajo taskDoc = null;

			// Only write to task.xml if NOT single event and if NOT workflow task.
			if ( needsPersistence(t) ) {

				SharedStoreLock ssl = null;
				try {
					ssl = getConfigLock();

					// Read current version of tasks.xml.
					taskDoc = Dispatcher.getInstance().getNavajoConfig().readConfig(TASK_CONFIG);

					// Create empty tasks.xml if it did not yet exist.
					if (taskDoc == null) {
						taskDoc = NavajoFactory.getInstance().createNavajo();
						Message m = NavajoFactory.getInstance().createMessage(taskDoc, "tasks", Message.MSG_TYPE_ARRAY);
						taskDoc.addMessage(m);
					}
					Message allTasks = taskDoc.getMessage("tasks");

					// Add task to Navajo if it does not yet exist and if it is not part of a workflow instance. Workflow instances
					// tasks are managed by WorkFlowManager.

					/**
					 * TODO NAGAAN OF HET OOK MOGELIJK IS OM WORKFLOW GERELATEERD HELEMAAL NIET OP TE SLAAN:
					 * t.getWorkflowDef() == null || t.getWorkflowDef().equals("")
					 */
					if ( allTasks != null && containsTask(allTasks, id) == null ) {
						Message newTask = NavajoFactory.getInstance().createMessage(taskDoc, "tasks");
						allTasks.addMessage(newTask);
						Property propId = NavajoFactory.getInstance().createProperty(taskDoc, "id", Property.STRING_PROPERTY, t.getId(), 0, "", Property.DIR_OUT);
						Property propUser = NavajoFactory.getInstance().createProperty(taskDoc, "username", Property.STRING_PROPERTY, t.getUsername(), 0, "", Property.DIR_OUT);
						Property propPassword = NavajoFactory.getInstance().createProperty(taskDoc, "password", Property.STRING_PROPERTY, t.getPassword(), 0, "", Property.DIR_OUT);
						Property propService = NavajoFactory.getInstance().createProperty(taskDoc, "webservice", Property.STRING_PROPERTY, t.getWebservice(), 0, "", Property.DIR_OUT);
						Property propProxy = NavajoFactory.getInstance().createProperty(taskDoc, "proxy", Property.BOOLEAN_PROPERTY, t.isProxy()+"", 0, "", Property.DIR_OUT);
						Property propKRS = NavajoFactory.getInstance().createProperty(taskDoc, "keeprequestresponse", Property.BOOLEAN_PROPERTY, t.isKeepRequestResponse()+"", 0, "", Property.DIR_OUT);
						Property propTrigger = NavajoFactory.getInstance().createProperty(taskDoc, "trigger", Property.STRING_PROPERTY, t.getTrigger().getDescription(), 0, "", Property.DIR_OUT);
						Property propWorkflowDef = NavajoFactory.getInstance().createProperty(taskDoc, "workflowdef", Property.STRING_PROPERTY, t.getWorkflowDefinition(), 0, "", Property.DIR_OUT);
						Property propWorkflowId = NavajoFactory.getInstance().createProperty(taskDoc, "workflowid", Property.STRING_PROPERTY, t.getWorkflowId(), 0, "", Property.DIR_OUT);

						Property propTaskDescription = NavajoFactory.getInstance().createProperty(taskDoc, "taskdescription", Property.STRING_PROPERTY, t.getTaskDescription(), 0, "", Property.DIR_OUT);
						Property propClientId = NavajoFactory.getInstance().createProperty(taskDoc, "clientid", Property.STRING_PROPERTY, t.getClientId(), 0, "", Property.DIR_OUT);

						newTask.addProperty(propId);
						newTask.addProperty(propUser);
						newTask.addProperty(propPassword);
						newTask.addProperty(propService);
						newTask.addProperty(propProxy);
						newTask.addProperty(propKRS);
						newTask.addProperty(propTrigger);
						newTask.addProperty(propWorkflowDef);
						newTask.addProperty(propWorkflowId);
						newTask.addProperty(propTaskDescription);
						newTask.addProperty(propClientId);

						// Persist task request Navajo and tasks.xml
						writeTaskConfig(taskDoc);
					}

				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if ( ssl != null ) {
						releaseConfigLock(ssl);
					}
				}
			}
			
			if ( t.isKeepRequestResponse() ) {
				writeTaskInput(t);
			}

		}

		return true;
	}
	
	public Map<String,Task> getTasks() {
		return tasks;
	}
	
	public static void main(String [] args)  {
		try {
		TaskRunner tr = TaskRunner.getInstance();
		Task t1 = new Task("asdfd", null, "", null, "time:*|*|10|10|*", null);
		Task t2 = new Task("InitBM", "ROOT", "", null, "webservice:", null);
		tr.addTask("myTask", t1, false);
		tr.addTask("myOtherTask", t2, false);
		System.err.println("LEAVING MAIN");
		} catch (Exception e ) {
			System.err.println(e.getMessage());
		}
	}

	public String getVERSION() {
		return VERSION;
	}
	
	public void terminate() {
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Killed");
	}
	
	public final void addTaskListener(TaskListener tl) {
		synchronized ( semaphore2 ) {
			taskListeners.add(tl);
		}
	}
	
	public final void removeTaskListener(TaskListener tl) {
		synchronized ( semaphore2 ) {
			taskListeners.remove(tl);
		}
	}
	
	public final void fireAfterTaskEvent(Task t, Navajo request) {
		
		synchronized ( semaphore2 ) {
			for ( int i = 0 ; i < taskListeners.size(); i++ ) {
				TaskListener tl = (TaskListener) taskListeners.get(i); 
				tl.afterTask(t, request);
			}
		}
	}
	
	/**
	 * This method returns true if 
	 * 1. The associated webservice of this task was not a proxy.
	 * 2. The associated webservice of this task was a proxy and the beforeTask method of the tasklistener returns true.
	 * 
	 * @param t
	 * @return
	 */
	public final boolean fireBeforeTaskEvent(Task t) {

		synchronized ( semaphore2 ) {
			for ( int i = 0 ; i < taskListeners.size(); i++ ) {
				TaskListener tl = (TaskListener) taskListeners.get(i);
				boolean result = tl.beforeTask(t);
				if ( !result ) {
					return false;
				}
			}
		}
		return true;
	}

	public final void removeTask(TaskInterface t) {
		removeTask(t.getId());
	}
}
