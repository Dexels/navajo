package com.dexels.navajo.tipi.internal;

import java.io.File;
import java.net.URL;

import com.dexels.navajo.tipi.internal.cache.CacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.FileLocalStorage;
import com.dexels.navajo.tipi.internal.cache.impl.GeneralCacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.HttpRemoteStorage;

public class CachedHttpResourceLoader extends CachedResourceLoader {

	protected final CacheManager cache;

	public CachedHttpResourceLoader(File baseDir, URL baseUrl) {
		cache = new GeneralCacheManager(new FileLocalStorage(baseDir),
				new HttpRemoteStorage(baseUrl));
	}

	public CacheManager getCacheManager() {
		return cache;
	}

}
