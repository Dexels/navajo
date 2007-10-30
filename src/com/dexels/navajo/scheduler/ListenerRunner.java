package com.dexels.navajo.scheduler;

import com.dexels.navajo.server.GenericThread;

/**
 * ListenerRunner is used to run 'activated' clock listeners. Activated clock listeners use a 2 phase method:
 * 1. Listener is activated by some the Chief.
 * 2. Activated listener task is performed by some server Y.
 * 
 * 
 * @author arjen
 *
 */
public class ListenerRunner extends GenericThread {

	private static Object semaphore = new Object();
	private static volatile ListenerRunner instance = null;
	private static String id = "Navajo ListenerRunner";
	public static String VERSION = "$Id$";
	
	private static final int CLOCK_RESOLUTION = 5000;
	
	public ListenerRunner() {
		super(id);
		setSleepTime(CLOCK_RESOLUTION);
	}
	
	public final static ListenerRunner getInstance() {
		if ( instance != null ) {
			return instance;
		}
		
		synchronized (semaphore) {
			if ( instance != null ) {
				return instance;
			}
			instance = new ListenerRunner();
			instance.startThread(instance);
		}
		
		return instance;
	}
	
	public final void worker() {
	   ListenerStore.getInstance().performActivatedListeners();
	}
	
	public void inactive() {
		try {
			synchronized ( ListenerStore.semaphore ) {
				ListenerStore.semaphore.wait();
			}
		} catch (InterruptedException e) {

		}
	}
}
