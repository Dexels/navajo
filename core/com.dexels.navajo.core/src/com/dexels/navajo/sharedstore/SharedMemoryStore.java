package com.dexels.navajo.sharedstore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoObjectInputStream;

public class SharedMemoryStore implements SharedStoreInterface {

	Map<String, SharedStoreEntry> store = null;
	SharedStoreEntryFactory ssf = null;
	private Map<String,SharedStoreLock> mLocks = new ConcurrentHashMap<String,SharedStoreLock>();
	
	private final static Logger logger = LoggerFactory.getLogger(SharedMemoryStore.class);
	
	private final String constructName(String parent, String name) {
		return parent + "/" + name.replace('/', '_');
	}
	
	private SharedStoreLock tryLock(SharedMemoryStoreLock hcl, boolean block) {
		if ( block ) {
			hcl.lock();
			mLocks.put(constructName(hcl.parent, hcl.name), hcl);
			return hcl;
		} else {
			if ( hcl.tryLock() ) {
				mLocks.put(constructName(hcl.parent, hcl.name), hcl);
				return hcl;
			} else {
				return null;
			}
		}
	}
	
	public SharedMemoryStore(Map<String, SharedStoreEntry> storeImpl, SharedStoreEntryFactory entryFactory) {
		store = storeImpl;
		ssf = entryFactory;
	}
	
	public SharedMemoryStore() {
		store = new ConcurrentHashMap<String, SharedStoreEntry>();
		ssf = new DefaultSharedStoreEntryFactoryImplementation();
	}
	
	public void setSharedStoreEntryFactory(SharedStoreEntryFactory entryFactory) {
		ssf = entryFactory;
	}
	
	public void setStoreImplementation(Map<String,SharedStoreEntry> storeImpl) {
		this.store = storeImpl;
	}
	
	public Map<String, SharedStoreEntry> getStoreImplementation() {
		return this.store;
	}
	
	@Override
	public void removeAll(String parent) {
		Iterator<String> entries = new HashSet<String>(store.keySet()).iterator();
		while ( entries.hasNext() ) {
			String key = entries.next();
			if ( key.startsWith(parent + "/") ) {
				store.remove(key);
			}
		}
	}

	@Override
	public void store(String parent, String name, Serializable value, boolean append, boolean requireLock) throws SharedStoreException {
		if ( append || requireLock ) {
			throw new SharedStoreException("Append / requireLock semantics not supported for this store type");
		}
		SharedStoreEntry e = ssf.constructSharedStoreEntry(constructName(parent, name), value);
		store.put(e.getName(), e);
	}

	@Override
	public void storeText(String parent, String name, String value, boolean append, boolean requireLock) throws SharedStoreException {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public void  createParent(String parent) throws SharedStoreException {
		// Do nothing.
	}

	public void remove(String parent, String name) {
		removeEntry(parent, name);
	}
	
	protected void removeEntry(String parent, String name) {
		SharedStoreEntry e = store.get(constructName(parent, name));
		if ( e != null ) {
			store.remove(constructName(parent, name));
		}
	}
	
	private SharedStoreEntry getEntry(String parent, String name) {
		SharedStoreEntry e = store.get(constructName(parent, name));
		if ( e != null ) {
			return e;
		}
		return null;
	}
	
	@Override
	public long lastModified(String parent, String name) {
		SharedStoreEntry e = getEntry(parent, name);
		if ( e != null ) {
			return e.getLastModified();
		} else {
			return -1;
		}
	}

	@Override
	public void setLastModified(String parent, String name, long l)
			throws IOException {
		SharedStoreEntry e = getEntry(parent, name);
		if ( e != null ) {
			e.setLastModified(l);
		}
	}

	@Override
	public boolean exists(String parent, String name) {
		return store.containsKey(constructName(parent, name));
	}

	@Override
	public SharedStoreLock lock(String parent, String name, String owner, int lockType, boolean block) {
		SharedMemoryStoreLock ssl = new SharedMemoryStoreLock(name, parent);
		ssl.setOwner(owner);
		return tryLock(ssl, block);
	}

	@Override
	public SharedStoreLock lock(String parent, String name, int lockType, boolean block) {
		return tryLock(new SharedMemoryStoreLock(name, parent), block);
	}

	@Override
	public Serializable get(String parent, String name) throws SharedStoreException {
		SharedStoreEntry e = getEntry(parent, name);
		if ( e != null ) {
			return (Serializable) e.getValue();
		} else {
			throw new SharedStoreException("No such object: " + parent + "/" + name);
		}
	}

	@Override
	public InputStream getStream(String parent, String name) throws SharedStoreException {
		SharedStoreEntry sse = getEntry(parent, name);
		if ( sse == null ) {
			return null;
		}
		byte [] value = (byte []) sse.getValue();
		return new ByteArrayInputStream(value);
		
	}

	@Override
	public OutputStream getOutputStream(final String parent, final String name, final boolean requireLock) throws SharedStoreException {	
		ByteArrayOutputStream baos = new ByteArrayOutputStream() {
			@Override 
			public void close() throws IOException {
				super.close();
				SharedStoreLock ssl = null;
				if ( requireLock ) {
					ssl = lock(parent, name, 1, true);
				}
				try {
				SharedStoreEntry e = new SharedStoreEntry(constructName(parent, name), toByteArray());
				store.put(e.getName(), e);
				} finally {
					if ( ssl != null ) {
						release(ssl);
					}
				}
			}
		};
		return baos;
	}

	@Override
	public void release(SharedStoreLock lock) {
		if ( lock == null ) {
			logger.error("SharedStoreLock CAN NOT be null in release()");
			return;
		}
		((SharedMemoryStoreLock) lock).unlock();
		mLocks.remove(constructName(lock.parent, lock.name));
	}

	@Override
	public SharedStoreLock getLock(String parent, String name, String owner) {
		SharedStoreLock ssl = mLocks.get(constructName(parent, name));
		if ( ssl != null && ( owner == null || owner.equals(ssl.getOwner()) ) ) {
			return ssl;
		}
		return null;
	}

	@Override
	public String[] getObjects(String parent) {
		
		Set<String> entries = new HashSet<String>();
		Iterator<String> allObjects = store.keySet().iterator();
		while ( allObjects.hasNext() ) {
			String key = allObjects.next();
			// Fetch name part.
			String name = key.substring(key.lastIndexOf("/") + 1);
			if ( (parent+"/"+name).equals(key) ) {
				entries.add(name);
			}
		}
		
		if ( entries.size() > 0) {
			String [] keys = new String[entries.size()];
			keys = entries.toArray(keys);
			return keys;
		} else {
			return new String[]{};
		}
	}

	public int getSize() {
		return store.size();
		
	}
	@Override
	public String[] getParentObjects(String parent) {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public SharedStoreEntry[] getEntries(String parent) {
		Set<SharedStoreEntry> entries = new HashSet<SharedStoreEntry>();
		Iterator<String> allObjects = store.keySet().iterator();
		while ( allObjects.hasNext() ) {
			String key = allObjects.next();
			// Fetch name part.
			SharedStoreEntry sse = store.get(key);
			String name = key.substring(key.lastIndexOf("/") + 1);
			if ( (parent+"/"+name).equals(key) ) {
				entries.add(sse);
			}
		}
		
		if ( entries.size() > 0) {
			SharedStoreEntry [] keys = new SharedStoreEntry[entries.size()];
			keys = entries.toArray(keys);
			return keys;
		} else {
			return new SharedStoreEntry[]{};
		}
	}

	@Override
	public long getNextAtomicLong(String id) {
		return 0;
	}

}
