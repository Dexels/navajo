package com.dexels.navajo.tipi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.CacheManager;

public abstract class CachedResourceLoader extends ClassPathResourceLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6716569339779493458L;
    private final static Logger logger = LoggerFactory.getLogger(CachedResourceLoader.class);
	public abstract CacheManager getCacheManager();

	@Override
	public InputStream getResourceStream(String location) throws IOException {
		InputStream contents = getCacheManager().getContents(location);
		if (contents != null) {
		    logger.debug("Returning locally CACHED version of {}", location);
			return contents;
		}
		return super.getResourceStream(location);
	}

	@Override
	public URL getResourceURL(String location) throws IOException {
		URL u = getCacheManager().getLocalURL(location);
		// URL u = getCacheManager().getRemoteURL(location);
		if (u != null) {
			return u;
		}
		return super.getResourceURL(location);
	}

	@Override
	public void flushCache() {
		CacheManager c = getCacheManager();
		if (c != null) {
			c.flushCache();
		}
	}
}
