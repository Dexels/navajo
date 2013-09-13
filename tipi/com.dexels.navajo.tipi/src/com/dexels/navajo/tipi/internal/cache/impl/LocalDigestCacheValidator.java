package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.security.action.LoadLibraryAction;

import com.dexels.navajo.tipi.internal.cache.CacheValidator;
import com.dexels.navajo.tipi.internal.cache.LocalStorage;
import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class LocalDigestCacheValidator implements CacheValidator {

	private static final String LOCAL_DIGEST_PROPERTIES = "digest.properties";
	private static final String REMOTE_DIGEST_PROPERTIES = "remotedigest.properties";
	private Properties localDigestProperties = new Properties();
	private Properties remoteDigestProperties = new Properties();

	
	private final static Logger logger = LoggerFactory
			.getLogger(LocalDigestCacheValidator.class);
	
	private LocalStorage localStorage;
	private RemoteStorage remoteStorage;
	
	@Override
	public boolean isLocalValid(String location) throws IOException {
		String localDigest = (String) localDigestProperties.get(location);
		String remoteDigest = (String) remoteDigestProperties.get(location);
		if(localDigest==null || remoteDigest == null) {
			return false;
		}
		return localDigest.equals(remoteDigest);
	}

	public void activate() throws IOException {
		loadDigestFile(LOCAL_DIGEST_PROPERTIES);
		loadRemoteDigestFile(REMOTE_DIGEST_PROPERTIES);
		
	}

	public void setLocalStorage(LocalStorage localStorage) {
		this.localStorage = localStorage;
	}

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
		}
		
	}
	private void loadDigestFile(String location) throws IOException {
		InputStream in = localStorage.getLocalData(location);
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

}
