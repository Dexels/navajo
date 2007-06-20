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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
public final class WebserviceListener implements WebserviceListenerInterface {

	static WebserviceListener instance = null;
	
	private Set afterTriggers = null;
	private Set beforeTriggers = null;
	
	/**
	 * Sets to store webservices that are listened to.
	 */
	private Map beforeWebservices = null;
	private Map afterWebservices = null;
	
	public final static WebserviceListener getInstance() {
		if ( instance == null ) {
			instance = new WebserviceListener();
			instance.afterTriggers = Collections.synchronizedSet(new HashSet());
			instance.beforeTriggers = Collections.synchronizedSet(new HashSet());
			instance.beforeWebservices = Collections.synchronizedMap(new HashMap());
			instance.afterWebservices = Collections.synchronizedMap(new HashMap());
		}
		return instance;
	}
	
	public final void registerBeforeTrigger(BeforeWebserviceTrigger t) {
		beforeTriggers.add(t);
		Integer i = (Integer) beforeWebservices.get(t.getWebservicePattern());
		if ( i == null ) {
			i = new Integer(1);
		} else {
			i = new Integer(i.intValue() + 1);
		}
		beforeWebservices.put(t.getWebservicePattern(), i);
	}
	
	public final void removeBeforeTrigger(BeforeWebserviceTrigger t) {
		beforeTriggers.remove(t);
		Integer i = (Integer) beforeWebservices.get(t.getWebservicePattern());
		if ( i == null ) {
			return;
		}
		i = new Integer(i.intValue() -1 );
		if ( i.intValue() > 0 ) {
			beforeWebservices.put(t.getWebservicePattern(), i);
		} else {
			beforeWebservices.remove(t.getWebservicePattern());
		}
	}
	
	/**
	 * Register a new trigger to the listener list.
	 * 
	 * @param t the trigger object to be registered
	 */
	public final void registerTrigger(WebserviceTrigger t) {
		afterTriggers.add(t);
		Integer i = (Integer) afterWebservices.get(t.getWebservicePattern());
		if ( i == null ) {
			i = new Integer(1);
		} else {
			i = new Integer(i.intValue() + 1);
		}
		afterWebservices.put(t.getWebservicePattern(), i);
	}
	
	/**
	 * Removes a webservice trigger from the listener list.
	 * 
	 * @param t the trigger object that needs to be removed.
	 */
	public final void removeTrigger(WebserviceTrigger t) {
		afterTriggers.remove(t);
		Integer i = (Integer) afterWebservices.get(t.getWebservicePattern());
		if ( i == null ) {
			return;
		}
		i = new Integer(i.intValue() -1 );
		if ( i.intValue() > 0 ) {
			afterWebservices.put(t.getWebservicePattern(), i);
		} else {
			afterWebservices.remove(t.getWebservicePattern());
		}
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

		// Return immediately if webservice is not contained in afterWebservices set, i.e. there is
		// no listener interested in this webservice.
		if ( afterWebservices.get(webservice) == null ) {
			return;
		} 
		
		// First create copy of triggers set to prevent concurrent modification exceptions.
		HashSet copyOfTriggers = new HashSet(afterTriggers);
		// Iterate over copy.
		Iterator iter = copyOfTriggers.iterator();
		while ( iter.hasNext() ) {

			final WebserviceTrigger t = (WebserviceTrigger) iter.next();

			if ( webservice.equals(t.getWebservicePattern()) ) {
				t.setAccess(a);

                // Spawn task asynchronously, if there is no webservice to run, invoke task sychronously.
				if ( t.getTask().getWebservice() != null ) {
					
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
				} else {
					// Invoke task synchronously to support workflow before and after task trigger synchronously.
					t.getTask().run();
				}
			}
		}
	}

	
	public final Navajo beforeWebservice(String webservice, Access a) {
		
        // Return immediately if webservice is not contained in beforeWebservices set, i.e. there is
		// no listener interested in this webservice.
		if ( beforeWebservices.get(webservice) == null ) {
			return null;
		}
		
		// First create copy of triggers set to prevent concurrent modification exceptions.
		HashSet copyOfTriggers = new HashSet(beforeTriggers);
		// Iterate over copy.
		Iterator iter = copyOfTriggers.iterator();
		while ( iter.hasNext() ) {

			final BeforeWebserviceTrigger t = (BeforeWebserviceTrigger) iter.next();

			if ( webservice.equals(t.getWebservicePattern()) ) {
				t.setAccess(a);
				// Run task (synchronously if proxy).
				if ( t.getTask().isProxy() ) {
					t.getTask().run();
					// If task was proxy webservice, return result of this task immediately.
					if ( t.getTask().getResponse() != null ) {
						a.rpcName = t.getTask().getWebservice();
						a.setOutputDoc(t.getTask().getResponse());
						return t.getTask().getResponse();
					}
				} else { // There is no proxy webservice defined.
					// Spawn task asynchronously if there is a webservice defined.
					if ( t.getTask().getWebservice() != null ) {
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
					} else {
                        //	Invoke task synchronously to support workflow before and after task trigger synchronously.
						t.getTask().run();
					}

					return null;
				}

			}	
		}

		return null;
	}
}
