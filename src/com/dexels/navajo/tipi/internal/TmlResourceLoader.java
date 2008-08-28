package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;

public class TmlResourceLoader extends ClassPathResourceLoader {

	private ZipResourceLoader myZipResourceLoader;
	private String myPrefix;
	
	public TmlResourceLoader(ZipResourceLoader z, String prefix) {
		myPrefix = prefix;
		myZipResourceLoader = z;
	}

	public URL getResourceURL(String loc) throws IOException {
		String location = myPrefix + loc;
		System.err.println("Trying to locate in zip: "+location);
		return myZipResourceLoader.getResourceURL(location);
	}

	public InputStream getResourceStream(String loc) throws IOException {
		String location = myPrefix + loc;
		System.err.println("Trying to locate in zip: "+location);
		return myZipResourceLoader.getResourceStream(location);
	
	}
}
