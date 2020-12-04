/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.CacheManager;
import com.dexels.navajo.tipi.internal.cache.CacheValidator;
import com.dexels.navajo.tipi.internal.cache.ClassLoaderStorage;
import com.dexels.navajo.tipi.internal.cache.LocalStorage;
import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class GeneralCacheManager implements CacheManager {
    private final ExecutorService executorService = Executors.newFixedThreadPool(1); 

    private final ClassLoaderStorage classLoaderStorage;
	private final LocalStorage local;
	private final RemoteStorage remote;
	private final CacheValidator cacheValidator;
	private final String id;
	
	private final static Logger logger = LoggerFactory.getLogger(GeneralCacheManager.class);
	
	public GeneralCacheManager(ClassLoaderStorage c, LocalStorage l, RemoteStorage r, CacheValidator vc, String id) {
		this.classLoaderStorage = c;
		this.local = l;
		this.remote = r;
		this.cacheValidator = vc;
		this.id = id;
	}

	@Override
	public InputStream getContents(String location) throws IOException {
		if (classLoaderStorage.hasLocal(location)) {
			if ( cacheValidator.isClassLoaderValid(location)) {
				return classLoaderStorage.getLocalData(location);
			} else {
				logger.info("Invalidating location in ClassLoaderStorage: {}", location);
				classLoaderStorage.invalidate(location);
			}
		}
		
		final boolean isUpToDate = isUpToDate(location);
		logger.debug("Is up to date: {} == {}",location,isUpToDate);
		if (isUpToDate) {
			return local.getLocalData(location);
		}
		return downloadLocation(location);
	}

	private InputStream downloadLocation(final String location) throws IOException {
		final Map<String, Object> metadata = new HashMap<String, Object>();
		InputStream is = remote.getContents(location, metadata);
		
		if (is == null) {
            logger.warn("Error while downloading location {} : No such item.");
            return null;
        }
		
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copyResource(baos, is);
        is.close();
        
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    local.storeData(location, new ByteArrayInputStream(baos.toByteArray()) , metadata);
                    cacheValidator.update(location);
                } catch (IOException e) {
                    logger.error("IOException on updating local cache for {}", location, e);
                }
            }
        });
        
		return new ByteArrayInputStream(baos.toByteArray());
	}
	
	   

	@Override
	public boolean hasLocal(String location) throws IOException {
		
		boolean hasLocal = local.hasLocal(location);
		logger.debug("Local present for location: {} == {} ",location,hasLocal);
		return hasLocal;
	}

	@Override
	public boolean isUpToDate(String location) throws IOException {
		if(cacheValidator.isLocalValid(location)) {
			if (!hasLocal(location)) {
				logger.debug("Not up to date: {}, has no local",location);
				return false;
			}
			logger.debug("Up to date: {}, local found",location);
			return true;
		}
		logger.debug("Not up to date: {}, local invalid",location);
		return false;
	}

	@Override
	public URL getLocalURL(String location) throws IOException {
		
		if (classLoaderStorage.hasLocal(location)) {
			if ( cacheValidator.isClassLoaderValid(location)) {
				return classLoaderStorage.getURL(location, null);
			} else {
				logger.info("Invalidating location in ClassLoaderStorage: {}", location);
				classLoaderStorage.invalidate(location);
			}
		}
		
	    InputStream is = null;
		if (!isUpToDate(location)) {
			logger.debug("Not up to date, downloading {}",location);
			is = downloadLocation(location);
		} else {
		    is =  local.getLocalData(location);
		}
		URL localURL = local.getURL(location, is);
		if(localURL==null) {
			// localstorage does not support 'direct' url's, create temp one:
			return createTempURL(local.getLocalData(location));
		} else {
			return localURL;
		}
	}

	private URL createTempURL(InputStream localData) throws IOException {
		File f = File.createTempFile("tipiCache", "");
//		InputStream is = getLocalData(location);
		OutputStream os = new FileOutputStream(f);
		copyResource(os, localData);
		f.deleteOnExit();
		return f.toURI().toURL();
	}

	@Override
	public URL getRemoteURL(String location) throws IOException {
		return remote.getURL(location);
	}

	@Override
	public void flushCache() {
		try {
			local.flushAll();
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
	}
	
	@Override
	public void invalidate() {
		cacheValidator.invalidate();
	}

	private final void copyResource(OutputStream out, InputStream in)
			throws IOException {
		// BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) > -1) {
			// logger.debug("Read: "+read+" bytes from class: "+in);
			bout.write(buffer, 0, read);
		}
		in.close();
		bout.flush();
		bout.close();
	}
	

}
