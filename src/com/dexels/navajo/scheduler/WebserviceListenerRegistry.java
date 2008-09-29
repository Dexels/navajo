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

import java.util.HashMap;
import java.util.HashSet;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.scheduler.tribe.ServiceEventsSmokeSignal;
import com.dexels.navajo.scheduler.triggers.AfterWebserviceTrigger;
import com.dexels.navajo.scheduler.triggers.BeforeWebserviceTrigger;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerRegistryInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.workflow.WorkFlowManager;


/**
 * This class is responsible for keeping track of all existing webservice
 * triggers. It is responsible for settting the webservice trigger alarm when
 * a matching webservice is invoked.
 * 
 */
public final class WebserviceListenerRegistry implements WebserviceListenerRegistryInterface {

	static volatile WebserviceListenerRegistry instance = null;
	private final HashMap<String,Integer> registeredWebservices = new HashMap<String,Integer>();
	private static Object semaphore = new Object();
	
	/**
	 * Register already persisted web service triggers from the ListenerStore.
	 */
	private void init() {
		// Register already present webservices.
		Listener [] l =  ListenerStore.getInstance().getListeners(AfterWebserviceTrigger.class.getName());
		for (int i = 0; i < l.length; i++) {
			addRegisteredWebservice( ((AfterWebserviceTrigger) l[i]).getWebservicePattern());
		}
		Listener [] lb = ListenerStore.getInstance().getListeners(BeforeWebserviceTrigger.class.getName());
		for (int i = 0; i < lb.length; i++) {
			addRegisteredWebservice( ((BeforeWebserviceTrigger) lb[i]).getWebservicePattern());
		}
	}
	
	public final static WebserviceListenerRegistry getInstance() {
		if ( instance != null) {
			return instance;
		}
		synchronized (semaphore) {
			if ( instance == null ) {
				instance = new WebserviceListenerRegistry();
				instance.init();
			}
		}
		return instance;
	}
	
	/**
	 * Method registers a beforewebservice trigger. Registration is an atomic action: when method is finished, each tribe member
	 * has some trigger state.
	 * 
	 * @param t
	 */
	public final void registerBeforeTrigger(BeforeWebserviceTrigger t) {

		ListenerStore.getInstance().addListener(t);
		// Broadcast..
		TribeManagerFactory.getInstance().broadcast(new ServiceEventsSmokeSignal(DispatcherFactory.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.ADD_WEBSERVICE, t.getWebservicePattern()));
		addRegisteredWebservice(t.getWebservicePattern());
		
	}
	
	public final void removeBeforeTrigger(BeforeWebserviceTrigger t) {

		ListenerStore.getInstance().removeListener(t);
		// Broadcast..
		TribeManagerFactory.getInstance().broadcast(new ServiceEventsSmokeSignal(DispatcherFactory.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.REMOVE_WEBSERVICE, t.getWebservicePattern()));
		removeRegisteredWebservice(t.getWebservicePattern());
		
	}
	
	/**
	 * Register a new trigger to the listener list.
	 * 
	 * @param t the trigger object to be registered
	 */
	public final void registerTrigger(AfterWebserviceTrigger t) {

		ListenerStore.getInstance().addListener(t);
		// Broadcast..
		TribeManagerFactory.getInstance().broadcast(new ServiceEventsSmokeSignal(DispatcherFactory.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.ADD_WEBSERVICE, t.getWebservicePattern()));
		addRegisteredWebservice(t.getWebservicePattern());
		
	}
	
	/**
	 * Removes a webservice trigger from the listener list.
	 * 
	 * @param t the trigger object that needs to be removed.
	 */
	public final void removeTrigger(AfterWebserviceTrigger t) {

		ListenerStore.getInstance().removeListener(t);
		removeRegisteredWebservice(t.getWebservicePattern());
		// Broadcast..
		TribeManagerFactory.getInstance().broadcast(new ServiceEventsSmokeSignal(DispatcherFactory.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.REMOVE_WEBSERVICE, t.getWebservicePattern()));

	}
	
	/**
	 * Method to indicate listener that webservice was(!) invoked.
	 * If there is a matching trigger (having a matching pattern), the trigger
	 * alarms gets set.
	 * 
	 * @param webservice the name of the webservice that was invoked
	 * @param a the access object of the caller
	 */
	public final void afterWebservice(String webservice, Access a) {
		afterWebservice(webservice, a, new HashSet(), false);
	}
	
