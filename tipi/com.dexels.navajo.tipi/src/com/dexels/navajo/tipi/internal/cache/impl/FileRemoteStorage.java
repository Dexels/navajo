package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class FileRemoteStorage implements RemoteStorage {
	private File base = null;

	public FileRemoteStorage(File base) {
		this.base = base;
	}

	@Override
	public InputStream getContents(String location, Map<String, Object> metadata)
			throws IOException {
		File u = new File(base, location);
		if (!u.exists()) {
			return null;
		}
		metadata.put("length", u.length());

		FileInputStream fis = new FileInputStream(u);
		return fis;
	}

	@Override
	public long getRemoteModificationDate(String location) throws IOException {
		File u = new File(base, location);
		return u.lastModified();
	}

	@Override
	public URL getURL(String location) throws IOException {
		File u = new File(base, location);
		return u.toURI().toURL();
	}

}
