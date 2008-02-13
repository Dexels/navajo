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
package com.dexels.navajo.tribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.util.AuditLog;

/**
 * LockFiles is used to filter lock files.
 * 
 * @author arjen
 *
 */
class LockFiles implements FilenameFilter {

	String parent;
	String basename;

	public LockFiles(String parent, String name) {
		this.parent = parent.replace('/', '_');
		this.basename = name;
	}

	public boolean accept(File dir, String name) {
		return name.endsWith(parent + "_" + basename + ".lock");
	}

}

/**
 * FileComparator is used for sorting files based on modification date.
 * 
 * @author arjen
 *
 */
class FileComparator implements Comparator<File>{

	public int compare(File f1, File f2){
		
		if(f1 == f2) {
			return 0;
		}

		if(f1.isDirectory() && f2.isFile())
			return -1;
		if(f1.isFile() && f2.isDirectory())
			return 1;

		return (new Long(f1.lastModified())).compareTo(new Long(f2.lastModified()));
	}
}

/**
 * The SharedFile store is a File-system implementation of the SharedStoreInterface. 
 * It basically assumes some sort of OS-dependend network file system (e.g. NFS) for the "shared" part
 * of the SharedStoreInterface.
 * 
 * @author arjen
 *
 */
public class SharedFileStore implements SharedStoreInterface {

	/**
	 * The path name of the shared file store.
	 */
	private static String sharedStoreName = "sharedstore";
	private File sharedStore = null;
	private static Object lockSemaphore = new Object();
	
	/**
	 * Constructs a (lock) name for a SharedStoreLock object.
	 *  
	 * @param ssl
	 * @return
	 */
	private final String constructLockName(SharedStoreLock ssl) {
		return constructLockName(ssl.parent, ssl.name, ssl.owner);
	}
	
	/**
	 * Constructs a (lock) name based on explictly given object parent, name and owner.
	 * 
	 * @param parent
	 * @param name
	 * @param owner
	 * @return
	 */
	private final String constructLockName(String parent, String name, String owner) {
		return owner + "_" + parent.replace('/', '_') + "_" + name + ".lock";
	}
	
