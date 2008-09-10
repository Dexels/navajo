package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.*;
import java.net.*;

import com.dexels.navajo.tipi.internal.cache.*;

public class FileRemoteStorage implements RemoteStorage {
	private File base = null;

	public FileRemoteStorage(File base) {
		this.base = base;
	}

	public InputStream getContents(String location) throws IOException {
		File u = new File(base, location);
		FileInputStream fis = new FileInputStream(u);
		return fis;
	}

	public long getRemoteModificationDate(String location) throws IOException {
		File u = new File(base, location);
		return u.lastModified();
	}

	public URL getURL(String location) throws IOException {
		File u = new File(base, location);
		return u.toURL();
	}

}
