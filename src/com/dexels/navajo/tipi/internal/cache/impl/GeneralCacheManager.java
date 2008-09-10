package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.*;
import java.net.*;

import com.dexels.navajo.tipi.internal.cache.*;

public class GeneralCacheManager implements CacheManager {

	private final LocalStorage local;
	private final RemoteStorage remote;

	public GeneralCacheManager(LocalStorage l, RemoteStorage r) {
		this.local = l;
		this.remote = r;
	}

	public InputStream getContents(String location) throws IOException {
		if (isUpToDate(location)) {
			return local.getLocalData(location);
		}
		InputStream is = remote.getContents(location);
		local.storeData(location, is);
		return local.getLocalData(location);
	}

	public boolean hasLocal(String location) throws IOException {
		return local.hasLocal(location);
	}

	public boolean isUpToDate(String location) throws IOException {
		if (!hasLocal(location)) {
			System.err.println("CACHE MISSSSSS!");
			return false;
		}
		long localMod = local.getLocalModificationDate(location);
		long remoteMod = remote.getRemoteModificationDate(location);
		if (localMod >= remoteMod) {
			System.err.println("CACHE HIT!");
			return true;
		}
		System.err.println("CACHE MISSSS!");
		return false;
	}

	public URL getLocalURL(String location) throws IOException {
		if (isUpToDate(location)) {
			return local.getURL(location);
		}
		InputStream is = remote.getContents(location);
		local.storeData(location, is);
		return local.getURL(location);
	}

	public URL getRemoteURL(String location) throws IOException {
		return remote.getURL(location);
	}

}
