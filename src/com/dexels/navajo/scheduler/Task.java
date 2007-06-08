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

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.FatalException;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.scheduler.TaskInterface;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

/**
 * Defines the task object that describes among other things, the webservice
 * that needs to be triggered.
 * 
 * @author Arjen
 *
 */
public class Task implements Runnable, TaskMXBean, TaskInterface {
	
	public String webservice;
	public String username;
	public String password;
	public String trigger;
	public Navajo navajo = null;
	public Navajo response = null;
	
    private Trigger myTrigger = null;
    private boolean remove = false;
    private boolean inactive = false;
    private boolean isRunning = false;
    private String id = null;
    private Thread myThread = null;
    
    public Task() {
    	// The empty constructor.
    }
    
	/**
	 * 
	 * @param webservice
	 * @param username
	 * @param password
	 * @param a
	 * @param triggerURL  
	 */
	public Task(String webservice, 
				String username,
				String password,
				Access a, 
				String triggerURL,
				Navajo requestNavajo) throws IllegalTrigger, IllegalTask {
		if ( webservice == null || webservice.equals("") ) {
			throw new IllegalTask("Empty webservice pattern");
		}
		this.webservice = webservice;
		if ( username == null || password == null ) {
			throw new IllegalTask("No username/password specified");
		}
		this.username = username;
		this.password = password;
		this.myTrigger = Trigger.parseTrigger(triggerURL);
		this.navajo = requestNavajo;
	}
	
	/**
	 * Set the thread to which the task belongs.
	 * 
	 * @param t the thread
	 */
	protected void setThread(Thread t) {
		myThread = t;
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Scheduling task: " + id);
	}
	
	/**
	 * @return the trigger object that goes with this task.
	 */
	public Trigger getTrigger() {
		return myTrigger;
	}
	
	public void setTrigger(String s) throws UserException {
		try {
			Trigger t = Trigger.parseTrigger(s);
			this.myTrigger = t;
			System.err.println("Set trigger for task " + getId() + ": " + t.getDescription());
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserException(-1, e.getMessage(), e);
		}
	}
	
	/**
	 * @return the unique task id
	 */ 
	public String getId() {
		return this.id;
	}
	
	/**
	 * @return the webservice (can be regular expression)
	 */
	public String getWebservice() {
		return this.webservice;
	}
	
	public void setWebservice(String s) {
		this.webservice = s;
	}
	
	/**
	 * @return the username that is used to run webservice
	 */
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String s) {
		this.username = s;
	}
	
	/**
	 * @return the password that is used to run webservice
	 */
	protected String getPassword() {
		return this.password;
	}
	
	protected void setPassword(String s) {
		this.password = s;
	}
	
	/**
	 * Set the unique task id
	 * 
	 * @param s unique id
	 */
	public void setId(String s) {
		this.id = s;
	}
	
	/**
	 * If set to true, flags the task for removal.
	 * 
	 * @param b
	 */
	public void setRemove(boolean b) {
		this.remove = b;
		try {
			JMXHelper.deregisterMXBean(JMXHelper.TASK_DOMAIN, getId());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "About to remove task: " + id);
		myTrigger.removeTrigger();
		if ( myThread != null && myThread.isAlive() ) {
			myThread.interrupt();
		}
	}
	
	/**
	 * If set to true, flags to task to be temporarily inactive
	 * @param b
	 */
	public void setInactive(boolean b) {
		this.inactive = b;
	}
	
	/**
	 * @return true if task is active
	 */
	public boolean isActive() {
		return !inactive;
	}
	
	/**
	 * @return true if task is running
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	public void setNavajo(Navajo n) {
		this.navajo = n;
		Header h = n.getHeader();
		if ( h != null ) {
			setUsername(h.getRPCUser());
			setPassword(h.getRPCPassword());
			setWebservice(h.getRPCName());
			h.setSchedule("");
		} else {
			System.err.println("Weird, empty header supplied for input navajo for task");
		}
	}
	
	public Navajo getNavajo() {
		return navajo;
	}
	
	/**
	 * Set the response for the webservice
	 * 
	 * @param r
	 */
	public void setResponse(Navajo r) {
		this.response = r;
	}
	
	/**
	 * Gets the response for the webservice
	 * 
	 * @return
	 */
	public Navajo getResponse() {
		return response;
	}
	
	/**
	 * The worker method for the task, keeps running until it gets flagged for removal.
	 */
	public void run() {
		
		JMXHelper.registerMXBean(this, JMXHelper.TASK_DOMAIN, getId());
		
		while (!remove) {
			
			// Sleep for a while.
			try {
				Thread.sleep(1000);
				//System.err.println("Task " + getId() + " listening for alarm: " + myTrigger.getDescription());
			} catch (Exception e) {
				if ( remove ) {
					AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Terminated task while sleeping: " + id);
					return;
				}
				e.printStackTrace(System.err);
			}
			
			if (myTrigger.alarm()) {
				
				Access access = myTrigger.getAccess();
				Navajo request = null;
				if ( access != null && navajo == null ) {
					request = ( myTrigger.swapInOut() ? access.getOutputDoc() :  access.getInDoc() );
				} else if ( navajo != null ) {
					request = navajo;
				} else {
					request = NavajoFactory.getInstance().createNavajo();
				} 
				
				isRunning = true;
				AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "(!!)Alarm goes off for task: " + id);
				java.util.Date now = new java.util.Date();
				
				Header h = request.getHeader();
				if (h == null) {
					h = NavajoFactory.getInstance().createHeader(request, webservice, username, password, -1);
					request.addHeader(h);
				} else {
					h.setRPCName(webservice);
					h.setRPCPassword(password);
					h.setRPCUser(username);
					h.setExpirationInterval(-1);
				}
				Dispatcher.getInstance().setUseAuthorisation(true);
				
				// Dispatcher is dead, exit.
				if ( Dispatcher.getInstance() == null ) {
					System.err.println("ERROR: Dead dispatcher, trying to execute task");
					return;
				}
				
				try {
					Navajo result = Dispatcher.getInstance().handle(request);
					this.setResponse(result);
				} catch (FatalException e) {
					e.printStackTrace(System.err);
					TaskRunner.log(this, null, true, e.getMessage(), now);
				} 

				TaskRunner.writeTaskOutput(this);
				TaskRunner.log(this, getResponse(), ( getResponse() != null && getResponse().getMessage("error") != null ), "", now );

				
				isRunning = false;		
				
				if ( myTrigger.isSingleEvent() ) {
					System.err.println(">>>>>>>>>>>>>> Single event task finished, removing everything");
					TaskRunner.getInstance().removeTaskInput(this);
					TaskRunner.getInstance().removeTask( this.getId() );
				} else {
					myTrigger.resetAlarm();		
					System.err.println("Reset alarm, issingleevent = " + myTrigger.isSingleEvent());
					System.err.println(">>>>>>>>>>>>>> Not a single event task finished, removing nothing");
				}
			}
			
		}
		try {
			JMXHelper.deregisterMXBean(JMXHelper.TASK_DOMAIN, getId());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
            // e.printStackTrace(System.err);
		}
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Terminated task: " + id);
	}
	
	public String getTriggerDescription() {
		return getTrigger().getDescription();
	}
	
	public static void main(String [] args) throws Exception {
		Task t = new Task("InitBM", "ROOT", "", null, "", null);
		t.run();
	}

	public TaskInterface getInstance() {
		return new Task();
	}
}
