package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.tipi.internal.cache.ClassLoaderStorage;

public class ClassLoaderStorageImpl implements ClassLoaderStorage {
	private String id; 
	private Set<String> negativeCache = new HashSet<>();
	
	public ClassLoaderStorageImpl(String id) {
		this.id = id;
	}
	
	@Override
	public boolean hasLocal(String location) throws IOException {
		return (!negativeCache.contains(location) && getURL(location, null) != null);
	}

	
	
	@Override
	public void invalidate(String location) {
		negativeCache.add(location);
	}

	@Override
	public InputStream getLocalData(String location) throws IOException {
		if (negativeCache.contains(location)) {
			return null;
		}
		URL u = getURL(location, null);
		if (u == null) {
			return null;
		}
		return u.openStream();
	}

	@Override
	public URL getURL(String location, InputStream is) throws IOException {
		if (negativeCache.contains(location)) {
			return null;
		}
		
		ClassLoader classLoader = getClass().getClassLoader();
		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
			
		}
		return classLoader.getResource(id + "/" + location);
	}



}
