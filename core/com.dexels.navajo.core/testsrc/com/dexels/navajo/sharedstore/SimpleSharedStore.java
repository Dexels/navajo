package com.dexels.navajo.sharedstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class SimpleSharedStore implements SharedStoreInterface {

	File storeRoot;
	
	public SimpleSharedStore(String name) {
		storeRoot = new File(name);
		if ( !storeRoot.exists() ) {
			storeRoot.mkdirs();
		}
	}
	
	@Override
	public void remove(String parent, String name) {
		new File(storeRoot, parent + "/" + name).delete();
	}

	@Override
	public void removeAll(String parent) {
	}

	@Override
	public void store(String parent, String name, Serializable value,
			boolean append, boolean requireLock) throws SharedStoreException {
	}

	@Override
	public void storeText(String parent, String name, String value,
			boolean append, boolean requireLock) throws SharedStoreException {
	}

	@Override
	public void createParent(String parent) throws SharedStoreException {
	}

	@Override
	public long lastModified(String parent, String name) {
		return 0;
	}

	@Override
	public void setLastModified(String parent, String name, long l)
			throws IOException {
	}

	@Override
	public boolean exists(String parent, String name) {
		return new File(storeRoot, parent + "/" + name).exists();
	}

	@Override
	public SharedStoreLock lock(String parent, String name, String owner,
			int lockType, boolean block) {
		return null;
	}

	@Override
	public SharedStoreLock lock(String parent, String name, int lockType,
			boolean block) {
		return null;
	}

	@Override
	public Serializable get(String parent, String name)
			throws SharedStoreException {
		return null;
	}

	@Override
	public InputStream getStream(String parent, String name)
			throws SharedStoreException {
		try {
			return new FileInputStream(new File(storeRoot, parent + "/" + name));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public OutputStream getOutputStream(String parent, String name,
			boolean requireLock) throws SharedStoreException {
		try {
			File f = new File(storeRoot, parent);
			if ( !f.exists() ) {
				f.mkdirs();
			}
			System.err.println("In getOutputStream(), filename: " + f.getAbsolutePath());
			return new FileOutputStream(new File(f, name));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void release(SharedStoreLock lock) {
		
	}

	@Override
	public SharedStoreLock getLock(String parent, String name, String owner) {
		return null;
	}

	@Override
	public String[] getObjects(String parent) {
		return null;
	}

	@Override
	public String[] getParentObjects(String parent) {
		return null;
	}
	

	public void release() {
		storeRoot.delete();
	}

	@Override
	public SharedStoreEntry[] getEntries(String parent) {
		return null;
	}

	@Override
	public long getNextAtomicLong(String id) {
		return 0;
	}
	
}

