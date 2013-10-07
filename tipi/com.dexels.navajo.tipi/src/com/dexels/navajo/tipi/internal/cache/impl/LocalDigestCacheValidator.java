package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private String id;
	
	public LocalDigestCacheValidator()  {

	}

	@Override
	public boolean isLocalValid(String location) throws IOException {
		String localDigest = (String) localDigestProperties.get(location);
		String remoteDigest = (String) remoteDigestProperties.get(location);
		if(remoteDigest==null) {
			logger.info("No remote found for: "+location+" assuming absent in loader: "+id);
			throw new IOException("Resource absent");
		}
		if(localDigest==null || remoteDigest == null) {
			return false;
		}
		final boolean equals = localDigest.equals(remoteDigest);
		if(!equals) {
			System.err.println("\n>>\n>>\n>> Changed to : "+location +" in loader: "+id);
		}
		return equals;
	}

	public void activate() throws IOException {
		loadDigestFile(LOCAL_DIGEST_PROPERTIES);
		loadRemoteDigestFile(REMOTE_DIGEST_PROPERTIES);
		
	}
	
	private final void copyResource(OutputStream out, InputStream in)
			throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
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

	public void setId(String id) {
		this.id = id;
	}

}
