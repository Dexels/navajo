package com.dexels.navajo.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class NavajoEventRegistry {

	private volatile static NavajoEventRegistry instance = null;
	
	private HashMap<Class<? extends NavajoEvent>, HashSet<NavajoListener>> registry = 
															new HashMap<Class<? extends NavajoEvent>, HashSet<NavajoListener>>();
	
	private static Object sempahore = new Object();
	
	public static NavajoEventRegistry getInstance() {
		if ( instance != null ) {
			return instance;
		} else {
			instance = new NavajoEventRegistry();
			return instance;
		}
	}
	
	public void addListener(Class<? extends NavajoEvent> type, NavajoListener l) {
		
		System.err.println("Event Listener Added for: " + type + ", l = " + l.getClass());
		synchronized (sempahore) {
			
			HashSet<NavajoListener> registered = registry.get(type);
			if ( registered == null ) {
				registered = new HashSet<NavajoListener>();
				registry.put(type, registered);
			}
			registered.add(l);
		}
	}
	
	public void removeListener(Class<? extends NavajoEvent> type, NavajoListener l) {
		synchronized (sempahore) {
			HashSet<NavajoListener> registered = registry.get(type);
			registered.remove(l);
		}
	}
	
	public void triggerEvent(NavajoEvent ne) {
	
		System.err.println("Event Triggered: " + ne.getClass());
		HashSet<NavajoListener> copy = null; 
		synchronized (sempahore) {
			HashSet<NavajoListener> registered = registry.get(ne.getClass());
			copy = new HashSet<NavajoListener>();
			copy.addAll(registered);
		}
		Iterator<NavajoListener> i = copy.iterator();
		while ( i.hasNext() ) {
			i.next().invoke(ne);
		}
		
	}
}
