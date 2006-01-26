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

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

import com.dexels.navajo.server.Access;


/**
 * This class is responsible for keeping track of all existing webservice
 * triggers. It is responsible for settting the webservice trigger alarm when
 * a matching webservice is invoked.
 * 
 */
public class WebserviceListener {

	static WebserviceListener instance = null;
	
	private Set triggers = null;
	
	public static WebserviceListener getInstance() {
		if ( instance == null ) {
			instance = new WebserviceListener();
			instance.triggers = Collections.synchronizedSet(new HashSet());
		}
		return instance;
	}
	
	/**
	 * Register a new trigger to the listener list.
	 * 
	 * @param t the trigger object to be registered
	 */
	public void registerTrigger(WebserviceTrigger t) {
		triggers.add(t);
	}
	
	/**
	 * Removes a webservice trigger from the listener list.
	 * 
	 * @param t the trigger object that needs to be removed.
	 */
	public void removeTrigger(WebserviceTrigger t) {
		triggers.remove(t);
	}
	
	/**
	 * Method to indicate listener that webservice was(!) invoked.
	 * If there is a matching trigger (having a matching pattern), the trigger
	 * alarms gets set.
	 * 
	 * @param webservice the name of the webservice that was invoked
	 * @param a the access object of the caller
	 */
	public void invocation(String webservice, Access a) {
		Iterator iter = triggers.iterator();
		while ( iter.hasNext() ) {
			WebserviceTrigger t = (WebserviceTrigger) iter.next();
		    if ( webservice.matches(t.getWebservice()) ) {
				t.setAccess(a);
				t.setAlarm();
			}	
		}
	}
}
