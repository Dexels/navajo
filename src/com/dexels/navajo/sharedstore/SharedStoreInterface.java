/**
 * Title:        Navajo<p>
 * Description:  This file is part of the Navajo Service Oriented Application Framework<p>
 * Copyright:    Copyright 2002-2008 (c) Dexels BV<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package com.dexels.navajo.sharedstore;

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
	 * Remove all objects of the specified parent
	 * 
	 * @param parent
	 * @param name
	 */
	public void removeAll(String parent);
	
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
	public void createParent(String parent) throws SharedStoreException;
	
	/**
	 * Returns the last modification time (in millis) of a specific object identified by its parent and name.
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
