package com.dexels.navajo.tipi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TmlResourceLoader extends ClassPathResourceLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = -690976439637837136L;
	private ZipResourceLoader myZipResourceLoader;
	private String myPrefix;

	public TmlResourceLoader(ZipResourceLoader z, String prefix) {
		myPrefix = prefix;
		myZipResourceLoader = z;
	}

	public URL getResourceURL(String loc) throws IOException {
		String location = myPrefix + loc;
		System.err.println("Trying to locate in zip: " + location);
		return myZipResourceLoader.getResourceURL(location);
	}

	public InputStream getResourceStream(String loc) throws IOException {
		String location = myPrefix + loc;
		System.err.println("Trying to locate in zip: " + location);
		return myZipResourceLoader.getResourceStream(location);

	}
}
