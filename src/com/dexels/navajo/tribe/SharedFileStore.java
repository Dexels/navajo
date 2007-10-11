package com.dexels.navajo.tribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
	
	private static String sharedStoreName = "sharedstore";
	private File sharedStore = null;
	
	private final String constructLockName(SharedStoreLock ssl) {
		return ssl.owner + "_" + ssl.parent.replace('/', '_') + "_" + ssl.name + ".lock";
	}
	
	private final String constructLockName(String parent, String name, String owner) {
		return owner + "_" + parent.replace('/', '_') + "_" + name + ".lock";
	}
	
	private final boolean lockExists(SharedStoreLock ssl) {
		System.err.println("Check if lock exists: " + ssl);
		
			File [] files = sharedStore.listFiles(new LockFiles(ssl.parent, ssl.name));
			return files.length > 0;
		
	}
	
	private final void removeLock(SharedStoreLock ssl) {
		synchronized ( sharedStoreName ) {
			File f = new File(sharedStore, constructLockName(ssl));
			f.delete();
		}
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
		sharedStore = new File(Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/" + sharedStoreName);
		if ( !sharedStore.exists() ) {
			sharedStore.mkdirs();
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

	public SharedStoreLock getLock(String parent, String name, String owner) {
		try {
			SharedStoreLock ssl = readLock(parent, name, owner);
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
		//System.err.println("fs = " + fs);
		if ( fs != null ) {
			for (int i = 0; i < fs.length; i++) {
				if ( fs[i].isFile()) {
					names.add(fs[i].getName());
				}
			}
		}
		String [] result = new String[names.size()];
		result = (String []) names.toArray(result);

		return result;
	}

	public SharedStoreLock lock(String parent, String name, String owner, int lockType) {
		System.err.println("ABOUT TO LOCK: (" + parent + "," + name + "," + lockType + ")");
		SharedStoreLock ssl = new SharedStoreLock(name, parent);
		ssl.lockType = lockType;
		ssl.owner = owner;
		if ( !lockExists(ssl) ) {
			try {
				writeLock(ssl);
				return ssl;
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return null;
	}

	public void release(SharedStoreLock lock) {
		//System.err.println("ABOUT TO RELEASE: " + lock);
		if ( lock != null ) {
			removeLock(lock);
		}
	}

	public String store(String parent, Object o) {
		
		return null;
	}

	public void store(String parent, String name, Object o) throws SharedStoreException {
		File p = new File(sharedStore, parent);
		if (!p.exists()) {
			p.mkdirs();
		}
		File f = new File(p, name);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(o);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	public void remove(String parent, String name) {
		File f = new File(sharedStore, parent + "/" + name);
		f.delete();
	}

	public void createParent(String parent) {
		SharedStoreLock ssl =  lock(parent, "", Dispatcher.getInstance().getNavajoConfig().getInstanceName(), SharedFileStore.READ_WRITE_LOCK);
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

}
