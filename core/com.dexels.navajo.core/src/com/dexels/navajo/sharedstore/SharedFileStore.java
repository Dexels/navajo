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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import navajocore.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.NavajoObjectInputStream;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;
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
 * TODO: Create logging mechanism to log SharedFileStore errors/warnings.
 * 
 * @author arjen
 *
 */
public class SharedFileStore implements SharedStoreInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(SharedFileStore.class);
	
	/**
	 * The path name of the shared file store.
	 */
	private static String sharedStoreName = "sharedstore";
	private File sharedStore = null;

	private NavajoConfigInterface navajoConfig;
	private TribeManagerInterface tribeManagerInterface;
	private final static Object lockSemaphore = new Object();
	
	public void activate() {
		if ( navajoConfig != null ) {
			sharedStore = new File(navajoConfig.getRootPath() + "/" + sharedStoreName);
			if ( !sharedStore.exists() ) {
				if ( !sharedStore.mkdirs() ) {
					logger.error("Could not create SharedFileStore");
				}
			}
		}
		SharedStoreFactory.setInstance(this);
		
		logger.info("Started SharedFileStore");
	}
	
	public void deactivate() {
		SharedStoreFactory.setInstance(null);
		logger.info("Stopped SharedFileStore");
	}
	
	
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
		if ( files != null && files.length > 0 ) {
			// Check age of lock.
//			for (int i = 0; i < files.length; i++) {

				if ( ( System.currentTimeMillis() - files[0].lastModified() ) > ssl.getLockTimeOut() ) {
					files[0].delete();
					return false;
				} else {
					return true;
				}
//			}
		} 
		//System.err.println("\nLOCK " + ssl + " DOES NOT EXIST");
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

		String name = constructLockName(ssl);
		File f = new File(sharedStore, name);
		if ( !f.exists() ) {
			if ( name.contains("/") ) {
				f.mkdirs();
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
	
	/**
	 * Creates a new instance of a SharedFileStore.
	 * 
	 * @throws Exception when SharedFileStore could not be created.
	 */
	public SharedFileStore() throws Exception {
		if(!Version.osgiActive()) {
			navajoConfig = DispatcherFactory.getInstance().getNavajoConfig();
			tribeManagerInterface = TribeManagerFactory.getInstance();
			activate();
		} 
	}
	
	public SharedFileStore(File store, NavajoConfigInterface c) {
		sharedStore = store;
		navajoConfig = c;
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
			throw new SharedStoreException(e.getMessage(), e);
		}
	}
	
	/**
	 * Returns the Object that was serialized in the file identified by its parent (path) and its name.
	 */
	public Serializable get(String parent, String name) throws SharedStoreException {
		File f = new File(sharedStore, parent + "/" + name);
		NavajoObjectInputStream ois = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			ois = new NavajoObjectInputStream(fis, navajoConfig.getClassloader());
			Serializable o = (Serializable) ois.readObject();
			return o;
		} catch (Exception e) {
			throw new SharedStoreException(e.getMessage(), e);
		} finally {
			if ( ois != null ) {
				try {
				ois.close();
				} catch (Exception e) {}
			}
			if ( fis != null ) {
				try {
				fis.close();
				} catch (Exception e) {}
			}
		}
	}

	/**
	 * Gets a SharedStoreLock if it exists for a file identified by its parent (path) and its name.
	 * If it does not exist, null is returned.
	 */
	public SharedStoreLock getLock(String parent, String name, String owner) {
		try {
			SharedStoreLock ssl = readLock(parent, name, owner);
			return ssl;
		} catch (Exception e) {
			//e.printStackTrace(System.err);
			return null;
		}
	}

	/**
	 * Gets the names of all parent objects in a given the shared file store parent.
	 * @param parent
	 * @return
	 */
	public String [] getParentObjects(String parent) {
		
		ArrayList<String> names = new ArrayList<String>();
		File p = ( parent != null ? new File(sharedStore, parent) : sharedStore );
		File [] fs = p.listFiles(); 
		// Sort files on last modification date
		if ( fs != null) {
			Arrays.sort(fs, new FileComparator());
			for (int i = 0; i < fs.length; i++) {
				if ( fs[i].isDirectory()) {
					names.add(fs[i].getName());
				}
			}
		}
		String [] result = new String[names.size()];
		result = names.toArray(result);

		return result;
	}
	/**
	 * Gets the names of all non-parent (files) objects in the shared file store sorted by 'oldest' object first.
	 */
	public String [] getObjects(String parent) {
		ArrayList<String> names = new ArrayList<String>();
		File p = new File(sharedStore, parent);
		File [] fs = p.listFiles(); 
		// Sort files on last modification date
		if ( fs != null) {
			Arrays.sort(fs, new FileComparator());
			for (int i = 0; i < fs.length; i++) {
				if ( fs[i].isFile()) {
					names.add(fs[i].getName());
				}
			}
		}
		String [] result = new String[names.size()];
		result = names.toArray(result);

		return result;
	}

	public SharedStoreLock lock(String parent, String name, int lockType, boolean block) {
		return lock(parent, name, navajoConfig.getInstanceName(), lockType, block);
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
	public SharedStoreLock lock(String parent, String name, String owner, int lockType, boolean block) {
		
		if ( !tribeManagerInterface.getIsChief() ) {
			LockAnswer la = (LockAnswer) tribeManagerInterface.askChief(
					new GetLockRequest( parent, name, lockType, block));
			return la.mySsl;
		} else {
			SharedStoreLock ssl = new SharedStoreLock(name, parent);
			ssl.lockType = lockType;
			ssl.owner = owner;

			synchronized (lockSemaphore) {

				do {
					if ( !lockExists(ssl) ) {
						try {
							writeLock(ssl);
							//System.err.println("WROTE LOCK, RETURNING LOCK FOR " + ssl.parent + "/" + ssl.name + " TO " + ssl.owner );
							return ssl;
						} catch (Exception e) {
							logger.error("Error: ", e);
						}
					} else if ( block ){
						try {
							lockSemaphore.wait();
						} catch (InterruptedException e) {
						}
					}
				} while ( block);
				//System.err.println("LOCK COULD NOT BE OBTAINED FOR: " + parent + "/" + name + " to " + ssl.owner);
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

		if ( !tribeManagerInterface.getIsChief() ) {
			tribeManagerInterface.askChief( new RemoveLockRequest(lock.parent, lock.name) );
		} else {
			synchronized (lockSemaphore) {
				if ( lock != null ) {
					//System.err.println("RELEASING LOCK " + lock.parent + "/" + lock.name + " FOR " + lock.owner);
					File f = new File(sharedStore, constructLockName(lock));
					f.delete();
				}
				lockSemaphore.notify();
			}
		}
	}

	/**
	 * @param parent  
	 * @param o 
	 */
	public String store(String parent, Serializable o) {
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
	public void store(String parent, String name, Serializable o, boolean append, boolean requireLock) throws SharedStoreException {

		SharedStoreLock ssl = null;
		try {
			if (requireLock) {
//				System.err.println("Waiting for lock....");
				ssl = lock(parent, name, SharedFileStore.READ_WRITE_LOCK, true);
			}
			File p = new File(sharedStore, parent);
			if (!p.exists()) {
				p.mkdirs();
			}
			File f = new File(p, name);
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try {
				fos = new FileOutputStream(f, append);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(o);
				oos.reset();
			} catch (Exception e) {
				logger.error("Error: ", e);
				f.delete();
			} finally {
			if ( oos != null ) {
					try {
					oos.close();
					} catch (IOException e) {}
				}
				if ( fos != null ) {
					try {
						fos.close();
					} catch (IOException e) {}
				}
				if ( ssl != null ) {
					release(ssl);
				}
			}
			f.setLastModified(System.currentTimeMillis());
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
		f.delete();
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
				p.mkdirs();
					
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
				p.mkdirs();
			}
			File f = new File(p, name);
			FileOutputStream fos = null;
			OutputStreamWriter sw = null;
			try {
				fos = new FileOutputStream(f, append);
				sw = new OutputStreamWriter(fos);
				sw.write(str);
			} catch (Exception e) {
				logger.error("Error: ", e);
					f.delete();
			} finally {
				if ( sw != null ) {
					try {
						sw.close();
					} catch (Exception e) {}
				}
				if ( fos != null )  {
					try {
						fos.close();
					} catch (Exception e) {}
				}
			}
			f.setLastModified(System.currentTimeMillis());
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
				p.mkdirs();
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
		for (int i = 0; i < 1500*1000; i++) {
		sfs.store("aap", "noot", new Access(), false, false);
		if ( i % 10000 == 0 ) {
			System.err.println("10000 done.");
		}
		}
		String s = (String) sfs.get("aap", "noot");
		System.err.println("result = " + s);
	}

	public void removeAll(String parent) {
		AuditLog.log("SharedFileStore", "in removeAll("  + parent + ")");
		String [] s  = getObjects(parent);
		for ( int i = 0; i < s.length; i++ ) {
			remove(parent, s[i]);
		}
		// Recurse into parents.
		String [] p = getParentObjects(parent);
		for ( int i = 0; i < p.length; i++ ) {
			removeAll(parent + "/" + p[i]);
		}
	}

	public void setLastModified(String parent, String name, long l) throws IOException {
		File f = new File(sharedStore, ( name != null ? parent + "/" + name : parent ) );
		if ( !f.exists() ) {
			throw new FileNotFoundException(f.getAbsolutePath());
		}
		//System.err.println(f.getAbsolutePath() + ", lastmodified:" + l);
		f.setLastModified(l);
	}
	
	public void setNavajoConfig(NavajoConfigInterface cnf) {
		this.navajoConfig = cnf;
	}

	public void clearNavajoConfig(NavajoConfigInterface cnf) {
		this.navajoConfig = null;
	}

	public void setTribeManager(TribeManagerInterface tribeManagerInterface) {
		this.tribeManagerInterface = tribeManagerInterface;
	}

	public void clearTribeManager(TribeManagerInterface tribeManagerInterface) {
		this.tribeManagerInterface = null;
	}

	@Override
	public SharedStoreEntry[] getEntries(String parent) {
		ArrayList<SharedStoreEntry> names = new ArrayList<SharedStoreEntry>();
		File p = new File(sharedStore, parent);
		File [] fs = p.listFiles(); 
		// Sort files on last modification date
		if ( fs != null) {
			Arrays.sort(fs, new FileComparator());
			for (int i = 0; i < fs.length; i++) {
				if ( fs[i].isFile()) {
					SharedStoreEntry sse = new SharedStoreEntry(parent, fs[i].getName(), fs[i].lastModified(), "unknown", fs[i].getTotalSpace());
					names.add(sse);
				}
			}
		}
		SharedStoreEntry [] result = new SharedStoreEntry[names.size()];
		result = names.toArray(result);

		return result;
	}

	@Override
	public synchronized long getNextAtomicLong(String id) {
		
		SharedStoreLock ssl = lock("__SEQUENCES__", id, 0, true);
		try {
			if ( exists("__SEQUENCES__", id) ) {
				Long l = (Long) get("__SEQUENCES__", id);
				l = l.longValue() + 1;
				store("__SEQUENCES__", id, l, false, false);
				return l;
			} else {
				Long l = new Long(0);
				store("__SEQUENCES__", id, l, false, false);
				return l;
			}
		} catch (SharedStoreException e) {
			e.printStackTrace(System.err);
			logger.error(e.getMessage(), e);
			return -1;
		} finally {
			if ( ssl != null ) {
			release(ssl);
			}
		}
	}
}
