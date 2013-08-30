package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class HttpRemoteStorage implements RemoteStorage {
	private URL baseUrl = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(HttpRemoteStorage.class);
	
	public HttpRemoteStorage(URL base) {
		baseUrl = base;
	}

	@Override
	public InputStream getContents(String location, Map<String, Object> metadata)
			throws IOException {
		URL u = new URL(baseUrl, location);
		InputStream is = null;
		try {
			logger.info("DOWNLOADING location: " + u);
			URLConnection uc = u.openConnection();
			uc.addRequestProperty("Accept-Encoding", "gzip");
			metadata.put("length", uc.getContentLength());
			metadata.put("encoding", uc.getContentEncoding());
			metadata.put("type", uc.getContentType());
			// Should't I check the encoding and gunzip if necessary?
			// Or is that taken care of further downstream?
			is = uc.getInputStream();

		} catch (FileNotFoundException e) {
			logger.error("Remote location: " + location + " not found",e);
		}
		return is;
	}

	@Override
	public long getRemoteModificationDate(String location) throws IOException {
		if(true) {
			return 0;
		}
		logger.info("Checking modification date of location: "+location);
		URL u = new URL(baseUrl, location);
		URLConnection connection = u.openConnection();
		if (connection instanceof HttpURLConnection) {
			HttpURLConnection urlc = (HttpURLConnection) connection;
			urlc.setRequestMethod("HEAD");
		}
		return connection.getLastModified();

	}

	@Override
	public URL getURL(String location) throws IOException {
		return new URL(baseUrl, location);
	}

}
