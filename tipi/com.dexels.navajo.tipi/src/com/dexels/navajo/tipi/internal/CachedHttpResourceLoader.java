package com.dexels.navajo.tipi.internal;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.CacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.FileLocalStorage;
import com.dexels.navajo.tipi.internal.cache.impl.GeneralCacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.HttpRemoteStorage;
import com.dexels.navajo.tipi.internal.cache.impl.LocalDigestCacheValidator;

public class CachedHttpResourceLoader extends CachedResourceLoader {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CachedHttpResourceLoader.class);
	
	private static final long serialVersionUID = 3322463272837679890L;
	protected final CacheManager cache;

	public CachedHttpResourceLoader(String id, File baseDir, URL baseUrl) {
		final LocalDigestCacheValidator cacheValidator = new LocalDigestCacheValidator();
		final FileLocalStorage localStore = new FileLocalStorage(baseDir);
		final HttpRemoteStorage remoteStore = new HttpRemoteStorage(baseUrl);
		cache = new GeneralCacheManager(localStore,
				remoteStore, cacheValidator);
		cacheValidator.setLocalStorage(localStore);
		cacheValidator.setRemoteStorage(remoteStore);
		cacheValidator.setId(id);
		try {
			cacheValidator.activate();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	public CacheManager getCacheManager() {
		return cache;
	}

}
