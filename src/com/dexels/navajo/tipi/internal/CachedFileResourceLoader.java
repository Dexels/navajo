package com.dexels.navajo.tipi.internal;

import java.io.File;

import com.dexels.navajo.tipi.internal.cache.CacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.FileLocalStorage;
import com.dexels.navajo.tipi.internal.cache.impl.FileRemoteStorage;
import com.dexels.navajo.tipi.internal.cache.impl.GeneralCacheManager;

public class CachedFileResourceLoader extends CachedResourceLoader {

	protected final CacheManager cache;

	public CachedFileResourceLoader(File baseDir, File baseRemoteFile) {
		cache = new GeneralCacheManager(new FileLocalStorage(baseDir), new FileRemoteStorage(baseRemoteFile));
	}

	public CacheManager getCacheManager() {
		return cache;
	}

	
}
