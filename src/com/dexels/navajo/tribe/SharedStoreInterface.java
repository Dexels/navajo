package com.dexels.navajo.tribe;

import java.io.InputStream;

/**
 * A shared store is used as as persistent store for a Navajo Tribe cluster.
 * 
 * @author arjen
 *
 */
public interface SharedStoreInterface {

	public static final int READ_WRITE_LOCK = 0;
	public static final int WRITE_LOCK = 1;
	
	
	public void remove(String parent, String name);
	
	/**
	 * Store an object, the name of the object is returned by the SharedStore manager.
	 * 
	 * @param o
	 * @return
	 */
	//public String store(String parent, Object o);
	
	/**
	 * Store an object in the shared store. If object with same name already exists, a SharedStoreException is thrown.
	 * 
	 * @param parent
	 * @param name
	 * @param o
	 */
	public void store(String parent, String name, Object o) throws SharedStoreException;
	
	/**
	 * Create a sharedstoreinterface 'parent'. A Parent is used for logical grouping of objects.
	 * 
	 * @param parent
	 */
	public void createParent(String parent);
	
	/**
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	public long lastModified(String parent, String name);
	
	/**
	 * Check whether object exists in the shared store.
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	public boolean exists(String parent, String name);
	
	/**
	 * Lock an object.
	 * 
	 * @param parent
	 * @param name
	 * @param lockType, can be READ_WRITE_LOCK(0) or WRITE_LOCK(1)
	 * @return SharedStoreLock if lock was granted, else null.
	 */
	public SharedStoreLock lock(String parent, String name, String owner, int lockType);
	
	/**
	 * Get a specific object from the shared store. 
	 * Returns null if it does not exist or if it has a lock.
	 * 
	 * @param parent
	 * @param name
	 * @return 
	 */
	public Object get(String parent, String name) throws SharedStoreException;
	
	/**
	 * Gets an object from the store as a stream.
	 * 
	 * @param parent
	 * @param name
	 * @return
	 * @throws SharedStoreException
	 */
	public InputStream getStream(String parent, String name) throws SharedStoreException;
	
	/**
	 * Release the lock on an object.
	 * 
	 * @param parent
	 * @param name
	 */
	public void release(SharedStoreLock lock);
	
	/**
	 * Returns a SharedStoreLock of a specific object. Returns null if it does not exist.
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	public SharedStoreLock getLock(String parent, String name, String owner);
	
	/**
	 * Returns all object names from parent sub that do not have a READ_WRITE_LOCK.
	 * 
	 * @param name
	 * @return
	 */
	public String [] getObjects(String parent);
	
}
