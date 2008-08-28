package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.*;
import java.net.*;

import com.dexels.navajo.tipi.internal.cache.*;

public class FileLocalStorage implements LocalStorage {

	private final File baseFile;

	public FileLocalStorage(File base) {
		this.baseFile = base;
	}

	public void flush(String location) {
		if(hasLocal(location)) {
			File f = new File(baseFile,convertPath(location));
			f.delete();
		}
	}

	public void flushAll(String location) {
		System.err.println("FLUSHALL: todo: Implement");
	}

	public InputStream getLocalData(String location) throws IOException {
		if(hasLocal(location)) {
			File f = new File(baseFile,convertPath(location));
			FileInputStream fis = new FileInputStream(f);
			return fis;
		}
		return null;
	}

	public long getLocalModificationDate(String location) throws IOException {
		if(hasLocal(location)) {
			File f = new File(baseFile,convertPath(location));
			return f.lastModified();
		}
		throw new IOException("File not found. Shouldn't happen");
	}

	public boolean hasLocal(String location) {
		File f = new File(baseFile,convertPath(location));
		return f.exists();
	}

	public void storeData(String location, InputStream data) throws IOException {
		File f = new File(baseFile,convertPath(location));
		FileOutputStream fos = new FileOutputStream(f);
		copyResource(fos, data);
		
	}

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
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

	public String convertPath(String location) {
		return location.replaceAll("/", "_");
	}

	public URL getURL(String location) throws IOException {
		File f = new File(baseFile,convertPath(location));
		return f.toURL();
	}


}
