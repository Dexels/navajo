package com.dexels.navajo.tipi.internal;

import java.io.*;

import com.dexels.navajo.tipi.internal.cache.*;
import com.dexels.navajo.tipi.internal.cache.impl.*;

public class CachedFileResourceLoader extends CachedResourceLoader {

	protected final CacheManager cache;
	
	public CachedFileResourceLoader(File baseDir, File baseRemoteFile) {
		cache = new GeneralCacheManager(new FileLocalStorage(baseDir),new FileRemoteStorage(baseRemoteFile));
	}

	public CacheManager getCacheManager() {
		return cache;
	}

}
