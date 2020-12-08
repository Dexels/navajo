/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	
	@Override
	public void invalidate() throws IOException {
		getCacheManager().invalidate();
	}

	
}
