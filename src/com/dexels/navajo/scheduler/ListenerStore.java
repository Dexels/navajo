package com.dexels.navajo.scheduler;

import java.util.HashMap;
import java.util.HashSet;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.sharedstore.GetLockRequest;
import com.dexels.navajo.sharedstore.LockAnswer;
import com.dexels.navajo.sharedstore.RemoveLockRequest;
import com.dexels.navajo.sharedstore.SharedStoreException;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterface;
import com.dexels.navajo.workflow.WorkFlowManager;

public final class ListenerStore {

	public final static Object semaphore = new Object();
	private final static Object semaphore_init = new Object();
	private final static Object semaphore_lock = new Object();
	private final static String storeLocation = "listeners";
	public final static  String activatedListeners = "listeners/activated";
	private volatile static ListenerStore instance = null;
	
	private SharedStoreInterface ssi = null;
	private HashMap<String,Integer> registeredWebservices = null;
	
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

				instance.registeredWebservices = new HashMap<String,Integer>();
				// Register already present webservices.
				Listener [] l =  instance.getListeners(AfterWebserviceTrigger.class.getName());
				for (int i = 0; i < l.length; i++) {
					instance.addRegisteredWebservice( ((AfterWebserviceTrigger) l[i]).getWebservicePattern());
				}
				Listener [] lb = instance.getListeners(BeforeWebserviceTrigger.class.getName());
				for (int i = 0; i < lb.length; i++) {
					instance.addRegisteredWebservice( ((BeforeWebserviceTrigger) lb[i]).getWebservicePattern());
				}
			
			
		}
		
		return instance;
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
				return true;
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
	
	public final void addListener(Listener l, String type, boolean nolock) {

			//synchronized (semaphore) {

				try {
					ssi.store(storeLocation, l.getListenerId(), l, false, false);
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}

			//}
	}
	
	public final void removeListener(Listener l, String type, boolean nolock) {

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
	 * Activate trigger. 
	 * Notice: do not forget to set proper lock before calling this method.
	 * 
	 * @param t
	 */
	public final void activate(Trigger t) {
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
			TribeManagerFactory.getInstance().broadcast(new ListenerRunnerActivationSmokeSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), "DOIT", "NOW"));
			semaphore.notifyAll();
		}
	}
	
	/**
	 * Peform listeners that are in 'activated' state.
	 * Before performing an activated listener, try to get a lock. Ignore if lock was already set..
	 * 
	 */
	public final void performActivatedListeners() {

		
		synchronized (semaphore) {
			String [] allNames = ssi.getObjects(activatedListeners);
			for (int i = 0; i < allNames.length; i++ ) {
				//System.err.println(Dispatcher.getInstance().getNavajoConfig().getInstanceName() + ": In performActivatedListeners() Checking: "  + allNames[i]);
				Trigger lis = null;
				boolean locked = false;
				boolean performed = false;
				try {
					
					lis = (Trigger) ssi.get(activatedListeners, allNames[i]);
					
					boolean isWorkflow = ( lis.getTask().getWorkflowId() != null );
					boolean myWorkflow = ( lis.getTask().getWorkflowId() != null && WorkFlowManager.getInstance().hasWorkflowId(lis.getTask().getWorkflowId()));
					
					//System.err.println(allNames[i] + ", lis = " + lis + ", isWorkflow = " + isWorkflow + ", myWorkflow = " + myWorkflow);
					if (!isWorkflow || myWorkflow) {
						if (!isWorkflow) {
							locked = lock(lis.getListenerId(), true);
							if ( locked ) {
								performed = true;
								lis.perform();
								//System.err.println("IN !isWorkflow " + allNames[i] + " WAS PERFORMED");
							} else {
								//System.err.println(">>>>>>>>>>>>>>>>>> COULD NOT GET LOCK: " + lis.getListenerId() );
								//retryList.add(lis);
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
}
