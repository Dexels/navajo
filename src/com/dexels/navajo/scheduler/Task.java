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
    
	public Task(String webservice, String username, String password, Access a, Trigger t) {
		this.webservice = webservice;
		this.username = username;
		this.password = password;
		this.myTrigger = t;
		this.myAccess = a;
		this.myDispatcher  = myAccess.getDispatcher();
		request = NavajoFactory.getInstance().createNavajo();
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String s) {
		this.id = s;
	}
	
	public void setRequest(Navajo in) {
		this.request = in;
	}
	
	public void setRemove(boolean b) {
		this.remove = b;
	}
	
	public void setInactive(boolean b) {
		this.inactive = b;
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
				e.printStackTrace(System.err);
			}
			
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
						Navajo result = myDispatcher.handle(request, null);
						
						System.err.println("RESULT OF TASK:");
						result.write(System.err);
					} else {
						// Dummy task
						Thread.sleep(5000);
					}
					isRunning = false;
					
					// Sleep for long time, does not need to fire alarm until at least an hour again.
					Thread.sleep(100000);
					
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}
			
		}
	}
	
	public static void main(String [] args) throws Exception {
		Trigger trig = new TimeTrigger(1, 25, 10, 43, null);
		Task t = new Task("InitBM", "ROOT", "", null, trig);
		t.run();
	}
}
