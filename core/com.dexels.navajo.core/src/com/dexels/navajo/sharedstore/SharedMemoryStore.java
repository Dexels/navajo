package com.dexels.navajo.sharedstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Entry {

	long lastModified;
	Object value;
	
	public Entry(Object v) {
		this.value = v;
		lastModified = System.currentTimeMillis();
	}
	
	public long getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(long l) {
		this.lastModified = l;
	}
	
	public void update(Object v) {
		this.value = v;
		lastModified = System.currentTimeMillis();
	}
}

public class SharedMemoryStore implements SharedStoreInterface {

	ConcurrentHashMap<String, Map<String, Entry>> store = new ConcurrentHashMap<String, Map<String,Entry>>();
	
	@Override
	public void remove(String parent, String name) {
		Map<String,Entry> pMap = store.get(parent);
		if ( pMap != null ) {
			pMap.remove(name);
			System.err.println("Removed " + parent + "/" + name + ", size: " + pMap.size());
		}
		
	}
	
	@Override
	public void removeAll(String parent) {
		Iterator<String> parents = new HashSet<String>(store.keySet()).iterator();
		while ( parents.hasNext() ) {
			String key = parents.next();
			// Remove parents that either start with the given parent + "/" name or are equal to the given parent name.
			if ( key.startsWith(parent + "/") || key.equals(parent)) {
				store.remove(key);
			}
		}
	}

	@Override
	public void store(String parent, String name, Object value, boolean append, boolean requireLock) throws SharedStoreException {
		if ( append || requireLock ) {
			throw new SharedStoreException("Append / requireLock semantics not supported for this store type");
		}
		Entry e = new Entry(value);
		createParent(parent);
		Map<String, Entry> pMap = store.get(parent);
		pMap.put(name, e);
	}

	@Override
	public void storeText(String parent, String name, String value, boolean append, boolean requireLock) throws SharedStoreException {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public void  createParent(String parent) throws SharedStoreException {
		ConcurrentHashMap<String, Entry> pMap = new ConcurrentHashMap<String, Entry>();
		store.putIfAbsent(parent, pMap);
	}

	private Entry getEntry(String parent, String name) {
		Map<String,Entry> pMap = store.get(parent);
		if ( pMap != null ) {
			Entry e = pMap.get(name);
			if ( e != null ) {
				return e;
			}
		}
		return null;
	}
	
	@Override
	public long lastModified(String parent, String name) {
		Entry e = getEntry(parent, name);
		if ( e != null ) {
			return e.getLastModified();
		} else {
			return -1;
		}
	}

	@Override
	public void setLastModified(String parent, String name, long l)
			throws IOException {
		Entry e = getEntry(parent, name);
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
	public Object get(String parent, String name) throws SharedStoreException {
		System.err.println("In SharedMemoryStore. Getting " + parent + "/" + name);
		Entry e = getEntry(parent, name);
		if ( e != null ) {
			return e.value;
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
		Map<String,Entry> objects = store.get(parent);
		if ( objects != null ) {
			String [] keys = new String[objects.size()];
			keys = objects.keySet().toArray(keys);
			return keys;
		} else {
			return new String[]{};
		}
	}

	@Override
	public String[] getParentObjects(String parent) {
		throw new RuntimeException("Not Implemented");
	}

}
