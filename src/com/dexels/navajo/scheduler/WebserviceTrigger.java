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

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;

public class WebserviceTrigger extends Trigger {

	public String webservice;
	public String myDescription;
	
	private boolean alarm = false;
	private WebserviceListener myListener = null;
	
	public WebserviceTrigger(String description, WebserviceListener listener) {
		System.err.println("Creating webservicetrigger: " + description);
		myDescription = description;
		webservice = description;
		myListener = listener;
	}
	
	public void removeTrigger() {
		myListener.removeTrigger(this);
	}
	
	public void setAlarm() {
		System.err.println("Webservice: " + webservice + " called!");
		alarm = true;
	}
	
	public boolean alarm() {
		return alarm;
	}

	public void resetAlarm() {
		alarm = false;
	}
	
	public String getDescription() {
		return myDescription;
	}
	
	public String getWebservice() {
		return webservice;
	}

}
