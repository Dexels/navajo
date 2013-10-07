package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.File;
import java.net.URL;

public class ManagedGeneralCacheManager extends GeneralCacheManager {

	public ManagedGeneralCacheManager(File baseDir, URL baseUrl) {
		super(new FileLocalStorage(baseDir),new HttpRemoteStorage(baseUrl),new LocalDigestCacheValidator());
	}
}
