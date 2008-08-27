package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.*;
import java.net.*;
import java.util.*;

import com.dexels.navajo.tipi.internal.cache.*;

public class HttpRemoteStorage implements RemoteStorage {
	private URL baseUrl = null;
	
	public HttpRemoteStorage(URL base) {
		baseUrl = base;
	}
	
	public InputStream getContents(String location) throws IOException {
		URL u = new URL(baseUrl,location);
		return u.openStream();
	}

	public long getRemoteModificationDate(String location) throws IOException {
		URL u = new URL(baseUrl,location);
		HttpURLConnection urlc = (HttpURLConnection)u.openConnection();
		urlc.setRequestMethod("HEAD");
		return urlc.getLastModified();
	}

	public URL getURL(String location) throws IOException {
		return new URL(baseUrl,location);
	}




}
