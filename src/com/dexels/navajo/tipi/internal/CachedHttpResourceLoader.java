package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;

import com.dexels.navajo.tipi.internal.cache.*;
import com.dexels.navajo.tipi.internal.cache.impl.*;

public class CachedHttpResourceLoader extends CachedResourceLoader {

	protected final CacheManager cache;

	public CachedHttpResourceLoader(File baseDir, URL baseUrl) {
		cache = new GeneralCacheManager(new FileLocalStorage(baseDir), new HttpRemoteStorage(baseUrl));
	}

	public CacheManager getCacheManager() {
		return cache;
	}

}
