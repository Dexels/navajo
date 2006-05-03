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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.util.AuditLog;

public class TaskRunner implements Runnable {

	private int maxSize = 25;
	private static TaskRunner instance = null;
	private final Map tasks = Collections.synchronizedMap(new HashMap());
	private NavajoConfig myConfig;
	private Dispatcher myDispatcher;
	private long configTimestamp = -1;
		
	public static TaskRunner getInstance(NavajoConfig config) {
		return getInstance(config, null);
	}
	
	protected boolean containsTask(String id) {
		return tasks.containsKey(id);
	}
	
	private long getConfigTimeStamp() {
		if ( myConfig != null ) {
			java.io.File f = new java.io.File(myConfig.getConfigPath() + "/tasks.xml");
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
	
	private synchronized void readConfig() {
		// Read task configuration file to read predefined tasks config/tasks.xml
    	try {
    		if ( myConfig == null ) {
    			return;
    		}
    		Navajo taskDoc = myConfig.readConfig("tasks.xml");
    		
    		if ( taskDoc != null ) {
    			// Remove all previous tasks.
    			clearTaskMap();
	    		ArrayList allTasks = taskDoc.getMessages("tasks");
	    		for (int i = 0; i < allTasks.size(); i++) {
	    			Message m = (Message) allTasks.get(i);
	    			String id = m.getProperty("id").getValue();
	    			String webservice = m.getProperty("webservice").getValue();
	    			String username = m.getProperty("username").getValue();
	    			String password = m.getProperty("password").getValue();
	    			String trigger = m.getProperty("trigger").getValue();
	    			Access newAcces = new Access(-1, -1, -1, username, webservice, "Taskrunner", "127.0.0.1", "localhost", false, null);
	    			Task t = new Task(webservice, username, password, newAcces, trigger);
	    			t.setDispatcher(myDispatcher);
	    			instance.addTask(id, t);
	    		}
	    		setConfigTimeStamp();
    		}
    	} catch (IllegalTrigger i) {
    		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, i.getMessage());
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static TaskRunner getInstance(NavajoConfig config, Dispatcher myDispatcher) {
		
		if ( instance != null ) {
			return instance;
		}
		
		instance = new TaskRunner();
		instance.myConfig = config;
		instance.myDispatcher = myDispatcher;
		Thread thread = new Thread(instance);
	    thread.setDaemon(true);
	    thread.start();
	    
	    if ( config != null && myDispatcher != null ) {
	    	instance.readConfig();
	    }
	    AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Started task scheduler process $Id$");
		
	    return instance;
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				// Check whether tasks.xml has gotten updated.
				if ( isConfigModified() ) {
					readConfig();
				}
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
			}
			
		}
	}
	
	public int getTaskListSize() {
		return tasks.size();
	}
	
	public synchronized void removeTask(String id) {
		
		if ( !tasks.containsKey(id) ) {
			return;
		}
		
		Task t = (Task) tasks.get(id);
		tasks.remove(id);
		t.setRemove(true);
		
		// Remove task from configuration file config/tasks.xml
		Navajo taskDoc;
		try {
			taskDoc = myConfig.readConfig("tasks.xml");
			if (taskDoc != null) {
				Message allTasks = taskDoc.getMessage("tasks");
				if (allTasks != null) {
					Message tbr = containsTask(allTasks, id);
					if ( tbr != null ) {
						allTasks.removeMessage(tbr);
						if ( myConfig != null ) {
							myConfig.writeConfig("tasks.xml", taskDoc);
						}
						setConfigTimeStamp();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public static synchronized void log(Task t, Navajo result, boolean error, java.util.Date startedat) {
		File log = new File( Dispatcher.getNavajoConfig().rootPath + "/log/tasks.log" );
		if ( !log.exists() ) {
			log.getParentFile().mkdirs();
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter( log, true );
			StringBuffer header = new StringBuffer();
            header.append("---------------------------------------------------------\n");
            header.append("REPORT LOG FOR TASK: " + t.getId() + "\n"); 
            header.append("ws       : " + t.getWebservice() + "\n");
            header.append("trigger  : " + t.getTrigger().getDescription() + "\n");
            header.append("status   : " +  (error ? "error" : "ok") + "\n");
            header.append("started @: " + startedat + "\n");
            header.append("finished@: " + (new java.util.Date()) + "\n");
            
			StringWriter sw = new StringWriter();
			if ( error ) {
				header.append("error result: \n");
				result.write(sw);
			}
			String logMsg = header.toString() + sw.toString();
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
	
	public synchronized void updateTask(String id, Task t) {
		if (! tasks.containsKey(id ))  {
			return;
		}
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Updating task: " + id);
		// Remove first.
		removeTask(id);
		// Add later.
		addTask(id, t);
	}
	
	public synchronized boolean addTask(String id, Task t) {
		
		if ( tasks.containsKey(id) ) {
			t.setRemove(true);
			return false;
		}
		
		if ( tasks.size() == maxSize ) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Task pool is full");
			t.setRemove(true);
			return false;
		}
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Adding task: " + id);
		tasks.put(id, t);
		t.setId(id);
		Thread thread = new Thread(t);
		t.setThread(thread);
		thread.start();
		// Add to task configuration file config/tasks.xml
		
		Navajo taskDoc = null;
		try {
			if ( myConfig != null ) {
				taskDoc = myConfig.readConfig("tasks.xml");
			}
			if (taskDoc == null) {
				taskDoc = NavajoFactory.getInstance().createNavajo();
				Message m = NavajoFactory.getInstance().createMessage(taskDoc, "tasks", Message.MSG_TYPE_ARRAY);
				taskDoc.addMessage(m);
			}
			Message allTasks = taskDoc.getMessage("tasks");
			if ( allTasks != null && containsTask(allTasks, id) == null ) {
				Message newTask = NavajoFactory.getInstance().createMessage(taskDoc, "tasks");
				allTasks.addMessage(newTask);
				Property propId = NavajoFactory.getInstance().createProperty(taskDoc, "id", Property.STRING_PROPERTY, t.getId(), 0, "", Property.DIR_OUT);
				Property propUser = NavajoFactory.getInstance().createProperty(taskDoc, "username", Property.STRING_PROPERTY, t.getUsername(), 0, "", Property.DIR_OUT);
				Property propPassword = NavajoFactory.getInstance().createProperty(taskDoc, "password", Property.STRING_PROPERTY, t.getPassword(), 0, "", Property.DIR_OUT);
				Property propService = NavajoFactory.getInstance().createProperty(taskDoc, "webservice", Property.STRING_PROPERTY, t.getWebservice(), 0, "", Property.DIR_OUT);
				Property propTrigger = NavajoFactory.getInstance().createProperty(taskDoc, "trigger", Property.STRING_PROPERTY, t.getTrigger().getDescription(), 0, "", Property.DIR_OUT);
				newTask.addProperty(propId);
				newTask.addProperty(propUser);
				newTask.addProperty(propPassword);
				newTask.addProperty(propService);
				newTask.addProperty(propTrigger);
				
				if ( myConfig != null ) {
					myConfig.writeConfig("tasks.xml", taskDoc);
				}
				instance.setConfigTimeStamp();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return true;
	}
	
	public Map getTasks() {
		return tasks;
	}
	
	public static void main(String [] args)  {
		try {
		TaskRunner tr = TaskRunner.getInstance(null);
		Task t1 = new Task("asdfd", null, "", null, "time:*|*|10|10|*");
		Task t2 = new Task("InitBM", "ROOT", "", null, "webservice:");
		tr.addTask("myTask", t1);
		tr.addTask("myOtherTask", t2);
		System.err.println("LEAVING MAIN");
		} catch (Exception e ) {
			System.err.println(e.getMessage());
		}
	}
}
