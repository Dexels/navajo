package com.dexels.navajo.scheduler.triggers;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.NavajoEventMap;
import com.dexels.navajo.scheduler.tribe.NavajoEventProxy;
import com.dexels.navajo.scheduler.tribe.NavajoServerEventSignal;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

/**
 * This class can be used to create objects that behave as NavajoListeners and can be used to activate Tasks.
 * 
 * @author arjen
 *
 */
public class NavajoEventTrigger extends Trigger implements NavajoListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2166990121640292643L;

	public String myDescription;

	private String associatedProxyGuid;
	
	public NavajoEventTrigger(String description) {
		myDescription = description;
	}
	
	/**
	 * Activates a NavajoEventTrigger.
	 * Makes sure that the NavajoEventTrigger is registered with the NavajoEventRegistry and that appropriate 'proxy'
	 * objects are created on the other Tribal Members.
	 * 
	 */
	@Override
	public void activateTrigger() {
		NavajoEventRegistry.getInstance().addListener(NavajoEventMap.getEventClass(myDescription), this);
		
		// Only create proxy listeners if this trigger belongs to an not-init state workflow.
		if ( getTask().getWorkflowDefinition() != null && getTask().getWorkflowId() != null ) {
			
			NavajoEventProxy nep = new NavajoEventProxy(NavajoEventMap.getEventClass(myDescription),
					TribeManagerFactory.getInstance().getMyMembership().getAddress());
			associatedProxyGuid = nep.getGuid();
			TribeManagerFactory.getInstance().broadcast(
					new NavajoServerEventSignal(DispatcherFactory.getInstance().getNavajoConfig().getInstanceName(),
							NavajoServerEventSignal.ADD_SERVER_EVENTPROXY, 
							nep));
			
		}
	}

	@Override
	public String getDescription() {
		return Trigger.SERVER_EVENT_TRIGGER + ":" + myDescription;
	}

	/**
	 * A NavajoEventTrigger is NOT single event. It could happen again.
	 */
	@Override
	public boolean isSingleEvent() {
		return false;
	}

	/** 
	 * Removes a NavajoEventTrigger.
	 * Makes sure that the NavajoEventTrigger is removed from the NavajoEventRegistry and that the associated
	 * 'proxy' objects are removed on the other tribal members.
	 * 
	 */
	@Override
	public void removeTrigger() {
		NavajoEventRegistry.getInstance().removeListener(NavajoEventMap.getEventClass(myDescription), this);

		// Only remove proxy listeners if this trigger belongs to an not-init state workflow.
		if ( getTask().getWorkflowDefinition() != null && getTask().getWorkflowId() != null ) {
			TribeManagerFactory.getInstance().broadcast(
					new NavajoServerEventSignal(DispatcherFactory.getInstance().getNavajoConfig().getInstanceName(),
							NavajoServerEventSignal.REMOVE_SERVER_EVENTPROXY, 
							associatedProxyGuid));
		}

	}

	/**
	 * Not possible to override the single event character of the NavajoEventTrigger.
	 * 
	 */
	@Override
	public void setSingleEvent(boolean b) {
	
	}

	public Navajo perform() {
		 // Spawn task asynchronously. If there is no web service to run, invoke task synchronously.
		if ( getTask().getWebservice() != null ) {
			
			GenericThread taskThread = new GenericThread("task:" + getTask().getId() ) {

				public void run() {
					try {
						worker();
					} finally {
						finishThread();
					}
				}

				public final void worker() {
					getTask().run();
				}

				@Override
				public void terminate() {
					// Nothing special.
				}
			};
			taskThread.startThread(taskThread);
		} else {
			// Invoke task synchronously to support work flow before and after task trigger synchronously.
			try {
				getTask().run();
			} catch (Throwable t2) {}
		}
		return null;
	}

	/**
	 * When NavajoEvent occurs for which this NavajoEventTrigger is registered, perform my associated task.
	 * 
	 */
	public void onNavajoEvent(NavajoEvent ne) {

		// Create Access object and Navajo Input document to store 'event' parameters.
		Access a = new Access();
		Navajo input = ne.getEventNavajo();
		a.setInDoc(input);
		setAccess(a);
		perform();		

	}

}