	public final void afterWebservice(String webservice, Access a, HashSet<String> ignoreTaskList, boolean locally) {

		
		// Return immediately if webservice is not contained in afterWebservices set, i.e. there is
		// no listener interested in this webservice.
		if ( !isRegisteredWebservice(webservice) ) {
			return;
		} 

		long start = System.currentTimeMillis();
		long count = 0;
		
		Listener [] all = ListenerStore.getInstance().getListeners(AfterWebserviceTrigger.class.getName());
		HashSet<String> ignoreTheseTaskOnOtherMembers = new HashSet<String>();
		boolean leftOvers = false;
		
		for (int i = 0; i < all.length; i++) {
			try {
			AfterWebserviceTrigger cl = (AfterWebserviceTrigger) all[i];
			if ( cl.getWebservicePattern().equals(webservice) && !ignoreTaskList.contains(cl.getTask().getId()) ) {

				AfterWebserviceTrigger t2 = (AfterWebserviceTrigger) cl.clone();
				t2.setAccess(a);
				boolean initializingWorkflow = ( t2.getTask().getWorkflowDefinition() != null && t2.getTask().getWorkflowId() == null );
				boolean myWorkflow = ( t2.getTask().getWorkflowId() != null && WorkFlowManager.getInstance().hasWorkflowId(t2.getTask().getWorkflowId()));
				boolean isWorkflow = ( t2.getTask().getWorkflowDefinition() != null );
				
				if ( !isWorkflow || initializingWorkflow || myWorkflow) {
					t2.perform();
					ignoreTheseTaskOnOtherMembers.add(t2.getTask().getId());
					count++;
				} else {
					leftOvers = true;
				}
			}
			} catch (Throwable tr) { tr.printStackTrace(System.err); } 
		}
		
		if ( !locally && leftOvers ) {
			TribeManagerFactory.getInstance().tribalAfterWebServiceRequest(webservice, a, ignoreTheseTaskOnOtherMembers);
		}
		
		//AuditLog.log("SERVICEVENT", ":  afterWebservice(" + webservice + ") took " + ( System.currentTimeMillis() - start ) + " millis." + ", locally = " + locally + ", processed = " + count + ", leftOvers =" + leftOvers);
		
	}

	
	/**
	 * Method to indicate listener that webservice is about te be(!) invoked.
	 * If there is a matching trigger (having a matching pattern), the trigger
	 * alarms gets set.
	 * 
	 * @param webservice the name of the webservice that was invoked
	 * @param a the access object of the caller
	 * @returns the Navajo in case the task was defined as being a proxy.
	 */
	
	public final Navajo beforeWebservice(String webservice, Access a) {
		return beforeWebservice(webservice, a,  new HashSet(), false);
	}
	
	public final Navajo beforeWebservice(String webservice, Access a, HashSet<String> ignoreTaskList, boolean locally) {

		// Return immediately if webservice is not contained in beforeWebservices set, i.e. there is
		// no listener interested in this webservice.
		if ( !isRegisteredWebservice(webservice) ) {
			return null;
		} 

		long start = System.currentTimeMillis();
		long count = 0;
		boolean leftOvers = false;
		
		try {
			Listener [] all = ListenerStore.getInstance().getListeners(BeforeWebserviceTrigger.class.getName());

			HashSet<String> ignoreTheseTaskOnOtherMembers = new HashSet<String>();
	
			for (int i = 0; i < all.length; i++) {

				try {
					BeforeWebserviceTrigger cl = (BeforeWebserviceTrigger) all[i];

					if ( cl.getWebservicePattern().equals(webservice) && !ignoreTaskList.contains(cl.getTask().getId()) ) {
						BeforeWebserviceTrigger t2 = (BeforeWebserviceTrigger) cl.clone();
						t2.setAccess(a);

						boolean initializingWorkflow = ( t2.getTask().getWorkflowDefinition() != null && t2.getTask().getWorkflowId() == null );
						boolean myWorkflow = ( t2.getTask().getWorkflowId() != null && WorkFlowManager.getInstance().hasWorkflowId(t2.getTask().getWorkflowId()));
						boolean isWorkflow = ( t2.getTask().getWorkflowDefinition() != null );

						Navajo n = null;
						if ( !isWorkflow || initializingWorkflow || myWorkflow) { // If this is NOT a workflow task or an initializing workflow task or                                                   
							// my workflow task, perform locally.
							n = t2.perform();
							ignoreTheseTaskOnOtherMembers.add(t2.getTask().getId());
							if ( t2.getTask().isProxy() ) {
								return n;
							}
							count++;
						} else {
							leftOvers = true;
						}
					}
				} catch (Throwable tr) { tr.printStackTrace(System.err); } 
			}

			// Try other tribal members...
			if ( !locally && leftOvers ) {
				return TribeManagerFactory.getInstance().tribalBeforeWebServiceRequest(webservice, a, ignoreTheseTaskOnOtherMembers);
			} else {
				return null;
			}

		} finally {
			//AuditLog.log("SERVICEVENT", ":  beforeWebservice(" + webservice + ") took " + ( System.currentTimeMillis() - start ) + " millis." + ", locally = " + locally + ", processed = " + count + ", leftOvers =" + leftOvers );
		}
	}
	
	/**
	 * Add a note that webservice name need to be listened to.
	 * 
	 * @param name
	 */
	public final void addRegisteredWebservice(String name) {
		
		// Send broadcast message to other tribal managers for notification of new webservice to be listened for....
		synchronized (registeredWebservices) {
			Integer i = registeredWebservices.get(name);
			if ( i == null ) {
				i = Integer.valueOf(1);
			} else {
				i =  Integer.valueOf(i.intValue() + 1);
			}
			registeredWebservices.put(name, i);
		}
	}
	
	/**
	 * Add a note that webservice name does not need to be listened to.
	 * 
	 * @param name
	 */
	public final void removeRegisteredWebservice(String name) {
		
		// Send broadcast message to other tribal managers for notification of new webservice to be listened for....
		synchronized (registeredWebservices) {
			Integer i = (Integer) registeredWebservices.get(name);
			if ( i == null ) {
				return;
			}
			i = Integer.valueOf(i.intValue() -1 );
			if ( i.intValue() > 0 ) {
				registeredWebservices.put(name, i);
			} else {
				registeredWebservices.remove(name);
			}
		}
	}
	
	public final HashMap<String,Integer> getRegisteredWebservices() {
		return registeredWebservices;
	}
	
	/**
	 * Caching service.
	 * Check whether a webservice is registered as being listened to.
	 * 
	 * @param name
	 * @return
	 */
	public final boolean isRegisteredWebservice(String name) {
		synchronized (registeredWebservices) {
			return registeredWebservices.containsKey(name);
		}
	}
}
