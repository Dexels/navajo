package com.dexels.navajo.sharedstore.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterface;
import com.dexels.navajo.sharedstore.SharedStoreLock;

public class SharedTribalMap<K,V> extends HashMap {

	public int size;
	public String id;
	
	/**
	 * Statistics.
	 */
	protected long insertCount;
	protected long deleteCount;
	protected long getCount;
	
	private static final long serialVersionUID = -1122073018927967102L;
	
	private static volatile HashMap<String,SharedTribalMap> registeredMaps = new HashMap<String,SharedTribalMap>();
	private boolean tribalSafe = false;
	
	private static volatile Object semaphore = new String();
	public  volatile Object semaphoreLocal = new String();
	
	protected SharedTribalMap() throws InstantiationException {
		//throw new InstantiationException("Instantiate this class as SharedTribalMap(id)");
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
				return registeredMaps.get(stm.getId());
			}
		}
	}
	
	protected static void registerMapLocal(SharedTribalMap stm) {
		synchronized (semaphore) {
			if ( registeredMaps.get(stm.getId()) != null ) {
				SharedTribalMap existing = registeredMaps.get(stm.getId());
				existing.clearLocal();
				existing.putAll(stm);
			} else {
				registeredMaps.put(stm.getId(), stm);
			}
		}
	}
	
	/**
	 * Deregisters a shared tribal map.
	 * 
	 * @param id
	 */
	public static void deregisterMap(String id) {
		synchronized (semaphore) {
			deregisterMapLocal(id);
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.DELETEMAP, id);
			TribeManagerFactory.getInstance().broadcast(tms);
		}
	}
	
	protected static void deregisterMapLocal(String id) {
		synchronized (semaphore) {
			if ( registeredMaps.get(id) != null ) {
				registeredMaps.get(id).clearLocal();
				registeredMaps.remove(id);
			}
		}
	}
	/**
	 * Deregisters a shared tribal map.
	 * 
	 * @param stm
	 */
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
	
	private final String getLockName(Object key) {
		if ( key != null ) {
			return id + "-" + key.hashCode();
		} else {
			return id;
		}
	}
	
	public Object put(Object key, Object value) {
		
		SharedStoreLock ssl = null;
		
		if ( tribalSafe ) {
			ssl = SharedStoreFactory.getInstance().lock("", getLockName(key) , SharedStoreInterface.READ_WRITE_LOCK, true);
		}
		
		try {
			//System.err.println(Dispatcher.getInstance().getApplicationId() + ": before putlocal(" + key + ")");
			Object o = putLocal(key, value);

			SharedTribalElement ste = new SharedTribalElement(getId(), key, value);
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.PUT, ste);
			TribeManagerFactory.getInstance().broadcast(tms);

			return o;
			
		} finally {
			if ( tribalSafe && ssl != null ) {
				SharedStoreFactory.getInstance().release(ssl);
			}
		}
	}
	

	public boolean containsKey(Object key) {
		SharedStoreLock ssl = null;
		if ( tribalSafe ) {
			ssl = SharedStoreFactory.getInstance().lock("", getLockName(key) , SharedStoreInterface.READ_WRITE_LOCK, true);
		}
		try {
			//System.err.println(Dispatcher.getInstance().getApplicationId() + ": id: " + ", containsKey(" + key + ") = " + super.containsKey(key) + ", hash = " + this.hashCode());
			return super.containsKey(key);
		} finally {
			if ( tribalSafe && ssl != null ) {
				SharedStoreFactory.getInstance().release(ssl);
			}
		}
	}
	
	public Object get(Object key) {

		SharedStoreLock ssl = null;
		if ( tribalSafe ) {
			ssl = SharedStoreFactory.getInstance().lock("", getLockName(key) , SharedStoreInterface.READ_WRITE_LOCK, true);
		}
		try {
			getCount++;
			return super.get(key);
		} finally {
			if ( tribalSafe && ssl != null ) {
				SharedStoreFactory.getInstance().release(ssl);
			}
		}
	}
	
	public void clear() {
		clearLocal();
		SharedTribalElement ste = new SharedTribalElement(getId(), null, null);
		TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.CLEAR, ste);
		TribeManagerFactory.getInstance().broadcast(tms);
	}
	
	protected void clearLocal() {
		synchronized (semaphoreLocal) {
			super.clear();
		}
	}
	
	protected Object putLocal(Object key, Object value) {
		synchronized (semaphoreLocal) {
			Object o = super.put(key, value);
			//System.err.println(Dispatcher.getInstance().getApplicationId() + ": " + id + ": in PutLocal(" + key + ", " + value + "), containsKey = " + containsKey(key) + ", hash = " + this.hashCode());
			insertCount++;
			return o;
		}
	}
	
	public Object remove(Object key) {

		SharedStoreLock ssl = null;
		if ( tribalSafe ) {
			ssl = SharedStoreFactory.getInstance().lock("", getLockName(key) , SharedStoreInterface.READ_WRITE_LOCK, true);
		}

		try {
			Object o = removeLocal(key);
			SharedTribalElement ste = new SharedTribalElement(getId(), key, null);
			TribalMapSignal tms = new TribalMapSignal(Dispatcher.getInstance().getNavajoConfig().getInstanceName(), TribalMapSignal.REMOVE, ste);
			TribeManagerFactory.getInstance().broadcast(tms);
			
			return o;
		} finally {
			if ( tribalSafe && ssl != null ) {
				SharedStoreFactory.getInstance().release(ssl);
			}
		}
	}
	
	protected Object removeLocal(Object key) {
		synchronized (semaphoreLocal) {
			//System.err.println(Dispatcher.getInstance().getApplicationId() + ": " + id + ": in removeLocal(" + key + ")");
			Object o = super.remove(key);
			semaphoreLocal.notifyAll();
			if ( o != null ) {
				deleteCount++;
			}
			return o;
		}
	}
	
	public int getSize() {
		return this.size();
	}
	
	public String getId() {
		return id;
	}

	public boolean isTribalSafe() {
		return tribalSafe;
	}

	public void setTribalSafe(boolean tribalSafe) {
		this.tribalSafe = tribalSafe;
	}
	
}
