package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.*;
import java.net.*;
import java.util.Map;

import sun.net.www.protocol.file.FileURLConnection;

import com.dexels.navajo.tipi.internal.cache.*;

public class HttpRemoteStorage implements RemoteStorage {
	private URL baseUrl = null;

	public HttpRemoteStorage(URL base) {
		baseUrl = base;
	}

	public InputStream getContents(String location, Map<String,Object> metadata) throws IOException {
		URL u = new URL(baseUrl, location);
		InputStream is = null;
		try {
			System.err.println("Opening location: "+u);
			URLConnection uc = u.openConnection();
			metadata.put("length", uc.getContentLength());
			metadata.put("encoding", uc.getContentEncoding());
			metadata.put("type", uc.getContentType());
			is =  uc.getInputStream();
			
		} catch (FileNotFoundException e) {
			System.err.println("Remote location: "+location+" not found");
		}
		return is;
	}

	public long getRemoteModificationDate(String location) throws IOException {
		URL u = new URL(baseUrl, location);
		URLConnection connection = u.openConnection();
		if(connection instanceof HttpURLConnection) {
			HttpURLConnection urlc = (HttpURLConnection) connection;
			urlc.setRequestMethod("HEAD");
		}
		return connection.getLastModified();
		
	}

	public URL getURL(String location) throws IOException {
		return new URL(baseUrl, location);
	}

}
