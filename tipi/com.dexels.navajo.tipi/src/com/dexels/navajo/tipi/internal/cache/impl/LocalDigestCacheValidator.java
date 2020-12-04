/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.CacheValidator;
import com.dexels.navajo.tipi.internal.cache.LocalStorage;
import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class LocalDigestCacheValidator implements CacheValidator {

	private static final String CLASSLOADER_DIGEST_PROPERTIES = "remotedigest.properties";
	private static final String LOCAL_DIGEST_PROPERTIES = "digest.properties";
	private static final String REMOTE_DIGEST_PROPERTIES = "remotedigest.properties";
	private Properties classLoaderDigestProperties = new Properties();
	private Properties localDigestProperties = new Properties();
	private Properties remoteDigestProperties = new Properties();
	
	private final static Logger logger = LoggerFactory
			.getLogger(LocalDigestCacheValidator.class);
	
	private LocalStorage localStorage;
	private RemoteStorage remoteStorage;
	private String id;
	
	public LocalDigestCacheValidator()  {

	}
	
	public void activate() throws IOException {
		loadClassLoaderDigestFile(CLASSLOADER_DIGEST_PROPERTIES);
		loadDigestFile(LOCAL_DIGEST_PROPERTIES);
		loadRemoteDigestFile(REMOTE_DIGEST_PROPERTIES);
		
	}

	@Override
	public boolean isClassLoaderValid(String location) throws IOException {
		String localDigest = (String) classLoaderDigestProperties.get(location);
		return validateDigest(location, localDigest);
	}

	
	@Override
	public boolean isLocalValid(String location) throws IOException {
		String localDigest = (String) localDigestProperties.get(location);
		return validateDigest(location, localDigest);
	}
	

	private boolean validateDigest(String location, String localDigest) throws IOException {
		String remoteDigest = (String) remoteDigestProperties.get(location);
		if(remoteDigest==null) {
			logger.debug("No remote found for: {}", location);
			throw new IOException("Resource absent: " + location);
		}
		if(localDigest==null) {
			logger.debug("No digest found for location: {}",location);
			return false;
		}
		final boolean equals = localDigest.equals(remoteDigest);
		return equals;
	}



	@Override
	public void setLocalStorage(LocalStorage localStorage) {
		this.localStorage = localStorage;
	}

	@Override
	public void setRemoteStorage(RemoteStorage remoteStorage) {
		this.remoteStorage = remoteStorage;
	}

	private void loadRemoteDigestFile(String location) throws IOException {
		Map<String, Object> metadata = new HashMap<String, Object>();
		InputStream in = remoteStorage.getContents(location, metadata);
		if(in!=null) {
			remoteDigestProperties.load(in);
		} else {
			logger.warn("No remote digest properties found");
			throw new IOException("No remote digest properties found");
		}
		
	}
	
	@Override
	public void invalidate() {
		localDigestProperties.clear();
		localStorage.delete(LOCAL_DIGEST_PROPERTIES);
	}
	
	private void loadDigestFile(String location) throws IOException {
		InputStream in = localStorage.getLocalData(location);
		logger.debug("Getting location: "+location);
		if(in !=null) {
			try {
				localDigestProperties.load(in);
			} catch (IOException e) {
				logger.warn("Error opening digest file. Corrupt? Throwing file away.");
				localDigestProperties.clear();
				localStorage.delete(location);
				throw new IOException("Error opening digest file. Corrupt? Throwing file away.", e);
			} finally {
				if(in!=null) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
	
	private void loadClassLoaderDigestFile(String location) throws IOException {
		InputStream in = getClassResourceStream(location);
		logger.debug("Getting classloader location: " + location);
		if (in != null) {
			try {
				classLoaderDigestProperties.load(in);
			} catch (IOException e) {
				logger.warn("Error opening classloader digest file. Corrupt?");
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	public void setId(String id) {
		this.id = id;
	}


	@Override
    public void update(final String location) throws IOException {

        localDigestProperties.put(location, remoteDigestProperties.get(location));
        // localStorage.s
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        localDigestProperties.store(baos, "Update at: " + new Date());
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Map<String, Object> metadata = new HashMap<String, Object>();
        localStorage.storeData(LOCAL_DIGEST_PROPERTIES, bais, metadata);
        logger.debug("Saved local digest: {}, and location: ", LOCAL_DIGEST_PROPERTIES, location);
        bais.close();

    }
	
	private InputStream getClassResourceStream(String location) throws IOException {
		URL u = getClassResourceURL(location);
		if (u == null) {
			return null;
		}
		return u.openStream();
	}
	
	private URL getClassResourceURL(String location) {
		ClassLoader classLoader = getClass().getClassLoader();
		// this is nuts... right?
		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
			
		}
		return classLoader.getResource(id + "/" + location);
	}
}
