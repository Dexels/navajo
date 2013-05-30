package com.dexels.navajo.sharedstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.junit.Test;

import com.dexels.navajo.sharedstore.SharedStoreException;
import com.dexels.navajo.sharedstore.SharedStoreInterface;
import com.dexels.navajo.sharedstore.SharedStoreLock;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store(String parent, String name, Serializable value,
			boolean append, boolean requireLock) throws SharedStoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeText(String parent, String name, String value,
			boolean append, boolean requireLock) throws SharedStoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createParent(String parent) throws SharedStoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long lastModified(String parent, String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLastModified(String parent, String name, long l)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String parent, String name) {
		return new File(storeRoot, parent + "/" + name).exists();
	}

	@Override
	public SharedStoreLock lock(String parent, String name, String owner,
			int lockType, boolean block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SharedStoreLock lock(String parent, String name, int lockType,
			boolean block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable get(String parent, String name)
			throws SharedStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getStream(String parent, String name)
			throws SharedStoreException {
		try {
			return new FileInputStream(new File(storeRoot, parent + "/" + name));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void release(SharedStoreLock lock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SharedStoreLock getLock(String parent, String name, String owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getObjects(String parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParentObjects(String parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void deletePath(File f) {
		if ( f.isFile() ) {
			f.delete();
		}
		if ( f.isDirectory() ) {
			File [] children = f.listFiles();
			for ( int i = 0; i < children.length; i++ ) {
				deletePath(children[i]);
			}
		}
	}
	public void release() {
		storeRoot.delete();
	}

	@Override
	public SharedStoreEntry[] getEntries(String parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getNextAtomicLong(String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

