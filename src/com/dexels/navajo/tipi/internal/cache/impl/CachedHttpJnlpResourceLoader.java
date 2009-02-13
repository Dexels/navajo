package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.*;
import java.net.*;

import javax.jnlp.UnavailableServiceException;

import com.dexels.navajo.tipi.internal.CachedResourceLoader;
import com.dexels.navajo.tipi.internal.cache.*;
import com.dexels.navajo.tipi.internal.cache.impl.*;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class CachedHttpJnlpResourceLoader extends CachedResourceLoader {

	protected final CacheManager cache;

	public CachedHttpJnlpResourceLoader(String relativePath, URL baseUrl, CookieManager cm) throws UnavailableServiceException {
		System.err.println("JNLP Local storage instantiated");
		cache = new GeneralCacheManager(new JnlpLocalStorage(relativePath,cm), new HttpRemoteStorage(baseUrl));
	}

	public CacheManager getCacheManager() {
		return cache;
	}

}
