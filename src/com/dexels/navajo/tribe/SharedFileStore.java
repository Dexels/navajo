package com.dexels.navajo.tribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import com.dexels.navajo.server.Dispatcher;

public class SharedFileStore implements SharedStoreInterface {

	
	class LockFiles implements FilenameFilter {

		String parent;
		String basename;
		
		public LockFiles(String parent, String name) {
			this.parent = parent.replace('/', '_');
			this.basename = name;
		}
		
		public boolean accept(File dir, String name) {
			//System.err.println("CHECK IF " + name + " ends with: " + parent + "_" + basename + ".lock");
			return name.endsWith(parent + "_" + basename + ".lock");
		}
		
	}
	
	class FileComparator implements Comparator{

		public int compare(Object o1, Object o2){
			if(o1 == o2)
				return 0;

			File f1 = (File) o1;
			File f2 = (File) o2;

			if(f1.isDirectory() && f2.isFile())
				return -1;
			if(f1.isFile() && f2.isDirectory())
				return 1;

			return (new Long(f1.lastModified())).compareTo(new Long(f2.lastModified()));
		}
	}
	
	private static String sharedStoreName = "sharedstore";
	private File sharedStore = null;
	private static Object lockSemaphore = new Object();
	
	private final String constructLockName(SharedStoreLock ssl) {
		return ssl.owner + "_" + ssl.parent.replace('/', '_') + "_" + ssl.name + ".lock";
	}
	
	private final String constructLockName(String parent, String name, String owner) {
		return owner + "_" + parent.replace('/', '_') + "_" + name + ".lock";
	}
	
	
	private final boolean lockExists(SharedStoreLock ssl) {
		//System.err.println("Check if lock exists: " + ssl);
		File [] files = sharedStore.listFiles(new LockFiles(ssl.parent, ssl.name));
		if ( files.length > 0 ) {
			// Check age of lock.
			for (int i = 0; i < files.length; i++) {
				if ( ( System.currentTimeMillis() - files[i].lastModified() ) > ssl.getLockTimeOut() ) {
					// Lock has time-out, delete it.
					files[i].delete();
					return false;
				} else {
					return true;
				}
			}
		} 
		return false;
	}
	
	private final SharedStoreLock readLock(String parent, String name, String owner) throws Exception {
		File f = new File(sharedStore, constructLockName(parent, name, owner));
		//System.err.println("CHECK IF FILE " + f.getName() + " EXISTS...");
		if (f.exists()) {
			SharedStoreLock ssl =  new SharedStoreLock(name, parent);
			ssl.owner = owner;
			return ssl;
		} else {
			throw new Exception("Lock does not exist");
		}
	}
	
	private final void writeLock(SharedStoreLock ssl) throws Exception {
		synchronized ( sharedStoreName ) {
			String name = constructLockName(ssl);
			File f = new File(sharedStore, name);
			if ( !f.exists() ) {
				if ( name.contains("/") ) {
					f.mkdirs();
				}
				f.createNewFile();
			} else {
				throw new Exception("Lock already exists");
			}
		}
	}
	
	public SharedFileStore() {
		if ( Dispatcher.getInstance() != null ) {
			sharedStore = new File(Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/" + sharedStoreName);
			if ( !sharedStore.exists() ) {
				sharedStore.mkdirs();
			}
		}
	}
	
	public boolean exists(String parent, String name) {
		File f = new File(sharedStore, parent + "/" + name);
		return f.exists();
	}

	public InputStream getStream(String parent, String name) throws SharedStoreException {
		File f = new File(sharedStore, parent + "/" + name);
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			throw new SharedStoreException(e.getMessage());
		}
	}
	
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

	public SharedStoreLock getLock(String parent, String name) {
		try {
			SharedStoreLock ssl = readLock(parent, name, Dispatcher.getInstance().getNavajoConfig().getInstanceName());
			return ssl;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}

	public String [] getObjects(String parent) {
		//System.err.println("In getObjects(" + parent + ")");
		ArrayList<String> names = new ArrayList<String>();
		File p = new File(sharedStore, parent);
		File [] fs = p.listFiles(); 
		// Sort files on last modification date
		if ( fs != null) {
			Arrays.sort(fs, new FileComparator());
			//System.err.println("fs = " + fs);
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

	public void release(SharedStoreLock lock) {

		if ( !TribeManager.getInstance().getIsChief() ) {
			TribeManager.getInstance().askChief( new RemoveLockRequest(lock.parent, lock.name) );
		} else {
			synchronized (lockSemaphore) {
				if ( lock != null ) {
					//System.err.println("RELEASING LOCK " + lock.parent + "/" + lock.name + " FOR " + lock.owner);
					File f = new File(sharedStore, constructLockName(lock));
					f.delete();
				}
			}
		}
	}

	public String store(String parent, Object o) {
		
		return null;
	}

	/**
	 * Store an object in the shared file store referenced by name.
	 * A lock is always 
	 */
	public void store(String parent, String name, Object o, boolean append, boolean requireLock) throws SharedStoreException {

		SharedStoreLock ssl = null;
		try {
			if (requireLock) {
				ssl = lock(parent, name, SharedFileStore.READ_WRITE_LOCK, true);
			}
			File p = new File(sharedStore, parent);
			if (!p.exists()) {
				p.mkdirs();
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

	public void remove(String parent, String name) {
		File f = new File(sharedStore, parent + "/" + name);
		f.delete();
	}

	public void createParent(String parent) {
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

	public long lastModified(String parent, String name) {
		File f = new File(sharedStore, ( name != null ? parent + "/" + name : parent ) );
		return f.lastModified();
	}

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
	
	public void test() {
		File f = new File("/home/arjen/Pictures");
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
		sfs.test();
	}

}
