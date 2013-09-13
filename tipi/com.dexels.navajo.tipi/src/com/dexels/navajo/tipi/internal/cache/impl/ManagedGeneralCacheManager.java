package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.File;
import java.net.URL;

import com.dexels.navajo.tipi.internal.cache.LocalStorage;
import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class ManagedGeneralCacheManager extends GeneralCacheManager {

	public ManagedGeneralCacheManager(File baseDir, URL baseUrl) {
		super(new FileLocalStorage(baseDir),new HttpRemoteStorage(baseUrl));
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		
	}

}
