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
import com.dexels.navajo.tribe.BeforeServiceEventAnswer;
import com.dexels.navajo.tribe.BeforeServiceEventRequest;
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
	public final void registerTrigger(WebserviceTrigger t) {

		ListenerStore.getInstance().addListener(t,WebserviceTrigger.class.getName(), true);
		// Broadcast..
		TribeManager.getInstance().broadcast(new ServiceEventsSmokeSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.ADD_WEBSERVICE, t.getWebservicePattern()));
		ListenerStore.getInstance().addRegisteredWebservice(t.getWebservicePattern());
		
	}
	
	/**
	 * Removes a webservice trigger from the listener list.
	 * 
	 * @param t the trigger object that needs to be removed.
	 */
	public final void removeTrigger(WebserviceTrigger t) {

		ListenerStore.getInstance().removeListener(t,WebserviceTrigger.class.getName(), false);
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

		Listener [] all = ListenerStore.getInstance().getListeners(WebserviceTrigger.class.getName());
		//System.err.println(">>>>>>>>>>>>>>GOT " + all.length + " WebserviceTriggers..........................");
		for (int i = 0; i < all.length; i++) {
			WebserviceTrigger cl = (WebserviceTrigger) all[i];
			if ( cl.getWebservicePattern().equals(webservice)) {
				//System.err.println("Got WebserviceTrigger: " + cl.getDescription() );
				// Set activated status.
				// If not workflow activate task, if workflow, perform task.

				// IF WORKFLOW, WAIT FOR FINISHING OF TASK.
				// ?? HOE ERVOOR TE ZORGEN DAT DIT AFTERWEBSERVICE EVENT OOK BIJ ANDER SERVERS TERECHT KOMT DIE
				// GEINTERESSEERD ZIJN IN DIT EVENT????????????? BROADCASTEN??????????????
				//System.err.println("Got synchronous WebserviceTrigger: " + cl.getDescription() );
				
				WebserviceTrigger t2 = (WebserviceTrigger) cl.clone();
				t2.setAccess(a);
				boolean initializingWorkflow = ( t2.getTask().getWorkflowDefinition() != null && t2.getTask().getWorkflowId() == null );
				boolean myWorkflow = ( t2.getTask().getWorkflowId() != null && WorkFlowManager.getInstance().hasWorkflowId(t2.getTask().getWorkflowId()));
				
				//System.err.println("initializingWorkflow: " + initializingWorkflow + ", myWorkflow: " + myWorkflow);
				if ( initializingWorkflow || myWorkflow) {
					t2.perform();
				} else {
					TribeManager.getInstance().broadcast(new ServiceEventsSmokeSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), ServiceEventsSmokeSignal.AFTERWEBSERVICE_EVENT, t2));
				}
			}
		}
	}

	private final Navajo tribaleBeforeServiceRequest(BeforeWebserviceTrigger t2) {
		
		Iterator<TribeMember> iter = TribeManager.getInstance().getClusterState().clusterMembers.iterator();
		boolean acknowledged = false;
		Navajo result = null;
		while ( iter.hasNext() && !acknowledged ) {
			TribeMember tm = iter.next();
			BeforeServiceEventRequest bser = new BeforeServiceEventRequest(t2);
			BeforeServiceEventAnswer bsa = (BeforeServiceEventAnswer) TribeManager.getInstance().askSomebody(bser, tm.getAddress());
			acknowledged = bsa.acknowledged();
			if ( acknowledged ) {
				result = bsa.getProxy();
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
				if ( cl.getTask().isProxy() ) {
					return tribaleBeforeServiceRequest(t2);
				} else {
					//System.err.println(Dispatcher.getInstance().getNavajoConfig().getInstanceName() + "In beforeWebservice(" + webservice + ")");
					boolean initializingWorkflow = ( t2.getTask().getWorkflowDefinition() != null && t2.getTask().getWorkflowId() == null );
					boolean myWorkflow = ( t2.getTask().getWorkflowId() != null && WorkFlowManager.getInstance().hasWorkflowId(t2.getTask().getWorkflowId()));
					if ( initializingWorkflow || myWorkflow) {
						t2.perform();
					} else {
						tribaleBeforeServiceRequest(t2);
					}
				}
			}
		}

		return null;
	}
}
