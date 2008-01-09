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

import java.util.Date;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

/**
 * A wrapper class for a Task. Wraps Task object to be used from a Navajo Script.
 * 
 * @author arjen
 *
 */
public class TaskMap implements Mappable {

	private Task myTask;
	
	public String id;
	public String webservice;
	public String username;
	public String trigger;
	public boolean active;
	public boolean running;
	public boolean finished;
	public Date startTime;
	public Date finishedTime;
	public String status;
	public String errorMessage;
	
	public TaskMap(Task t) {
		myTask = t;
		this.startTime = t.getStartTime();
		this.finishedTime = t.getFinishedTime();
		this.finished = t.isFinished();
		this.status = t.getStatus();
		this.errorMessage = t.getErrorMessage();
	}
	
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	public String getId() {
		return myTask.getId();
	}
	
	public String getWebservice() {
		return myTask.getWebservice();
	}
	
	public String getUsername() {
		return myTask.getUsername();
	}
	
	public String getTrigger() {
		return myTask.getTrigger().getDescription();
	}
	
	public boolean getRunning() {
		return myTask.isRunning();
	}
	
	public boolean getActive() {
		return myTask.isActive();
	}
	
	public String getTaskDescription(){
		return myTask.getTaskDescription();
	}
	
	public String getClientId(){
		return myTask.getClientId();
	}
	
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	public void kill() {
		// TODO Auto-generated method stub

	}

	public boolean getFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Date getFinishedTime() {
		return (Date) finishedTime.clone();
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = (Date) finishedTime.clone();
	}

	public Date getStartTime() {
		return (Date) startTime.clone();
	}

	public void setStartTime(Date startTime) {
		this.startTime = (Date) startTime.clone();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public Task getMyTask() {
		return myTask;
	}

}
