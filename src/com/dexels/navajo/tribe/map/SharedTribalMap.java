package com.dexels.navajo.tribe.map;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

public class SharedTribalMap<K,V> extends HashMap {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1122073018927967102L;
	
	private static volatile HashMap<String,SharedTribalMap> registeredMaps = new HashMap<String,SharedTribalMap>();
	private String id;
	
	private static final Object semaphore = new Object();
	private static final Object semaphoreLocal = new Object();
	
	public SharedTribalMap() throws InstantiationException {
		throw new InstantiationException("Instantiate this class as SharedTribalMap(id)");
	}
	
	/**
	 * Create a SharedTribalMap, note that the map is only shared after calling the registerMap method.
	 * 
	 * @param id
	 */
	public SharedTribalMap(String id) {
		super();
		this.id = id;
	}
	
	public void finalize() {
	}
	
	public static Collection<SharedTribalMap> getAllTribalMaps() {
		return registeredMaps.values();
	}
	
	/**
	 * Registers a SharedTribalMap.
	 * 
	 * @param stm
	 * @param overwrite if set to true, an existing SharedTribalMap is NOT overwritten
	 * @return the SharedTribalMap that is registered.
	 */
	public static SharedTribalMap registerMap(SharedTribalMap stm, boolean overwrite) {
		synchronized (semaphore) {
			if ( registeredMaps.get(stm.getId()) == null || overwrite ) {
				registerMapLocal(stm);
				TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.CREATEMAP, stm.getId());
				TribeManagerFactory.getInstance().broadcast(tms);
				return stm;
			} else {
				System.err.println("SharedTribalMap " + stm.getId() + " ALREADY PRESENT, IGNORING REGISTRATION..." + registeredMaps.get(stm.getId()).size());
				return registeredMaps.get(stm.getId());
			}
		}
	}
	
	protected static void registerMapLocal(SharedTribalMap stm) {
		synchronized (semaphoreLocal) {
			if ( registeredMaps.get(stm.getId()) != null ) {
				System.err.println("Registering SharedTribalMap " + stm.getId() + " that is already present... overwriting existing one..");
				SharedTribalMap existing = registeredMaps.get(stm.getId());
				existing.clear();
				existing.putAll(stm);
			} else {
				System.err.println("Registering new SharedTribalMap " + stm.getId() + ", creating new one..." + stm.size() );
				registeredMaps.put(stm.getId(), stm);
			}
		}
	}
	
	public static void deregisterMap(String id) {
		synchronized (semaphore) {
			deregisterMapLocal(id);
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.DELETEMAP, id);
			TribeManagerFactory.getInstance().broadcast(tms);
		}
	}
	
	protected static void deregisterMapLocal(String id) {
		synchronized (semaphoreLocal) {
			if ( registeredMaps.get(id) != null ) {
				registeredMaps.get(id).clear();
				registeredMaps.remove(id);
			}
		}
	}
	
	public static void deregisterMap(SharedTribalMap stm) {
		synchronized (semaphore) {
			deregisterMapLocal(stm.getId());
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.DELETEMAP, stm.getId());
			TribeManagerFactory.getInstance().broadcast(tms);
		}
	}
	
	public static SharedTribalMap getMap(String id) {
		return registeredMaps.get(id);
	}
	
	public Object put(Object key, Object value) {
		synchronized (semaphore) {
			Object o = super.put(key, value);

			SharedTribalElement ste = new SharedTribalElement(getId(), key, value);
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.PUT, ste);
			TribeManagerFactory.getInstance().broadcast(tms);

			return o;
		}
	}
	
	protected Object putLocal(Object key, Object value) {
		synchronized (semaphoreLocal) {
			Object o = super.put(key, value);
			return o;
		}
	}
	
	public Object remove(Object key) {
		synchronized (semaphore) {
			Object o = super.remove(key);
			SharedTribalElement ste = new SharedTribalElement(getId(), key, null);
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.REMOVE, ste);
			TribeManagerFactory.getInstance().broadcast(tms);
			return o;
		}
	}
	
	protected Object removeLocal(Object key) {
		synchronized (semaphoreLocal) {
			return super.remove(key);
		}
	}
	
	public String getId() {
		return id;
	}
	
}
