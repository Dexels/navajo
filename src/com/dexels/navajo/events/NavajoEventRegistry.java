package com.dexels.navajo.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A very simple event registry class.
 * 
 * @author arjen
 *
 */
public class NavajoEventRegistry {

	private volatile static NavajoEventRegistry instance = null;
	
	private HashMap<Class<? extends NavajoEvent>, HashSet<NavajoListener>> registry = 
															new HashMap<Class<? extends NavajoEvent>, HashSet<NavajoListener>>();
	
	private static Object semaphore = new Object();
	
	/**
	 * Return a (singleton) instance of the NavajoEventRegistry.
	 * 
	 * @return
	 */
	public static NavajoEventRegistry getInstance() {
		if ( instance != null ) {
			return instance;
		} else {
			System.err.println(">>>>>>>>>>>>> waiting for semaphore ");
			synchronized (semaphore ) {
				
				if ( instance != null ) {
					return instance;
				}
				
				System.err.println("Creating NavajoEventRegistry()....");
				instance = new NavajoEventRegistry();
				
				System.err.println("Returning instance = " + instance);
			}
			return instance;
		}
	}
	
	/**
	 * Express an interest in a certain type of event. The interested party should implement the NavajoListener interface
	 * 
	 * @param type
	 * @param l
	 */
	public void addListener(Class<? extends NavajoEvent> type, NavajoListener l) {
		
		System.err.println("Event Listener Added for: " + type + ", l = " + l.getClass());
		synchronized (semaphore) {
			
			HashSet<NavajoListener> registered = registry.get(type);
			if ( registered == null ) {
				registered = new HashSet<NavajoListener>();
				registry.put(type, registered);
			}
			registered.add(l);
		}
	}
	
	/**
	 * Deregister a previously registered interest in an event.
	 * 
	 * @param type
	 * @param l
	 */
	public void removeListener(Class<? extends NavajoEvent> type, NavajoListener l) {
		synchronized (semaphore) {
			HashSet<NavajoListener> registered = registry.get(type);
			registered.remove(l);
		}
	}
	
	/**
	 * Publish an event and handle the listeners asynchronously
	 * 
	 * @param ne
	 */
	public void publishAsynchronousEvent(final NavajoEvent ne) {

		System.err.println("Asynchronous Event Triggered: " + ne.getClass());
		Set<NavajoListener> copy = getInterestedParties(ne);
		if ( copy != null ) {
			Iterator<NavajoListener> i = copy.iterator();
			while ( i.hasNext() ) {
				final NavajoListener nl = i.next();
				try {
					new Thread() {
						public void run() {
							nl.onNavajoEvent(ne);
						}
					}.start();
				} catch (Throwable t) {
					t.printStackTrace(System.err);
				}
			}
		}
	}
	
	/**
	 * Publish an event and handle the listeners synchronously
	 * 
	 * @param ne
	 */
	public void publishEvent(NavajoEvent ne) {

		System.err.println("Synchronous Event Triggered: " + ne.getClass());
		Set<NavajoListener> copy = getInterestedParties(ne);
		if ( copy != null )  {
			Iterator<NavajoListener> i = copy.iterator();
			while ( i.hasNext() ) {
				try {
					i.next().onNavajoEvent(ne);
				} catch (Throwable t) {
					t.printStackTrace(System.err);
				}
			}
		}
	}
	
	private final Set<NavajoListener> getInterestedParties(NavajoEvent ne) {
		synchronized (semaphore) {
			HashSet<NavajoListener> copy = null; 
			HashSet<NavajoListener> registered = registry.get(ne.getClass());
			if ( registered != null ) {
				copy = new HashSet<NavajoListener>();
				copy.addAll(registered);
				return copy;
			} else {
				return null;
			}
		}
	}
	
}
