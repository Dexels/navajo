package com.dexels.navajo.tribe.map;

import java.util.Collection;
import java.util.HashMap;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

public class SharedTribalMap extends HashMap {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1122073018927967102L;
	
	private static volatile HashMap<String,SharedTribalMap> registeredMaps = new HashMap<String,SharedTribalMap>();
	private String id;
	
	private static final Object semaphore = new Object();
	
	public SharedTribalMap() throws InstantiationException {
		throw new InstantiationException("Instantiate this class as SharedTribalMap(id)");
	}
	
	public SharedTribalMap(String id) {
		super();
		this.id = id;
		SharedTribalMap.registerMap(this);
	}
	
	public void finalize() {
	}
	
	public static Collection<SharedTribalMap> getAllTribalMaps() {
		return registeredMaps.values();
	}
	
	public static void registerMap(SharedTribalMap stm) {
		synchronized (semaphore) {
			if ( registeredMaps.containsKey(stm.getId())) {
				return;
			}
			registeredMaps.put(stm.getId(), stm);
			System.err.println("Registered SharedTribalMap " + stm.getId());
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.CREATEMAP, stm.getId());
			TribeManagerFactory.getInstance().broadcast(tms);
		}
	}
	
	protected static void registerMapLocal(SharedTribalMap stm) {
		synchronized (semaphore) {
			if ( registeredMaps.containsKey(stm.getId())) {
				return;
			} else {
				registeredMaps.put(stm.getId(), stm);
			}
		}
	}
	
	public static void deregisterMap(String id) {
		synchronized (semaphore) {
			registeredMaps.remove(id);
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.DELETEMAP, id);
			TribeManagerFactory.getInstance().broadcast(tms);
		}
	}
	
	protected static void deregisterMapLocal(String id) {
		synchronized (semaphore) {
			registeredMaps.remove(id);
		}
	}
	
	public static void deregisterMap(SharedTribalMap stm) {
		synchronized (semaphore) {
			registeredMaps.remove(stm.getId());
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.DELETEMAP, stm.getId());
			TribeManagerFactory.getInstance().broadcast(tms);
		}
	}
	
	public static SharedTribalMap getMap(String id) {
		System.err.println("registeredMaps.size(): " + registeredMaps.size());
		return registeredMaps.get(id);
	}
	
	public Object put(Object key, Object value) {
		Object o = super.put(key, value);
		SharedTribalElement ste = new SharedTribalElement(getId(), key, value);
		TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.PUT, ste);
		TribeManagerFactory.getInstance().broadcast(tms);
		return o;
	}
	
	protected Object putLocal(Object key, Object value) {
		Object o = super.put(key, value);
		return o;
	}
	
	public String getId() {
		return id;
	}
	
}
