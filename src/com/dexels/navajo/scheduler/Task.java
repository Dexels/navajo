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

public class Task implements Runnable {
	
	private String webservice;
	private String username;
	private String password;
	
    private Navajo request = null;
    private Dispatcher myDispatcher = null;
    private Trigger myTrigger = null;
    private boolean remove = false;
    private boolean inactive = false;
    private boolean isRunning = false;
    private String id = null;
    private Access myAccess = null;
    private Thread myThread = null;
    
	public Task(String webservice, String username, String password, Access a, Trigger t) {
		this.webservice = webservice;
		this.username = username;
		this.password = password;
		this.myTrigger = t;
		this.myAccess = a;
		if ( myAccess != null && myAccess.getDispatcher() != null ) {
			this.myDispatcher  = myAccess.getDispatcher();
		}
		request = NavajoFactory.getInstance().createNavajo();
	}
	
	protected void setThread(Thread t) {
		myThread = t;
	}
	
	public Trigger getTrigger() {
		return myTrigger;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getWebservice() {
		return this.webservice;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setId(String s) {
		this.id = s;
	}
	
	public void setRequest(Navajo in) {
		this.request = in;
	}
	
	public void setDispatcher(Dispatcher d) {
		this.myDispatcher = d;
	}
	
	public void setRemove(boolean b) {
		this.remove = b;
		System.err.println("REMOVING TRIGGER");
		myTrigger.removeTrigger();
		if ( myThread != null && myThread.isAlive() ) {
			myThread.interrupt();
		}
	}
	
	public void setInactive(boolean b) {
		this.inactive = b;
	}
	
	public boolean isActive() {
		return !inactive;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void run() {
		
		while (!remove) {
			
			// Sleep for a while.
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				if ( remove ) {
					System.err.println("Terminating task: " + id + ", tjuss.");
					return;
				}
				e.printStackTrace(System.err);
			}
			
			//System.err.println("Hello from task: " + id);
			
			if (myTrigger.alarm()) {
				try {	
					isRunning = true;
					System.err.println("ALARM GOES OFF for " + id);
					
					if ( myDispatcher != null ) {
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
						myDispatcher.setUseAuthorisation(true);
						Navajo result = myDispatcher.handle(request);
						
						System.err.println("RESULT OF TASK:");
						result.write(System.err);
					} else {
						// Dummy task
						Thread.sleep(5000);
					}
					isRunning = false;
				
					myTrigger.resetAlarm();
					
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}
			
		}
		System.err.println("Terminating task: " + id + ", tjuss.");
	}
	
	public static void main(String [] args) throws Exception {
		Trigger trig = new TimeTrigger(1, 25, 10, 43, null);
		Task t = new Task("InitBM", "ROOT", "", null, trig);
		t.run();
	}
}
