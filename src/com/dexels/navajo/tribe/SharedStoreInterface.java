package com.dexels.navajo.tribe;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A shared store is used as as persistent store for a Navajo Tribe cluster.
 * 
 * @author arjen
 *
 */
public interface SharedStoreInterface {

	public static final int READ_WRITE_LOCK = 0;
	public static final int WRITE_LOCK = 1;
	
	
	/**
	 * Remove an object with the specified name of the specified parent
	 * 
	 * @param parent
	 * @param name
	 */
	public void remove(String parent, String name);
	
	/**
	 * Store an object in the shared store. If object with same name already exists, a SharedStoreException is thrown.
	 * 
	 * @param parent
	 * @param name
	 * @param value, the object to the shared store object referenced by name.
	 * @param append, if set to true the object is appended to an existing value. 
	 * @param requireLock, if requireLock is set; the call will block until a lock could be set.
	 */
	public void store(String parent, String name, Object value, boolean append, boolean requireLock) throws SharedStoreException;
	
	/**
	 * Store text in the shared store. If store object with same name already exists, a SharedStoreException is thrown.
	 * 
	 * @param parent
	 * @param name
	 * @param value, the object to the shared store object referenced by name.
	 * @param append,  if set to true the text is appended to an existing text. 
	 * @param requireLock, if requireLock is set; the call will block until a lock could be set.
	 */
	public void storeText(String parent, String name, String value, boolean append, boolean requireLock) throws SharedStoreException;
	
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
	 * Lock an object, this method takes care of distributed synchronization for obtaining lock(!)
	 * 
	 * @param parent
	 * @param name
	 * @param lockType, can be READ_WRITE_LOCK(0) or WRITE_LOCK(1)
	 * @param if block is set to true, method blocks until lock has been obtained.
	 * @return SharedStoreLock if lock was granted, else null.
	 */
	public SharedStoreLock lock(String parent, String name, int lockType, boolean block);
	
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
	 * Gets an object from the store as a stream for reading.
	 * 
	 * @param parent
	 * @param name
	 * @return
	 * @throws SharedStoreException
	 */
	public InputStream getStream(String parent, String name) throws SharedStoreException;
	
	/**
	 * Gets an object from the store as a stream for writing.
	 * 
	 * @param parent
	 * @param name
	 * @param requireLock
	 * @return
	 * @throws SharedStoreException
	 */
	public OutputStream getOutputStream(String parent, String name, boolean requireLock) throws SharedStoreException;
	
	/**
	 * Release the lock on an object.
	 * Method takes care of distributed synchronization.
	 * 
	 * @param parent
	 * @param name
	 */
	public void release(SharedStoreLock lock);
	
	/**
	 * Returns an existing(!) SharedStoreLock of a specific object. Returns null if it does not exist. 
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	public SharedStoreLock getLock(String parent, String name);
	
	/**
	 * Returns all object names from parent sub that do not have a READ_WRITE_LOCK.
	 * 
	 * @param name
	 * @return
	 */
	public String [] getObjects(String parent);
	
}