	/**
	 * Check whether lock exists for specified SharedStoreLock. If lock time-out is passed, delete timed-out lock and
	 * return no lock exists.
	 */
	private final boolean lockExists(SharedStoreLock ssl) {
		//System.err.println("Check if lock exists: " + ssl);
		File [] files = sharedStore.listFiles(new LockFiles(ssl.parent, ssl.name));
		if ( files.length > 0 ) {
			// Check age of lock.
			for (int i = 0; i < files.length; i++) {
				if ( ( System.currentTimeMillis() - files[i].lastModified() ) > ssl.getLockTimeOut() ) {
					// Lock has time-out, delete it.
					if (files[i].delete()) {
						return false; 
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		} 
		return false;
	}
	
	/**
	 * Reads a lock file given an object's parent, name and owner.
	 * 
	 * @param parent
	 * @param name
	 * @param owner
	 * @return
	 * @throws SharedStoreException
	 */
	private final SharedStoreLock readLock(String parent, String name, String owner) throws SharedStoreException {
		File f = new File(sharedStore, constructLockName(parent, name, owner));
		if (f.exists()) {
			SharedStoreLock ssl =  new SharedStoreLock(name, parent);
			ssl.owner = owner;
			return ssl;
		} else {
			throw new SharedStoreException("Lock does not exist");
		}
	}
	
	/**
	 * Writes a lock file for a given SharedStoreLock instance.
	 * 
	 * @param ssl
	 * @throws Exception
	 */
	private final void writeLock(SharedStoreLock ssl) throws SharedStoreException {
		synchronized ( sharedStoreName ) {
			String name = constructLockName(ssl);
			File f = new File(sharedStore, name);
			if ( !f.exists() ) {
				if ( name.contains("/") ) {
					if (!f.mkdirs()) {
						throw new SharedStoreException("Could not create parent for lock in shared store: " + ssl.toString());
					}
				}
				try {
					if (!f.createNewFile()) {
						throw new SharedStoreException("Could not write lock in shared store: " + ssl.toString());
					}
				} catch (IOException e) {
					throw new SharedStoreException("Could not write lock in shared store: " + ssl.toString() + ", message: " + e.getMessage());
				}
			} else {
				throw new SharedStoreException("Lock already exists");
			}
		}
	}
	
	/**
	 * Creates a new instance of a SharedFileStore.
	 * 
	 * @throws Exception when SharedFileStore could not be created.
	 */
	public SharedFileStore() throws Exception {
		if ( Dispatcher.getInstance() != null ) {
			sharedStore = new File(Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/" + sharedStoreName);
			if ( !sharedStore.exists() ) {
				if ( !sharedStore.mkdirs() ) {
					throw new SharedStoreException("Could not create SharedFileStore");
				}
			}
		}
	}
	
	/**
	 * Check whether file exists, given its parent (path) and its name.
	 * 
	 */
	public boolean exists(String parent, String name) {
		File f = new File(sharedStore, parent + "/" + name);
		return f.exists();
	}

	/**
	 * Return inputstream for a file,  given its parent (path) and its name.
	 */
	public InputStream getStream(String parent, String name) throws SharedStoreException {
		File f = new File(sharedStore, parent + "/" + name);
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			throw new SharedStoreException(e.getMessage());
		}
	}
	
	/**
	 * Returns the Object that was serialized in the file identified by its parent (path) and its name.
	 */
	public Object get(String parent, String name) throws SharedStoreException {
		File f = new File(sharedStore, parent + "/" + name);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(f));
			Object o = ois.readObject();
			ois.close();
			return o;
		} catch (Exception e) {
			throw new SharedStoreException(e.getMessage());
		}
	}

	/**
	 * Gets a SharedStoreLock if it exists for a file identified by its parent (path) and its name.
	 * If it does not exist, null is returned.
	 */
	public SharedStoreLock getLock(String parent, String name) {
		try {
			SharedStoreLock ssl = readLock(parent, name, Dispatcher.getInstance().getNavajoConfig().getInstanceName());
			return ssl;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}

	/**
	 * Gets the names of all objects in the shared file store sorted by 'oldest' object first.
	 */
	public String [] getObjects(String parent) {
		ArrayList<String> names = new ArrayList<String>();
		File p = new File(sharedStore, parent);
		File [] fs = p.listFiles(); 
		// Sort files on last modification date
		if ( fs != null) {
			Arrays.sort(fs, new FileComparator());
			if ( fs != null ) {
				for (int i = 0; i < fs.length; i++) {
					if ( fs[i].isFile()) {
						names.add(fs[i].getName());
					}
				}
			}
		}
		String [] result = new String[names.size()];
		result = (String []) names.toArray(result);

		return result;
	}

	/**
	 * Locks an object identified by its parent (path) and name.
	 * If block is set to true, the method blocks until the lock could be obtained.
	 * If block is set to false, null is returned if the lock could not be obtained.
	 * 
	 * @parent path of the lock object
	 * @name name of the lock object
	 * @lockType specifies the type of lock
	 * @block (see above) 
	 * 
	 */
	public SharedStoreLock lock(String parent, String name, int lockType, boolean block) {
		
		if ( !TribeManager.getInstance().getIsChief() ) {
			LockAnswer la = (LockAnswer) TribeManager.getInstance().askChief(
					new GetLockRequest( parent, name, lockType, block));
			return la.mySsl;
		} else {
			//System.err.println("ABOUT TO LOCK: (" + parent + "," + name + "," + lockType + ")");
			SharedStoreLock ssl = new SharedStoreLock(name, parent);
			ssl.lockType = lockType;
			ssl.owner = Dispatcher.getInstance().getNavajoConfig().getInstanceName();

			synchronized (lockSemaphore) {

				do {
					if ( !lockExists(ssl) ) {
						try {
							writeLock(ssl);
							//System.err.println("WROTE LOCK, RETURNING LOCK FOR " + ssl.parent + "/" + ssl.name + " TO " + ssl.owner );
							return ssl;
						} catch (Exception e) {
							e.printStackTrace(System.err);
						}
					}
				} while ( block);
			}
		}

		return null;
	}

	/**
	 * Release a lock given its SharedStoreLock instance.
	 * 
	 * @lock
	 */
	public void release(SharedStoreLock lock) {

		if ( !TribeManager.getInstance().getIsChief() ) {
			TribeManager.getInstance().askChief( new RemoveLockRequest(lock.parent, lock.name) );
		} else {
			synchronized (lockSemaphore) {
				if ( lock != null ) {
					//System.err.println("RELEASING LOCK " + lock.parent + "/" + lock.name + " FOR " + lock.owner);
					File f = new File(sharedStore, constructLockName(lock));
					if ( !f.delete() ) {
						AuditLog.log(AuditLog.AUDIT_MESSAGE_SHAREDSTORE, "Could not release lock: " + lock.toString());
					}
				}
			}
		}
	}

	public String store(String parent, Object o) {
		return null;
	}

	/**
	 * Store an object in the shared file store referenced by name.
	 * 
	 * @parent path name of the object
	 * @name name of the object
	 * @o the object itself
	 * @append if set to true, append content to already existing file identified by path and name
	 * @requireLock if set to true, the object is only stored when lock could be acquired, if set to false
	 * the object is always stored.
	 * 
	 */
	public void store(String parent, String name, Object o, boolean append, boolean requireLock) throws SharedStoreException {

		SharedStoreLock ssl = null;
		try {
			if (requireLock) {
				ssl = lock(parent, name, SharedFileStore.READ_WRITE_LOCK, true);
			}
			File p = new File(sharedStore, parent);
			if (!p.exists()) {
				if (!p.mkdirs()) {
					throw new SharedStoreException("Could not store object " + name);
				}
			}
			File f = new File(p, name);
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, append));
				oos.writeObject(o);
				oos.close();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		} finally {
			if ( ssl != null ) {
				release(ssl);
			}
		}
	}

