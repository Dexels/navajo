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

import java.util.HashSet;
import java.util.Iterator;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerRegistryInterface;
import com.dexels.navajo.tribe.ServiceEventAnswer;
import com.dexels.navajo.tribe.ServiceEventRequest;
import com.dexels.navajo.tribe.ServiceEventsSmokeSignal;
import com.dexels.navajo.tribe.TribeManager;
import com.dexels.navajo.tribe.TribeMember;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.workflow.WorkFlowManager;


/**
 * This class is responsible for keeping track of all existing webservice
 * triggers. It is responsible for settting the webservice trigger alarm when
 * a matching webservice is invoked.
 * 
 */
public final class WebserviceListenerRegistry implements WebserviceListenerRegistryInterface {

	static WebserviceListenerRegistry instance = null;
	
	public final static WebserviceListenerRegistry getInstance() {
		if ( instance == null ) {
			instance = new WebserviceListenerRegistry();
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

		ListenerStore.getInstance().addListener(t,BeforeWebserviceTrigger.class.getName(), true);
		// Broadcast..
		TribeManager.getInstance().broadcast(new ServiceEventsSmokeSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.ADD_WEBSERVICE, t.getWebservicePattern()));
		ListenerStore.getInstance().addRegisteredWebservice(t.getWebservicePattern());
		

	}
	
	public final void removeBeforeTrigger(BeforeWebserviceTrigger t) {

		ListenerStore.getInstance().removeListener(t,BeforeWebserviceTrigger.class.getName(), false);
		// Broadcast..
		TribeManager.getInstance().broadcast(new ServiceEventsSmokeSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.REMOVE_WEBSERVICE, t.getWebservicePattern()));
		ListenerStore.getInstance().removeRegisteredWebservice(t.getWebservicePattern());
		
	}
	
	/**
	 * Register a new trigger to the listener list.
	 * 
	 * @param t the trigger object to be registered
	 */
	public final void registerTrigger(AfterWebserviceTrigger t) {

		ListenerStore.getInstance().addListener(t,AfterWebserviceTrigger.class.getName(), true);
		// Broadcast..
		TribeManager.getInstance().broadcast(new ServiceEventsSmokeSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.ADD_WEBSERVICE, t.getWebservicePattern()));
		ListenerStore.getInstance().addRegisteredWebservice(t.getWebservicePattern());
		
	}
	
	/**
	 * Removes a webservice trigger from the listener list.
	 * 
	 * @param t the trigger object that needs to be removed.
	 */
	public final void removeTrigger(AfterWebserviceTrigger t) {

		ListenerStore.getInstance().removeListener(t,AfterWebserviceTrigger.class.getName(), false);
		ListenerStore.getInstance().removeRegisteredWebservice(t.getWebservicePattern());
		// Broadcast..
		TribeManager.getInstance().broadcast(new ServiceEventsSmokeSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.REMOVE_WEBSERVICE, t.getWebservicePattern()));

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
		if ( !ListenerStore.getInstance().isRegisteredWebservice(webservice) ) {
			return;
		} 

		long start = System.currentTimeMillis();
		long count = 0;
		
		Listener [] all = ListenerStore.getInstance().getListeners(AfterWebserviceTrigger.class.getName());
		HashSet<String> ignoreTheseTaskOnOtherMembers = new HashSet<String>();
		boolean leftOvers = false;
		
		for (int i = 0; i < all.length; i++) {
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
		}
		
		if ( !locally && leftOvers ) {
			tribalAfterWebServiceRequest(webservice, a, ignoreTheseTaskOnOtherMembers);
		}
		
		AuditLog.log("SERVICEVENT", ":  afterWebservice(" + webservice + ") took " + ( System.currentTimeMillis() - start ) + " millis." + ", locally = " + locally + ", processed = " + count + ", leftOvers =" + leftOvers);
		
	}

	private final void tribalAfterWebServiceRequest(String service, Access a, HashSet<String> ignoreTaskIds) {

		Iterator<TribeMember> iter = TribeManager.getInstance().getClusterState().clusterMembers.iterator();
		boolean acknowledged = false;
		while ( iter.hasNext() && !acknowledged ) {
			TribeMember tm = iter.next();
			if ( !tm.getMemberName().equals(Dispatcher.getInstance().getNavajoConfig().getInstanceName()) ) {
				AfterWebServiceRequest bwsr = new AfterWebServiceRequest(service, a, ignoreTaskIds);
				TribeManager.getInstance().askSomebody(bwsr, tm.getAddress());		
			}
		}
	}
	
	private final Navajo tribalBeforeWebServiceRequest(String service, Access a, HashSet<String> ignoreList) {

		Iterator<TribeMember> iter = TribeManager.getInstance().getClusterState().clusterMembers.iterator();
		boolean acknowledged = false;
		while ( iter.hasNext() && !acknowledged ) {
			TribeMember tm = iter.next();
			if ( !tm.getMemberName().equals(Dispatcher.getInstance().getNavajoConfig().getInstanceName()) ) {
				BeforeWebServiceRequest bwsr = new BeforeWebServiceRequest(service, a, ignoreList);
				BeforeWebServiceAnswer bwsa = (BeforeWebServiceAnswer) TribeManager.getInstance().askSomebody(bwsr, tm.getAddress());
				if ( bwsa.getMyNavajo() != null ) {
					return bwsa.getMyNavajo();
				}
				
			}
		}
		
		return null;
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
		if ( !ListenerStore.getInstance().isRegisteredWebservice(webservice) ) {
			return null;
		} 

		long start = System.currentTimeMillis();
		long count = 0;
		boolean leftOvers = false;
		
		try {
			Listener [] all = ListenerStore.getInstance().getListeners(BeforeWebserviceTrigger.class.getName());

			HashSet<String> ignoreTheseTaskOnOtherMembers = new HashSet<String>();
	
			for (int i = 0; i < all.length; i++) {
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
			}

			// Try other tribal members...
			if ( !locally && leftOvers ) {
				return tribalBeforeWebServiceRequest(webservice, a, ignoreTheseTaskOnOtherMembers);
			} else {
				return null;
			}

		} finally {
			AuditLog.log("SERVICEVENT", ":  beforeWebservice(" + webservice + ") took " + ( System.currentTimeMillis() - start ) + " millis." + ", locally = " + locally + ", processed = " + count + ", leftOvers =" + leftOvers );
		}
	}
}
