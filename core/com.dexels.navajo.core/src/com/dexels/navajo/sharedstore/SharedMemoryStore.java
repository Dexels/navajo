package com.dexels.navajo.sharedstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SharedMemoryStore implements SharedStoreInterface {

	Map<String, SharedStoreEntry> store = null;
	SharedStoreEntryFactory ssf = null;
	
	private final String constructName(String parent, String name) {
		return parent + "/" + name.replace('/', '_');
	}
	
	public SharedMemoryStore(Map<String, SharedStoreEntry> storeImpl, SharedStoreEntryFactory entryFactory) {
		store = storeImpl;
		ssf = entryFactory;
	}
	
	public SharedMemoryStore() {
		store = new ConcurrentHashMap<String, SharedStoreEntry>();
	}
	
	public void setSharedStoreEntryFactory(SharedStoreEntryFactory entryFactory) {
		ssf = entryFactory;
	}
	
	public void setStoreImplementation(Map<String,SharedStoreEntry> storeImpl) {
		this.store = storeImpl;
	}
	
	@Override
	public void remove(String parent, String name) {
		removeEntry(parent, name);
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

	private void removeEntry(String parent, String name) {
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
		return getEntry(parent, name) != null;
	}

	@Override
	public SharedStoreLock lock(String parent, String name, String owner, int lockType, boolean block) {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public SharedStoreLock lock(String parent, String name, int lockType,
			boolean block) {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public Serializable get(String parent, String name) throws SharedStoreException {
		SharedStoreEntry e = getEntry(parent, name);
		if ( e != null ) {
			return e.getValue();
		} else {
			throw new SharedStoreException("No such object: " + parent + "/" + name);
		}
	}

	@Override
	public InputStream getStream(String parent, String name) throws SharedStoreException {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public OutputStream getOutputStream(String parent, String name, boolean requireLock) throws SharedStoreException {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public void release(SharedStoreLock lock) {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public SharedStoreLock getLock(String parent, String name, String owner) {
		throw new RuntimeException("Not Implemented");
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

}
