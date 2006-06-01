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

import java.io.StringWriter;
import java.util.Date;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.AuditLog;

/**
 * Defines the task object that describes among other things, the webservice
 * that needs to be triggered.
 * 
 * @author Arjen
 *
 */
public class Task implements Runnable {
	
	public String webservice;
	public String username;
	public String password;
	public String trigger;
	
    private Trigger myTrigger = null;
    private boolean remove = false;
    private boolean inactive = false;
    private boolean isRunning = false;
    private String id = null;
    private Access myAccess = null;
    private Thread myThread = null;
    
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
				String triggerURL) throws IllegalTrigger, IllegalTask {
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
		this.myAccess = a;
	
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
	
	/**
	 * The worker method for the task, keeps running until it gets flagged for removal.
	 */
	public void run() {
		
		while (!remove) {
			
			// Sleep for a while.
			try {
				Thread.sleep(1000);
				//System.err.println("Task " + getId() + " listening for alarm: " + myTrigger.getDescription());
			} catch (Exception e) {
				if ( remove ) {
					AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Terminated task: " + id);
					return;
				}
				e.printStackTrace(System.err);
			}
			
			if (myTrigger.alarm()) {
				
				Access access = myTrigger.getAccess();
				Navajo request = null;
				if ( access != null ) {
					request = ( myTrigger.swapInOut() ? access.getOutputDoc() :  access.getInDoc() );
				} 
				if (request == null ) {
					request = NavajoFactory.getInstance().createNavajo();
				}
				try {	
					isRunning = true;
					AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Alarm goes off for task: " + id);
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
					
						Navajo result = Dispatcher.getInstance().handle(request);
						// Log result if error.
						if ( result != null && result.getMessage("error") != null ) {		
							TaskRunner.log(this, result, true, now );
						} else {
							TaskRunner.log(this, result, false, now );
						}
					
					isRunning = false;
				
					myTrigger.resetAlarm();
					
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}
			
		}
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Terminated task: " + id);
	}
	
	public static void main(String [] args) throws Exception {
		Task t = new Task("InitBM", "ROOT", "", null, "");
		t.run();
	}
}
