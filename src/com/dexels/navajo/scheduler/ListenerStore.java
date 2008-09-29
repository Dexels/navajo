package com.dexels.navajo.scheduler;

import java.util.HashSet;

import com.dexels.navajo.scheduler.tribe.ListenerRunnerActivationSmokeSignal;
import com.dexels.navajo.scheduler.triggers.TimeTrigger;
import com.dexels.navajo.scheduler.triggers.Trigger;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.sharedstore.GetLockRequest;
import com.dexels.navajo.sharedstore.LockAnswer;
import com.dexels.navajo.sharedstore.RemoveLockRequest;
import com.dexels.navajo.sharedstore.SharedStoreException;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterface;
import com.dexels.navajo.workflow.WorkFlowManager;

/**
 * The ListenerStore is used for persisting triggers.
 * The ListenerStore is currently ONLY used to store Web service triggers and Time triggers.
 * 
 * @author arjen
 *
 */
public final class ListenerStore {

	public final static Object semaphore = new Object();
	private final static Object semaphore_init = new Object();
	private final static String storeLocation = "listeners";
	public final static  String activatedListeners = "listeners/activated";
	private volatile static ListenerStore instance = null;
	
	private SharedStoreInterface ssi = null;
	
	public final static ListenerStore getInstance() {
		
		if ( instance != null ) {
			return instance;
		}
		
		synchronized ( semaphore_init ) {
			if ( instance != null ) {
				return instance;
			}
			
			instance = new ListenerStore();
			instance.ssi = SharedStoreFactory.getInstance();
			
			if ( TribeManagerFactory.getInstance().getIsChief() ) {  // Clear everything is the chief is started (complete reboot of system).
				instance.ssi.removeAll(activatedListeners);
				instance.ssi.removeAll(storeLocation);
			} 
		}
		
		return instance;
	}
		
	public void terminate() {
		instance = null;
	}
	
	/**
	 * Lock store for listeners of type.
	 * 
	 * @param type
	 * @return
	 */
	private final boolean lock(String type, boolean activatedlisteners) {
		//synchronized (semaphore_lock) {
			// Get lock on TimeTrigger listeners store.
			Answer a = TribeManagerFactory.getInstance().askChief(
					new GetLockRequest( ( activatedlisteners ? activatedListeners : storeLocation ), type, SharedStoreInterface.READ_WRITE_LOCK, false));
			if ( a != null ) {
				return ((LockAnswer) a).acknowledged();
			} else {
				System.err.println("GOT NULL LOCKANSWER");
				return false;
			}
		//}
	}
	
	/**
	 * Release lock for listeners of type.
	 * @param type
	 * @return
	 */
	private final void release(String type, boolean activatedlisteners) {
		//synchronized (semaphore_lock) {
			// Release lock.
		TribeManagerFactory.getInstance().askChief(new RemoveLockRequest(( activatedlisteners ? activatedListeners : storeLocation ), type));
		//}
	}
	
	public final void addListener(Listener l) {

			//synchronized (semaphore) {

				try {
					ssi.store(storeLocation, l.getListenerId(), l, false, false);
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}

			//}
	}
	
