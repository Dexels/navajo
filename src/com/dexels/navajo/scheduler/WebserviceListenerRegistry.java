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

import java.util.Collection;
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
import com.dexels.navajo.tribe.map.IntroductionRequest;
import com.dexels.navajo.tribe.map.SharedTribalMap;
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

		// Return immediately if webservice is not contained in afterWebservices set, i.e. there is
		// no listener interested in this webservice.
		if ( !ListenerStore.getInstance().isRegisteredWebservice(webservice) ) {
			return;
		} 

		Listener [] all = ListenerStore.getInstance().getListeners(AfterWebserviceTrigger.class.getName());
		
		for (int i = 0; i < all.length; i++) {
			AfterWebserviceTrigger cl = (AfterWebserviceTrigger) all[i];
			if ( cl.getWebservicePattern().equals(webservice)) {

				AfterWebserviceTrigger t2 = (AfterWebserviceTrigger) cl.clone();
				t2.setAccess(a);
				boolean initializingWorkflow = ( t2.getTask().getWorkflowDefinition() != null && t2.getTask().getWorkflowId() == null );
				boolean myWorkflow = ( t2.getTask().getWorkflowId() != null && WorkFlowManager.getInstance().hasWorkflowId(t2.getTask().getWorkflowId()));

				if ( t2.getTask().getWorkflowId() == null || initializingWorkflow || myWorkflow) {
					t2.perform();
				} else {
					if ( t2.getTask().getWebservice() != null ) { // If webservice is accompanied with the task, perform after task trigger asynchronously.
						TribeManager.getInstance().broadcast(new ServiceEventsSmokeSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.AFTERWEBSERVICE_EVENT, t2));
					} else { // Peform synchronously be sending request to each tribe member ( used in workflow transitions )
						tribalServiceRequest(t2);
					}
				}
			}
		}
	}

	private final Navajo tribalServiceRequest(WebserviceTrigger t2) {

		Iterator<TribeMember> iter = TribeManager.getInstance().getClusterState().clusterMembers.iterator();
		boolean acknowledged = false;
		Navajo result = null;
		while ( iter.hasNext() && !acknowledged ) {
			TribeMember tm = iter.next();
			if ( !tm.getMemberName().equals(Dispatcher.getInstance().getNavajoConfig().getInstanceName()) ) {
				ServiceEventRequest bser = new ServiceEventRequest(t2);
				ServiceEventAnswer bsa = (ServiceEventAnswer) TribeManager.getInstance().askSomebody(bser, tm.getAddress());
				acknowledged = bsa.acknowledged();
				if ( acknowledged ) {
					result = bsa.getProxy();
				}
			}
		}
		return result;
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

		// Return immediately if webservice is not contained in beforeWebservices set, i.e. there is
		// no listener interested in this webservice.
		if ( !ListenerStore.getInstance().isRegisteredWebservice(webservice) ) {
			return null;
		} 

		Listener [] all = ListenerStore.getInstance().getListeners(BeforeWebserviceTrigger.class.getName());
		for (int i = 0; i < all.length; i++) {
			BeforeWebserviceTrigger cl = (BeforeWebserviceTrigger) all[i];
			if ( cl.getWebservicePattern().equals(webservice)) {
				BeforeWebserviceTrigger t2 = (BeforeWebserviceTrigger) cl.clone();
				t2.setAccess(a);
				//System.err.println("Got synchronous BeforeWebserviceTrigger: " + cl.getDescription() );
				
				boolean initializingWorkflow = ( t2.getTask().getWorkflowDefinition() != null && t2.getTask().getWorkflowId() == null );
				boolean myWorkflow = ( t2.getTask().getWorkflowId() != null && WorkFlowManager.getInstance().hasWorkflowId(t2.getTask().getWorkflowId()));
				Navajo n = null;
				if ( t2.getTask().getWorkflowId() == null || initializingWorkflow || myWorkflow) {
					n = t2.perform();
				} else {
					// Peform synchronously be sending request to each tribe member.
					n = tribalServiceRequest(t2);
				}
				if ( t2.getTask().isProxy() ) {
					return n;
				}
			}
		}

		return null;
	}
}
