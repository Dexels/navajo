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

import java.util.Collection;
import java.util.Iterator;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.AuditLog;

public class TaskRunnerMap implements Mappable {

	// Introspect
	public TaskMap [] tasks;
	
	// Identification.
	public String id;
	public String webservice;
	public String trigger;
	
	// Actions
	public boolean start;
	public boolean update;
	public boolean remove;
	public boolean inactive;
	
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
		tr.updateTask(id, t);
	}
	
	public void setStart(boolean b) throws MappableException, UserException {
		
		if ( !b || id == null || webservice == null || trigger == null ) {
			return;
		}
		
		try {
			Task myTask = new Task(webservice, myAccess.rpcUser, myAccess.rpcPwd, myAccess, trigger);
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

	public void store() throws MappableException, UserException {
		
	}
	
	public void kill() {
		
	}

}