	/**
	 * Removes an object identified by parent and name
	 * 
	 * @parent the path name of the object
	 * @name the name of the object
	 * 
	 */
	public void remove(String parent, String name) {
		File f = new File(sharedStore, parent + "/" + name);
		if (f.exists() && !f.delete()) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_SHAREDSTORE, "Could not remove object, parent = " + parent + ", name = " + name);
		}
	}

	/**
	 * Create a parent path
	 * 
	 * @parent name of the path
	 */
	public void createParent(String parent) throws SharedStoreException {
		SharedStoreLock ssl =  lock(parent, "", SharedFileStore.READ_WRITE_LOCK, true);
		try {
			File p = new File(sharedStore, parent);
			if (!p.exists()) {
				if ( !p.mkdirs() ) {
					throw new SharedStoreException("Could not create parent: " + parent);
				}
					
			}
		} finally {
			release(ssl);
		}
	}

	/**
	 * Returns last modification time (in millis) of an object identified by parent and name.
	 * 
	 * @parent name of the path
	 * @name name of the object
	 */
	public long lastModified(String parent, String name) {
		File f = new File(sharedStore, ( name != null ? parent + "/" + name : parent ) );
		return f.lastModified();
	}

	/**
	 * Stores a special 'text' object (e.g. String) 
	 * (see store())
	 */
	public void storeText(String parent, String name, String str, boolean append, boolean requireLock) throws SharedStoreException {
		SharedStoreLock ssl = null;
		try {
			if ( requireLock) {
				ssl = lock(parent, name, SharedFileStore.READ_WRITE_LOCK, true);
			}

			File p = new File(sharedStore, parent);
			if (!p.exists()) {
				if ( !p.mkdirs() ) {
					throw new SharedStoreException("Could not create object for text, parent = " + parent + ", name = " + name);
				}
			}
			File f = new File(p, name);
			try {
				OutputStreamWriter sw = new OutputStreamWriter(new FileOutputStream(f, append));
				sw.write(str);
				sw.close();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		} finally {
			if ( ssl != null ) {
				release(ssl);
			}
		}
	}

	/**
	 * Gets an outputstream for an object given parent and name. If requireLock is set to true, the outputstream is only
	 * returned if a lock could be acquired. The lock is released after returning the outputstream.
	 * 
	 */
	public OutputStream getOutputStream(String parent, String name, boolean requireLock) throws SharedStoreException {

		SharedStoreLock ssl = null;
		try {
			if ( requireLock) {	
				ssl = lock(parent, name, SharedFileStore.READ_WRITE_LOCK, true);
			}
			File p = new File(sharedStore, parent);
			if (!p.exists()) {
				if (! p.mkdirs() ) {
					throw new SharedStoreException("Could not get outputstream for object, parent = " + parent + ", name = " + name);
				}
			}
			File f = new File(p, name);
			try {
				return new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				throw new SharedStoreException(e.getMessage());
			}
		} finally {
			if ( ssl != null ) {
				release(ssl);
			}
		}
	}
	
	public void test(String name) {
		File f = new File(name);
		File [] fs = f.listFiles();
		ArrayList<String> names = new ArrayList<String>();
		Arrays.sort(fs, new FileComparator());
		for (int i = 0; i < fs.length; i++) {
			names.add(fs[i].getName());
		}
		Iterator<String> i = names.iterator();
		while ( i.hasNext() ) {
			System.err.println(i.next());
		}
	}
	
	public static void main (String [] args) throws Exception {
		SharedFileStore sfs = new SharedFileStore();
		sfs.test("/home/arjen/Pictures");
	}

	public void removeAll(String parent) {
		String [] s  = getObjects(parent);
		for ( int i = 0; i < s.length; i++ ) {
			remove(parent, s[i]);
		}
	}

}
