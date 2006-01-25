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

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class TaskMap implements Mappable {

	// Identification.
	public String id;
	public String webservice;
	public String timeTrigger;
	
	// Actions
	public boolean start;
	public boolean remove;
	
	private Access myAccess;
	private Navajo myRequest;
	private Trigger myTrigger;
	
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		myAccess = access;
		myRequest = inMessage;
	}

	public void setTimeTrigger(String s) {
		this.timeTrigger = s;
		myTrigger = new TimeTrigger(timeTrigger);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setWebservice(String webservice) {
		this.webservice = webservice;
	}
	
	public void setRemove(boolean b) {
		if ( !b || id == null ) {
			return;
		}
		TaskRunner tr = TaskRunner.getInstance();
		tr.removeTask(id);
	}
	
	public void setStart(boolean b) throws MappableException, UserException {
		
		if ( !b || id == null || webservice == null || myTrigger == null ) {
			return;
		}
		
		Task myTask = new Task(webservice, myAccess.rpcUser, myAccess.rpcPwd, myAccess, myTrigger);
		myTask.setRequest(myRequest);
		
		TaskRunner tr = TaskRunner.getInstance();
		tr.addTask(id, myTask);
	}

	public void store() throws MappableException, UserException {
		
	}
	
	public void kill() {
		
	}

}
