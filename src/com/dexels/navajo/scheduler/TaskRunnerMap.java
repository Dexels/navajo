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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.AuditLog;

/**
 * Wrapper class for the TaskRunner. Can be used from Navajo Scripts to introspect the TaskRunnner.
 * 
 * @author arjen
 *
 */
public class TaskRunnerMap implements Mappable {

	// Introspect
	public TaskMap [] tasks;
	public String username = null;
	public TaskMap [] userTasks;
	public String response;
	
	// Identification.
	public String id;
	public String webservice;
	public String trigger;
	public Binary navajo;
	
	// Actions
	public boolean start;
	public boolean update;
	public boolean remove;
	public boolean inactive;


	// Various
	public boolean finished;
	public Date finishedTime;
	
	private Access myAccess;
	private Navajo myRequest;
	
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		myAccess = access;
		myRequest = inMessage;
	}

	public void setTrigger(String s) {
		this.trigger = s;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setNavajo(Binary n) {
		this.navajo = n;
	}
	
	public void setWebservice(String webservice) {
		this.webservice = webservice;
	}
	
	public void setInactive(boolean b) {
		TaskRunner tr = TaskRunner.getInstance();
		Task t = (Task) tr.getTasks().get(id);
		if ( t != null ) {
			t.setInactive(b);
		}
	}
	
	public void setRemove(boolean b) {
		if ( !b || id == null ) {
			return;
		}
		TaskRunner tr = TaskRunner.getInstance();
		tr.removeTask(id);
	}
	
	public void setUpdate(boolean b) throws MappableException, UserException {
		if ( !b || id == null ) {
			return;
		}
		TaskRunner tr = TaskRunner.getInstance();
		if ( ! tr.getTasks().containsKey(id) ) {
			return;
		}
		Task t = (Task) tr.getTasks().get(id);
		t.setWebservice(this.webservice);
		t.setTrigger(this.trigger);
		t.getTrigger().activateTrigger();
		tr.updateTask(id, t);
	}
	
	public void setStart(boolean b) throws MappableException, UserException {
		
		if ( !b || id == null || webservice == null || trigger == null ) {
			return;
		}
		
		try {
			// Create request Navajo if navajo binary has been set.
			Navajo requestNavajo = null;
			if ( navajo != null ) {
				try {
					requestNavajo = NavajoFactory.getInstance().createNavajo( navajo.getDataAsStream() );
				} catch (Exception e) {
					requestNavajo = null;
				}
			}
			Task myTask = new Task(webservice, myAccess.rpcUser, myAccess.rpcPwd, myAccess, trigger, requestNavajo);
			TaskRunner tr = TaskRunner.getInstance();
			if ( tr.containsTask( id ) ) {
				throw new UserException(-1, "Tasks already exists");
			}
			tr.addTask(id, myTask);
		} catch (IllegalTrigger i) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, i.getMessage());
		} catch (IllegalTask t) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, t.getMessage());
		}
	}
	
	public TaskMap [] getTasks() throws UserException, MappableException {
		TaskRunner tr = TaskRunner.getInstance();
		Collection all = tr.getTasks().values();
		
		TaskMap [] tm = new TaskMap[all.size()];
		Iterator iter = all.iterator();
		int index = 0;
		while ( iter.hasNext() ) {
			Task t = (Task) iter.next();
			tm[index] = new TaskMap(t);
			tm[index].load(null, myRequest, myAccess, Dispatcher.getInstance().getNavajoConfig() );
			index++;
		}
		
		return tm;
	}
	
	public TaskMap [] getUserTasks() throws UserException, MappableException {
		return getUserTasks(username);
	}
	
	public TaskMap [] getUserTasks(String user) throws UserException, MappableException {
		TaskRunner tr = TaskRunner.getInstance();
		// Get all finished tasks for this user.
		ArrayList l = tr.getFinishedTasks(user, null);
		// Add none-finished tasks.
		TaskMap [] scheduledTasks = getTasks();
		for (int i = 0; i < scheduledTasks.length; i++) {
			if ( user == null || scheduledTasks[i].getUsername().equals(user) ) {
				l.add(scheduledTasks[i]);
			}
		}
		TaskMap [] tm = new TaskMap[l.size()];
		Iterator iter = l.iterator();
		int index = 0;
		
		while ( iter.hasNext() ) {
			Object o = iter.next();
			if ( o instanceof Task ) {
				Task t = (Task) o;
				tm[index] = new TaskMap(t);
				tm[index].load(null, myRequest, myAccess, Dispatcher.getInstance().getNavajoConfig() );
			} else {
				tm[index] = (TaskMap) o;
			}
			index++;
		}
		
		return tm;
	}

	public void store() throws MappableException, UserException {
		
	}
	
	public void kill() {
		
	}

	public static void main(String [] args) throws Exception {
		String aap = "<tml><message name=\"aap\"/></tml>";
		
		Binary b = new Binary(aap.getBytes());
		
		InputStream is = b.getDataAsStream();
		
		Navajo n = NavajoFactory.getInstance().createNavajo(is);
		
		n.write(System.err);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}
	
	public String getResponse() {
		Navajo out = TaskRunner.getTaskOutput(id);
		if ( out != null ) {
			myAccess.setOutputDoc(out);
		}
		return null;
	}
}
