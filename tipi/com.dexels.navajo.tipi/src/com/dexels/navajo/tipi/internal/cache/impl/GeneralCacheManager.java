package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.CacheManager;
import com.dexels.navajo.tipi.internal.cache.LocalStorage;
import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class GeneralCacheManager implements CacheManager {

	private final LocalStorage local;
	private final RemoteStorage remote;
	
	private final static Logger logger = LoggerFactory
			.getLogger(GeneralCacheManager.class);
	public GeneralCacheManager(LocalStorage l, RemoteStorage r) {
		this.local = l;
		this.remote = r;
	}

	@Override
	public InputStream getContents(String location) throws IOException {
		if (isUpToDate(location)) {
			return local.getLocalData(location);
		}
		Map<String, Object> metadata = new HashMap<String, Object>();
		InputStream is = remote.getContents(location, metadata);
		if (is == null) {
			return null;
		}
		local.storeData(location, is, metadata);
		return local.getLocalData(location);
	}

	@Override
	public boolean hasLocal(String location) throws IOException {
		return local.hasLocal(location);
	}

	@Override
	public boolean isUpToDate(String location) throws IOException {
		if (!hasLocal(location)) {
			return false;
		}
		long localMod = local.getLocalModificationDate(location);
		long remoteMod = remote.getRemoteModificationDate(location);
		if (localMod >= remoteMod) {
			return true;
		}
		return false;
	}

	@Override
	public URL getLocalURL(String location) throws IOException {
		if (isUpToDate(location)) {
			return local.getURL(location);
		}
		Map<String, Object> metadata = new HashMap<String, Object>();
		InputStream is = remote.getContents(location, metadata);
		local.storeData(location, is, metadata);
		return local.getURL(location);
	}

	@Override
	public URL getRemoteURL(String location) throws IOException {
		return remote.getURL(location);
	}

	@Override
	public void flushCache() {
		try {
			local.flushAll();
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
	}

}
