package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;

public class FileResourceLoader extends ClassPathResourceLoader {

	private final File baseFile;

	public FileResourceLoader(File baseFile) {
		this.baseFile = baseFile;
	}

	public URL getResourceURL(String location) throws MalformedURLException {
//		System.err.println("FILE: LOOKING FOR: "+location);
		File f = null;
		
		if (baseFile==null) {
			f = new File(location);
		} else {
			f = new File(baseFile,location);
		}
		if(!f.exists()) {
			return super.getResourceURL(location);
		}
		URL u =  f.toURI().toURL();
		//URL u = new URL(baseURL, location);
		// System.err.println("HttpResourceLoader: Resolved to : "+u+" base:
		// "+baseURL);
		if (u == null) {
			return super.getResourceURL(location);
		}
		return u;
	}

	public InputStream getResourceStream(String location) throws IOException {
//		System.err.println("Stream: FILE: LOOKING FOR: "+location);
		URL u = getResourceURL(location);
		InputStream is = null;
		try {
			is = u.openStream();
		} catch (IOException e) {
		}
		if (is != null) {
			return is;
		}
//		System.err.println("FileResourceLoader failed. Looking in classpath: "+location+" base: "+baseFile);
		return super.getResourceStream(location);
	}
}
