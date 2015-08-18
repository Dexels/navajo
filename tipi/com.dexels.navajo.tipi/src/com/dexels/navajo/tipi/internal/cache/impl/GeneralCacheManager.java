package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.CacheManager;
import com.dexels.navajo.tipi.internal.cache.CacheValidator;
import com.dexels.navajo.tipi.internal.cache.LocalStorage;
import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class GeneralCacheManager implements CacheManager {

	private final LocalStorage local;
	private final RemoteStorage remote;
	private final CacheValidator cacheValidator;
	
	private final static Logger logger = LoggerFactory
			.getLogger(GeneralCacheManager.class);
	public GeneralCacheManager(LocalStorage l, RemoteStorage r, CacheValidator vc) {
		this.local = l;
		this.remote = r;
		this.cacheValidator = vc;
		
	}

	@Override
	public InputStream getContents(String location) throws IOException {
		final boolean isUpToDate = isUpToDate(location);
		logger.info("Is up to date: {} == {}",location,isUpToDate);
		if (isUpToDate) {
			return local.getLocalData(location);
		}
		Map<String, Object> metadata = new HashMap<String, Object>();
		InputStream is = remote.getContents(location, metadata);
		if (is == null) {
			return null;
		}
		local.storeData(location, is, metadata);
		cacheValidator.update(location);
		return local.getLocalData(location);
	}

	@Override
	public boolean hasLocal(String location) throws IOException {
		
		boolean hasLocal = local.hasLocal(location);
		logger.info("Local present for location: {} == {} ",location,hasLocal);
		return hasLocal;
	}

	@Override
	public boolean isUpToDate(String location) throws IOException {
		if(cacheValidator.isLocalValid(location)) {
			if (!hasLocal(location)) {
				return false;
			}
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
