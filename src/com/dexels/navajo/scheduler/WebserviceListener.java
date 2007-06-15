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

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerInterface;


/**
 * This class is responsible for keeping track of all existing webservice
 * triggers. It is responsible for settting the webservice trigger alarm when
 * a matching webservice is invoked.
 * 
 */
public class WebserviceListener implements WebserviceListenerInterface {

	static WebserviceListener instance = null;
	
	private Set afterTriggers = null;
	private Set beforeTriggers = null;
	
	public static WebserviceListener getInstance() {
		if ( instance == null ) {
			instance = new WebserviceListener();
			instance.afterTriggers = Collections.synchronizedSet(new HashSet());
			instance.beforeTriggers = Collections.synchronizedSet(new HashSet());
		}
		return instance;
	}
	
	public void registerBeforeTrigger(BeforeWebserviceTrigger t) {
		beforeTriggers.add(t);
	}
	
	public final void removeBeforeTrigger(BeforeWebserviceTrigger t) {
		beforeTriggers.remove(t);
	}
	
	/**
	 * Register a new trigger to the listener list.
	 * 
	 * @param t the trigger object to be registered
	 */
	public void registerTrigger(WebserviceTrigger t) {
		afterTriggers.add(t);
	}
	
	/**
	 * Removes a webservice trigger from the listener list.
	 * 
	 * @param t the trigger object that needs to be removed.
	 */
	public final void removeTrigger(WebserviceTrigger t) {
		afterTriggers.remove(t);
	}
	
	/**
	 * Method to indicate listener that webservice was(!) invoked.
	 * If there is a matching trigger (having a matching pattern), the trigger
	 * alarms gets set.
	 * 
	 * @param webservice the name of the webservice that was invoked
	 * @param a the access object of the caller
	 */
	public final void afterWebservice(final String webservice, final Access a) {
		// First create copy of triggers set to prevent concurrent modification exceptions.
		HashSet copyOfTriggers = new HashSet(afterTriggers);
		// Iterate over copy.
		Iterator iter = copyOfTriggers.iterator();
		while ( iter.hasNext() ) {
			
			final WebserviceTrigger t = (WebserviceTrigger) iter.next();
			
			if ( webservice.matches(t.getWebservicePattern()) ) {
				System.err.println("match AFTER invocation(" + webservice + ") for webservicepattern: " + t.getWebservicePattern());
				t.setAccess(a);
				// Spawn task.
				GenericThread taskThread = new GenericThread("task:" + t.getTask().getId() ) {

					public void run() {
						try {
							worker();
						} finally {
							finishThread();
						}
					}

					public final void worker() {
						t.getTask().run();
					}
				};
				taskThread.startThread(taskThread);
			}	
		}
	}

	public Navajo beforeWebservice(String webservice, Access a) {
		// First create copy of triggers set to prevent concurrent modification exceptions.
		HashSet copyOfTriggers = new HashSet(beforeTriggers);
		// Iterate over copy.
		Iterator iter = copyOfTriggers.iterator();
		System.err.println("CALLING beforeWebservice(" + webservice + ")");
		while ( iter.hasNext() ) {

			final BeforeWebserviceTrigger t = (BeforeWebserviceTrigger) iter.next();
			System.err.println("BeforeWebserviceTrigger: " + t.getWebservicePattern());
			
			if ( webservice.matches(t.getWebservicePattern()) ) {
				System.err.println("match BEFORE invocation(" + webservice + ") for webservicepattern: " + t.getWebservicePattern());
				t.setAccess(a);
				// Run task (synchronously!).
				t.getTask().run();
				// If task was proxy webservice, return result of this task immediately.
				if ( t.getTask().isProxy() && t.getTask().getResponse() != null ) {
					System.err.println("RETURNING NAVAJO OF TASK AS PROXY RESPONSE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
					a.rpcName = t.getTask().getWebservice();
					a.setOutputDoc(t.getTask().getResponse());
					return t.getTask().getResponse();
				} else {
					System.err.println("DID NOT RUN PROXY..........");
				}
			}	
		}
		
		return null;
	}
}
