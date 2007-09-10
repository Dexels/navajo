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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

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
	private final Map tasks = Collections.synchronizedMap(new HashMap());
	private final ArrayList taskListeners = new ArrayList();
	private long configTimestamp = -1;
	
		
	private final static String TASK_CONFIG = "tasks.xml";
	private final static String TASK_INPUT_DIR = "tasks";
	private volatile static File taskInputDir = null;
	
	private static Object semaphore = new Object();
	private static Object semaphore2 = new Object();
	private static String id = "Navajo TaskRunner";
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	
	private boolean configIsBeingUpdated = false;
	
	public TaskRunner() {
		super(id);
		// Check for existence of tasks directory.
		try {
			taskInputDir = new java.io.File(Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/" + TASK_INPUT_DIR);
			if ( !taskInputDir.exists() ) {
				taskInputDir.mkdirs();
			}
		} catch (Throwable t) {
			// Dan maar niet.
		}
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
		if ( t.getNavajo() == null ) {
			return;
		}
		try {
			FileWriter fw = new FileWriter(new File(taskInputDir, t.getId() + "_request.xml"));
			t.getNavajo().write(fw);
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the Navajo request input for a task.
	 * 
	 * @param t
	 */
	protected static void writeTaskOutput(Task t) {
		if ( t.getResponse() == null ) {
			return;
		}
		try {
			FileWriter fw = new FileWriter(new File(taskInputDir, t.getId() + "_response.xml"));
			t.getResponse().write(fw);
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected static Navajo getTaskOutput(String id) {
		FileReader fr;
		try {
			fr = new FileReader(new File(taskInputDir, id + "_response.xml"));
			Navajo n = NavajoFactory.getInstance().createNavajo(fr);
			fr.close();
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
			File f = new File(taskInputDir, t.getId() + "_request.xml");
			if ( !f.exists() ) {
				System.err.println("Input navajo does not exist for task: " + t.getId());
				return;
			}
			FileReader fr = new FileReader(f);
			Navajo n = NavajoFactory.getInstance().createNavajo(fr);
			fr.close();
			t.setNavajo(n);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the Navajo request input for a task.
	 * 
	 * @param t
	 */
	protected void removeTaskInput(Task t) {
		try {
			File f = new File(taskInputDir, t.getId() + "_request.xml");
			if ( f.exists() ) {
				f.delete();
			}
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
			File f = new File(taskInputDir, t.getId() + "_response.xml");
			if ( f.exists() ) {
				f.delete();
			}
		} catch (Exception e) {
			
		}	
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
		if ( configTimestamp != getConfigTimeStamp() && getConfigTimeStamp() != -1 ) {
			return true;
		} else {
			return false;
		}
	}
	
	private void clearTaskMap() {
		if ( tasks != null ) {
			for (Iterator iter = tasks.values().iterator(); iter.hasNext();) {
				Task element = (Task) iter.next();
				element.setRemove(true);
			}
			tasks.clear();
		}
	}
	
	private void readConfig(boolean init) {
		// Read task configuration file to read predefined tasks config/tasks.xml
		setConfigTimeStamp();
		
		try {

			Navajo taskDoc = Dispatcher.getInstance().getNavajoConfig().readConfig(TASK_CONFIG);

			if ( taskDoc != null ) {
				
				// Remove all previous tasks.
				clearTaskMap();
				Message allTasksMsg = taskDoc.getMessage("tasks");
				ArrayList allTasks = taskDoc.getMessages("tasks");
				for (int i = 0; i < allTasks.size(); i++) {
					Message m = (Message) allTasks.get(i);
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
					if ( !init || workflowid.equals("") ) {
						try {
							// Create a new task and activate its trigger.
							Task t = new Task(webservice, username, password, newAcces, trigger, null);
							t.setWorkflowDefinition(workflowdef);
							t.setWorkflowId(workflowid);
							t.setProxy(proxy.booleanValue());
							t.setId(id);
							t.setKeepRequestResponse(keeprequestresponse.booleanValue());
							readTaskInput(t);
							instance.addTask(id, t, false);
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
		

	}
	
	public static TaskRunner getInstance() {
		
		if (instance!=null) {
			return instance;
		}
		
		synchronized (semaphore) {
			if ( instance != null ) {
				return instance;
			}
			
			instance = new TaskRunner();	
			try {
				JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
			} catch (Throwable t) {
				// Then but not.
			}
			instance.readConfig(true);
			instance.startThread(instance);
			
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Started task scheduler process $Id$");
			return instance;
		}
	}
	
	public final void worker() {
		// Check whether tasks.xml has gotten updated
		if ( isConfigModified() && !instance.configIsBeingUpdated ) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Task configuration is modified, re-initializing");
			readConfig(false);
		}
	}
	
	public int getTaskListSize() {
		return tasks.size();
	}
	
	public void removeTask(String id) {
		
		if ( !tasks.containsKey(id) ) {
			return;
		}
		
		Task t = (Task) tasks.get(id);
		tasks.remove(id);
		t.setRemove(true);
		
		// Remove task from configuration file config/tasks.xml
		synchronized (semaphore) {
			instance.configIsBeingUpdated = true;
			Navajo taskDoc;
			try {
				taskDoc = Dispatcher.getInstance().getNavajoConfig().readConfig(TASK_CONFIG);
				if (taskDoc != null) {
					Message allTasks = taskDoc.getMessage("tasks");
					if (allTasks != null) {
						Message tbr = containsTask(allTasks, id);
						if ( tbr != null ) {
							allTasks.removeMessage(tbr);
							Dispatcher.getInstance().getNavajoConfig().writeConfig(TASK_CONFIG, taskDoc);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				instance.setConfigTimeStamp();
				instance.configIsBeingUpdated = false;
			}
		}
	}
	
	private Message containsTask(Message allTasks, String id) throws Exception {
		ArrayList list = allTasks.getAllMessages();
		for (int i = 0; i < list.size(); i++) {
			if ( ((Message) list.get(i)).getProperty("id").getValue().equals(id) ) {
				return (Message) list.get(i);
			}
		}
		return null;
	}
	
	public ArrayList getFinishedTasks(String username, String fromDate) {
		ArrayList result = new ArrayList();
		File log = new File( Dispatcher.getInstance().getNavajoConfig().rootPath + "/log/tasks.log" );
		try {
			BufferedReader fr = new BufferedReader( new FileReader(log) );
			String line = null;
			while ( ( line = fr.readLine() ) != null ) {
				StringTokenizer st = new StringTokenizer(line, ";");
				String id = null;
				String user = null;
				String webservice = null;
				String trigger = null;
				String taskDesc = null;
				String clientId = null;
				String singleEvent = null;
				String status  = null;
				String starttime = null;
				String endtime = null;
				String errorMsg = null;
				if ( st.hasMoreTokens() ) {
					id = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					webservice = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					user = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					trigger = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					taskDesc = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					clientId = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					singleEvent = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					status = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					starttime = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					endtime = st.nextToken();
				}
				if ( st.hasMoreTokens() ) {
					errorMsg = st.nextToken();
				}
				
				if ( username == null || username.equals(user)) {
					try {
						Task t = new Task(webservice, user, "", null, trigger, null);
						t.setTrigger(trigger);
						t.setId(id);
						t.setFinished(true);
						t.setStartTime(sdf.parse(starttime));
						t.setFinishedTime(sdf.parse(endtime));
						if ( singleEvent.equals("true")) {
							t.getTrigger().setSingleEvent(true);
						}
						t.setStatus(status);
						t.setErrorMessage(errorMsg);
						// Check whether request/response is avaible.
						Navajo out = getTaskOutput(id);
						if ( out != null ) {
							t.setResponse(out);
							t.setKeepRequestResponse(true);
						}
						result.add(t);
					} catch (Exception e) {}
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static synchronized void log(Task t, Navajo result, boolean error, String errMsg, java.util.Date startedat) {
		
		if ( errMsg != null ) {
			errMsg = errMsg.replaceAll("\n", ".");
		}
		File log = new File( Dispatcher.getInstance().getNavajoConfig().rootPath + "/log/tasks.log" );
		FileWriter fw = null;
		boolean freshfile = false;
		if ( !log.exists() ) {
			log.getParentFile().mkdirs();
			freshfile = true;
		}
		
		try {
			
			fw = new FileWriter( log, true );
			if ( freshfile ) {
				fw.write("ID;WEBSERVICE;USERNAME;TRIGGER;TASKDESCRIPTION;CLIENTID;SINGLEEVENT;STATUS;STARTTIME;ENDTIME;ERRORMESSAGE\n");
			}
			StringBuffer header = new StringBuffer();
           
            header.append(t.getId() + ";" + 
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
          
			String logMsg = header.toString();
			fw.write(logMsg);
			
		} catch (Exception e) {
			System.err.println("Error writing tasks log: " + e.getMessage());
		} finally {
			try {
				if ( fw != null ) {
					fw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		long l = new Random().nextLong();
		return "scheduled_task"+l;
	}
	
	public boolean addTask(TaskInterface t) {
		return addTask(generateTaskId(),(Task) t, false);
	}
	
	public boolean addTask(String id, Task t, boolean overwrite) {
		
		if ( tasks.containsKey(id) ) {
			if ( !overwrite ) {
				t.setRemove(true);
				return false;
			} else {
				removeTask(id);
			}
		}
		
		if ( tasks.size() == maxSize ) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Task pool is full");
			t.setRemove(true);
			return false;
		}
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Adding task: " + id);
		tasks.put(id, t);
		t.setId(id);
		t.getTrigger().activateTrigger();
		t.getTrigger().setTask(t);
		
		// Add to task configuration file config/tasks.xml
		
		Navajo taskDoc = null;
		synchronized (semaphore) {
			instance.configIsBeingUpdated = true;
			try {

				// Read current version of tasks.xml.
				taskDoc = Dispatcher.getInstance().getNavajoConfig().readConfig(TASK_CONFIG);

				// Create empty tasks.xml if it did not yet exist.
				if (taskDoc == null) {
					taskDoc = NavajoFactory.getInstance().createNavajo();
					Message m = NavajoFactory.getInstance().createMessage(taskDoc, "tasks", Message.MSG_TYPE_ARRAY);
					taskDoc.addMessage(m);
				}
				Message allTasks = taskDoc.getMessage("tasks");
				
				// Add task to Navajo if it does not yet exist.
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
					writeTaskInput(t);
					Dispatcher.getInstance().getNavajoConfig().writeConfig(TASK_CONFIG, taskDoc);
				}

			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// Set configtimestamp.
				instance.setConfigTimeStamp();
				instance.configIsBeingUpdated = false;
			}
		}
		
		return true;
	}
	
	public Map getTasks() {
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
	
	private static final synchronized void resetInstance() {
		instance = null;
	}
	
	public void terminate() {
		// Remove all tasks.
		Iterator iter = tasks.values().iterator();
		while ( iter.hasNext() ) {
			Task t = (Task) iter.next();
			try {
				t.setRemove(true);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		tasks.clear();
		resetInstance();
		try {
			JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
