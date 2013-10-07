package com.dexels.navajo.tipi.internal;

import java.io.File;

import com.dexels.navajo.tipi.internal.cache.CacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.FileLocalStorage;
import com.dexels.navajo.tipi.internal.cache.impl.FileRemoteStorage;
import com.dexels.navajo.tipi.internal.cache.impl.GeneralCacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.LocalDigestCacheValidator;

public class CachedFileResourceLoader extends CachedResourceLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5226489330806491180L;
	protected final CacheManager cache;

	public CachedFileResourceLoader(File baseDir, File baseRemoteFile) {
		cache = new GeneralCacheManager(new FileLocalStorage(baseDir),
				new FileRemoteStorage(baseRemoteFile), new LocalDigestCacheValidator());
	}

	public CacheManager getCacheManager() {
		return cache;
	}

}