	public final void removeListener(Listener l) {

			//synchronized (semaphore) {
				try {
					ssi.remove(storeLocation, l.getListenerId());
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			//}

	}
	
	/**
	 * Gets all the listeners of a specific type.
	 * Notice: if trying to manipulate listeners do not forget to set proper lock.
	 * 
	 * @param type
	 * @return
	 */
	public final Listener [] getListeners(String type) {

		//System.err.println("In getListeners(" + type + ")");
		
			HashSet<Listener> set = new HashSet<Listener>();
			String [] allNames = ssi.getObjects(storeLocation);
			for (int i = 0; i < allNames.length; i++ ) {
				//System.err.println("in getListeners(" + type + ") Checking: "  + allNames[i]);
				if ( allNames[i].startsWith(type) ) {
					try {
						Listener lis = (Listener) ssi.get(storeLocation, allNames[i]);
						set.add(lis);
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
			}
			Listener [] all = new Listener[set.size()];
			all = (Listener []) set.toArray(all);
			return all;
		
	}

	/**
	 * Activate listener. ONLY USED FOR ACTIVATING TIMETRIGGER OBJECTS.
	 * Notice: do not forget to set proper lock before calling this method.
	 * 
	 * @param t
	 */
	public final void activate(Listener t) {
		
		synchronized (semaphore) {
			try {
				// Update blue-print listener.
				ssi.store(storeLocation, t.getListenerId(), t, false, false);
				// Clone listener for activation in activated listeners store.
				ssi.store(activatedListeners, t.getListenerId() + "-" + t.hashCode(), t, false, false);
			} catch (SharedStoreException e) {
				e.printStackTrace(System.err);
			}
			// NOTIFY ALL OTHER TRIBE MEMBERS.
			TribeManagerFactory.getInstance().broadcast(new ListenerRunnerActivationSmokeSignal(DispatcherFactory.getInstance().getNavajoConfig().getInstanceName(), "DOIT", "NOW"));
			semaphore.notifyAll();
		}
	}
	
	protected final String [] getActivatedListeners() {
		return ssi.getObjects(activatedListeners);
	}
	
	/**
	 * Peform listeners (ONLY TIMETRIGGERs) that are in 'activated' state.
	 * Before performing an activated listener, try to get a lock. Ignore if lock was already set..
	 * 
	 */
	public final void performActivatedListeners() {

		
		synchronized (semaphore) {
			String [] allNames = getActivatedListeners();
			for (int i = 0; i < allNames.length; i++ ) {
				//System.err.println(Dispatcher.getInstance().getNavajoConfig().getInstanceName() + ": In performActivatedListeners() Checking: "  + allNames[i]);
				Trigger lis = null;
				boolean locked = false;
				boolean performed = false;
				try {
					
					lis = (Trigger) ssi.get(activatedListeners, allNames[i]);
					
					//System.err.println("LOCK lisid = " + lis.getListenerId() + ", name =" + allNames[i]);
					/**
					 * TODO: CHECK IF LIS STLL EXISTS. MAKE SURE THAT GET RESPECTS LOCKS!!!!
					 */
					boolean isWorkflow = ( lis.getTask().getWorkflowId() != null );
					boolean myWorkflow = ( lis.getTask().getWorkflowId() != null && WorkFlowManager.getInstance().hasWorkflowId(lis.getTask().getWorkflowId()));
					
					//System.err.println(allNames[i] + ", lis = " + lis + ", isWorkflow = " + isWorkflow + ", myWorkflow = " + myWorkflow);
					if (!isWorkflow || myWorkflow) {
						if (!isWorkflow) {
							locked = lock(lis.getListenerId(), true);
							if ( locked ) {
								// Get trigger again, to make sure it is still present in activatedListeners.
								lis = (Trigger) ssi.get(activatedListeners, allNames[i]);
								performed = true;
								lis.perform();
								//System.err.println("IN !isWorkflow " + allNames[i] + " WAS PERFORMED");
							} else {
								//System.err.println(">>>>>>>>>>>>>>>>>> COULD NOT GET LOCK: " + lis.getListenerId() );
								//retryList.add(lis);
								performed = false;
							}
						} else {
							performed = true;
							lis.perform();
							//System.err.println("IN ELSE " + allNames[i] + " WAS PERFORMED");
					    }
					//} else {
						//retryList.add(lis);
					}
				} catch (SharedStoreException ste) {
					
				} catch (Throwable e) {
					e.printStackTrace();
					//System.err.println("COULD NOT PERFORM: "  + allNames[i]);
					//retryList.add(lis);
				} finally {
					if ( lis != null && ( locked || performed )) {
						if  ( performed ) {
							ssi.remove(activatedListeners, allNames[i]);
						}
						if ( locked ) {
							release(lis.getListenerId(), true);
							locked = false;
						}
					}
				}
			}
			semaphore.notifyAll();
		}
			
	}
}
