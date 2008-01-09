/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005, 2006, 2007</p>
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
import java.util.HashMap;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.util.AuditLog;

public class WebserviceTrigger extends Trigger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1782109779749326472L;
	
	public String webservicePattern;
	public String myDescription;
	
	private boolean alarm = false;
	private HashMap<String,String> commands = new HashMap<String,String>();
	
	private boolean singleEvent = false;
	
	public WebserviceTrigger(String description) {
		
		myDescription = description;
		int parmIndex = myDescription.indexOf("?");
		if ( parmIndex != -1 ) {
			//System.err.println("Contains parameters");
			String paramlist = myDescription.substring(parmIndex+1);
			//System.err.println("Parsing params: " + paramlist);
			StringTokenizer t = new StringTokenizer(paramlist, "&");
			while ( t.hasMoreTokens() ) {
				StringTokenizer t2 = new StringTokenizer(t.nextToken(), "=");
				String key = t2.nextToken();
				String value = t2.nextToken();
				commands.put(key, value);
				if ( key.equals("doc") && value.equals("out") ) {
					//System.err.println("Swapping request/response");
					setSwapInOut(true);
				}
			}
			webservicePattern = myDescription.substring(0, parmIndex);
		} else {
			webservicePattern	= myDescription;
		}
		//System.err.println("webservice = " + webservice);
	
	}
	
	public boolean isSingleEvent() {
		return singleEvent;
	}
	
	public String getCommand(String c) {
		return (String) commands.get(c);
	}
	
	public void removeTrigger() {
		// Remove myself from the listener list.
		WebserviceListenerRegistry.getInstance().removeTrigger(this);
	}
	
	public void setAlarm() {
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Webservice trigger for pattern " + webservicePattern + " goes off.");
		alarm = true;
	}
	
	public boolean alarm() {
		return alarm;
	}

	public void resetAlarm() {
		alarm = false;
	}
	
	public String getDescription() {
		return Trigger.WS_TRIGGER + ":" + myDescription;
	}
	
	public String getWebservicePattern() {
		return webservicePattern;
	}

	public void setSingleEvent(boolean b) {
		singleEvent = b;
	}

	public Navajo perform() {
		
        // Spawn task asynchronously. If there is no web service to run, invoke task synchronously.
		if ( getTask().getWebservice() != null ) {
			
			GenericThread taskThread = new GenericThread("task:" + getTask().getId() ) {

				public void run() {
					try {
						worker();
					} finally {
						finishThread();
					}
				}

				public final void worker() {
					getTask().run();
				}
			};
			taskThread.startThread(taskThread);
		} else {
			// Invoke task synchronously to support work flow before and after task trigger synchronously.
			try {
				getTask().run();
			} catch (Throwable t2) {}
		}
		return null;
	}
	
	public void activateTrigger() {
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Activating webservice trigger: " + myDescription);	
		WebserviceListenerRegistry.getInstance().registerTrigger(this);
	}
}
