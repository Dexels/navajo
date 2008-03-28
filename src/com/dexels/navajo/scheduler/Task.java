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

import java.io.Serializable;
import java.util.Date;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.FatalException;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.scheduler.TaskInterface;
import com.dexels.navajo.sharedstore.SharedStoreLock;

/**
 * Defines the task object that describes among other things, the webservice
 * that needs to be triggered.
 * 
 * @author Arjen
 *
 */
public class Task implements Runnable, TaskMXBean, TaskInterface, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5450771508465465624L;
	
	public String webservice = "";
	public boolean proxy = false; // A task with a beforenavajo trigger can act as a proxy for the original webservice.
	public String username;
	public String password;
	public String trigger;
	public Navajo navajo = null;
	public Navajo response = null;
	public String status;
	public String errorMessage;
	public String workflowDefinition = null;
	public String workflowId = null;
	
    private Trigger myTrigger = null;
    @SuppressWarnings("unused")
	private boolean remove = false;
    private boolean inactive = false;
    private boolean isRunning = false;
    private boolean isFinished;
    private Date startTime = null;
    private Date finishedTime = null;
    private boolean keepRequestResponse = false;
    private String taskDescription = "";
    private String clientId = "";
    private String id = null;
    
    private boolean persisted = false;
    
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
		
		this.webservice = webservice;
		if ( username == null || password == null ) {
			throw new IllegalTask("No username/password specified");
		}
		this.username = username;
		this.password = password;
		this.myTrigger = Trigger.parseTrigger(triggerURL);
		if ( myTrigger != null ) {
			myTrigger.setTask(this);
		} else {
			System.err.println("Empty trigger for task.....");
		}
		this.navajo = requestNavajo;
		
		if(this.navajo != null){
			taskDescription = this.navajo.getHeader().getHeaderAttribute("description");
			clientId = this.navajo.getHeader().getHeaderAttribute("clientid");
		}
	}
	
	/**
	 * @return the trigger object that goes with this task.
	 */
	public Trigger getTrigger() {
		return myTrigger;
	}
	
	/**
	 * Sets the trigger for the task, if trigger url is invalid exception is thrown.
	 * 
	 */
	public void setTrigger(String s) throws UserException {
		try {
			// Check for old trigger.
			if ( getTrigger() != null ) {
				getTrigger().removeTrigger();
			}
			Trigger t = Trigger.parseTrigger(s);
			myTrigger = t;
			myTrigger.setTask(this);
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
	 * @return the webservice 
	 */
	public String getWebservice() {
		return this.webservice;
	}
	
	public String getClientId(){
		return this.clientId;
	}
	
	public String getTaskDescription(){
		return this.taskDescription;
	}
	
	/**
	 * Sets the webservice for this task
	 * 
	 * @param s
	 */
	public void setWebservice(String s) {
		this.webservice = s;
	}
	
	/**
	 * @return the username that is used to run webservice
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Sets the username for this task.
	 * 
	 * @param s
	 */
	public void setUsername(String s) {
		this.username = s;
	}
	
	/**
	 * @return the password that is used to run webservice
	 */
	protected String getPassword() {
		return this.password;
	}
	
	/**
	 * Sets the password for the user of this task.
	 * 
	 * @param s
	 */
	public void setPassword(String s) {
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
	
	public void setClientId(String s){
		this.clientId = s;
	}
	
	public void setTaskDescription(String s){
		this.taskDescription = s;
	}
	
	/**
	 * If set to true, flags the task for removal.
	 * If removePersistedTask is set to true, the task is also removed from tasks.xml (if task needs persistence )
	 * 
	 * @param b
	 */
	public void setRemove(boolean b, boolean removePersistedTask) {
		this.remove = b;
		//AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "About to remove task: " + id);
		if ( myTrigger != null ) {
			//System.err.println("removing trigger: " + myTrigger);
			myTrigger.removeTrigger();
			//System.err.println("done!");
			if ( removePersistedTask && needsPersistence() ) {
				//System.err.println("TaskRunner in setRemove(" + b + "," + removePersistedTask + "): waiting for sharedstore lock...");
				SharedStoreLock ssl = TaskRunner.getInstance().getConfigLock();
				//System.err.println("TaskRunner in setRemove(): got shared store lock");
				try {
					Navajo n = TaskRunner.getInstance().readConfig(false, true);
					Message msgs = n.getMessage("tasks");
					for (int i = 0; i < msgs.getArraySize(); i++) {
						Message m = (Message) msgs.getMessage(i);
						if ( m.getProperty("id").getValue().equals(getId()) ) {
							msgs.removeMessage(m);
							i = msgs.getArraySize() + 1;
						}
					}
					//System.err.println("TaskRunner in setRemove(): about to write task config");
					TaskRunner.getInstance().writeTaskConfig(n);
					//System.err.println("TaskRunner in setRemove(): removed task config");
				} finally {
					//System.err.println("TaskRunner in setRemove(): releasing sharedstore lock..");
					TaskRunner.getInstance().releaseConfigLock(ssl);
					//System.err.println("TaskRunner in setRemove(): done!");
				}
			}
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
	 * Sets the 'request' navajo for this task, can be empty. User username, password and webservice from header 
	 * to set respective values.
	 * 
	 */
	public void setNavajo(Navajo n) {
		this.navajo = n.copy();
		Header h = n.getHeader();
		if ( h != null ) {
			setUsername(h.getRPCUser());
			setPassword(h.getRPCPassword());
			setWebservice(h.getRPCName());
			h.setSchedule("");
			taskDescription = h.getHeaderAttribute("description");
			clientId = h.getHeaderAttribute("clientid");
			if("true".equals(h.getHeaderAttribute("keeprequestresponse"))){
				keepRequestResponse = true;
			}
		} else {
			System.err.println("Weird, empty header supplied for input navajo for task");
		}
	}
	
	/**
	 * Set request Navajo to be used by this task.
	 * 
	 * @param n
	 */
	public void setRequest(Navajo n) {
		this.navajo = n.copy();
		if ( navajo != null ) {
			navajo.removeHeader();
		}
	}
	
	/**
	 * Gets the request Navajo for this task.
	 * 
	 * @return
	 */
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

		//AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, " trigger " + getTriggerDescription() + " activated task: " + getId() );
		
		Navajo result = null;
		
		// Invoke onbefore triggers.
		// If the fireBeforeTaskEvent returns false and webservice was proxy, do not execute this webservice. 
		// If webservice was not a proxy, continue as normal.
		
		
		Navajo request = null;
		Access access = myTrigger.getAccess();
		
		if ( access != null && navajo == null ) {
			request = ( myTrigger.swapInOut() ? access.getOutputDoc() :  access.getInDoc() );
		} else if ( navajo != null ) {
			request = navajo;
		} else {
			request = NavajoFactory.getInstance().createNavajo();
		} 
		
		boolean resultOfBeforeTaskEvent = TaskRunner.getInstance().fireBeforeTaskEvent(this, request );
		
		// If Proxy and some boolean resultOfBeforeTaskEvent equals to true enter this block
		// If not a proxy, always enter this block...
		if ( ( resultOfBeforeTaskEvent && isProxy() ) || !isProxy() ) {

			if ( webservice != null && !webservice.equals("") ) {
				
				isRunning = true;

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
				// Reset request id to prevent integrity worker from kicking in.
				h.setRequestId(null);

				//Dispatcher.getInstance().setUseAuthorisation(false);

				// Dispatcher is dead, exit.
				if ( Dispatcher.getInstance() == null ) {
					System.err.println("ERROR: Dead dispatcher, trying to execute task");
					return;
				}

				try {
					result = Dispatcher.getInstance().handle(request);
					this.setResponse(result);
				} catch (FatalException e) {
					e.printStackTrace(System.err);
					TaskRunner.getInstance().log(this, null, true, e.getMessage(), now);
				} 
				TaskRunner.getInstance().log(this, getResponse(), ( getResponse() != null && getResponse().getMessage("error") != null ), 
						( getResponse().getMessage("error") != null ? getResponse().getMessage("error").getProperty("message").getValue() : ""), 
						now );	
			}

		} else {
			setResponse(null);
		}
		
		isRunning = false;		

		if ( myTrigger.isSingleEvent() ) {
			TaskRunner.getInstance().removeTask( this.getId(), true );
		} 
		
		if ( !keepRequestResponse ) {
			TaskRunner.getInstance().removeTaskInput(this);
		} else if ( getResponse() != null ){
			TaskRunner.getInstance().writeTaskOutput(this, myTrigger.isSingleEvent(), System.currentTimeMillis() );
		}
		
        // Invoke after triggers.
		TaskRunner.getInstance().fireAfterTaskEvent(this, result);
		
		//AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Terminated task: " + id);
	}
	
	public String getTriggerDescription() {
		return getTrigger().getDescription();
	}
	
	public static void main(String [] args) throws Exception {
		Task t = new Task("InitBM", "ROOT", "", null, "", null);
		t.run();
	}

	/**
	 * Creates a new TaskInterface for compatability with Navajo Standard Edition.
	 * 
	 */
	public TaskInterface getInstance() {
		return new Task();
	}

	/**
	 * return true if task is finished.
	 * 
	 * @return
	 */
	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * Sets the finished status
	 * 
	 * @param isFinished
	 */
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	/**
	 * Returns the timestamp when task was finished.
	 * 
	 * @return
	 */
	public Date getFinishedTime() {
		if (  finishedTime != null ) {
			return (Date) finishedTime.clone();
		} else {
			return null;
		}
	}

	/**
	 * Sets the timestamp when task was finished.
	 * 
	 * @param finishedTime
	 */
	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = ( finishedTime != null ? (Date) finishedTime.clone() : null );
	}

	/**
	 * Gets the timestamp when task was started.
	 * 
	 * @return
	 */
	public Date getStartTime() {
		if ( startTime != null ) {
			return (Date) startTime.clone();
		} else {
			return null;
		}
	}

	/**
	 * Sets the timestamp when task was started.
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = ( startTime != null ? (Date) startTime.clone() : null );
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorkflowDefinition() {
		return workflowDefinition;
	}

	public void setWorkflowDefinition(String workflowDefinition) {
		if ( workflowDefinition != null && !workflowDefinition.equals("" )) {
			this.workflowDefinition = workflowDefinition;
		}
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		if ( workflowDefinition != null && !workflowDefinition.equals("")) {
			this.workflowId = workflowId;
		}
	}

	public boolean isProxy() {
		return proxy;
	}

	public void setProxy(boolean proxy) {
		this.proxy = proxy;
	}

	public boolean isKeepRequestResponse() {
		return keepRequestResponse;
	}

	public void setKeepRequestResponse(boolean keepRequestResponse) {
		this.keepRequestResponse = keepRequestResponse;
	}

	public boolean isPersisted() {
		return persisted;
	}

	public boolean needsPersistence() {
		return ( this.isPersisted() || 
			( !this.getTrigger().isSingleEvent() && ( this.getWorkflowDefinition() == null || this.getWorkflowDefinition().equals("") ) ) );
	}
	
	public void setPersisted(boolean persisted) {
		this.persisted = persisted;
	}
}
