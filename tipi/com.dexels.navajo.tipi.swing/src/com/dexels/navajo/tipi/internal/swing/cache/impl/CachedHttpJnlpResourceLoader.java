package com.dexels.navajo.tipi.internal.swing.cache.impl;

import java.net.URL;

import javax.jnlp.UnavailableServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.CachedResourceLoader;
import com.dexels.navajo.tipi.internal.cache.CacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.GeneralCacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.HttpRemoteStorage;
import com.dexels.navajo.tipi.internal.cache.impl.LocalDigestCacheValidator;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class CachedHttpJnlpResourceLoader extends CachedResourceLoader {

	private static final long serialVersionUID = -7140275852274687914L;
	protected final CacheManager cache;
	private final static Logger logger = LoggerFactory
			.getLogger(CachedHttpJnlpResourceLoader.class);

	public CachedHttpJnlpResourceLoader(String relativePath, URL baseUrl,
			CookieManager cm) throws UnavailableServiceException {
		final LocalDigestCacheValidator cacheValidator = new LocalDigestCacheValidator();
		final JnlpLocalStorage localstore = new JnlpLocalStorage(relativePath, cm);
		final HttpRemoteStorage remoteStore = new HttpRemoteStorage(baseUrl);
		cache = new GeneralCacheManager(localstore,remoteStore, new LocalDigestCacheValidator());
	}

	public CacheManager getCacheManager() {
		return cache;
	}

}
