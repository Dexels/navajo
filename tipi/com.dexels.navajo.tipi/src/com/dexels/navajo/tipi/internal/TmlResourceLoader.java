package com.dexels.navajo.tipi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Deprecated
public class TmlResourceLoader extends ClassPathResourceLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = -690976439637837136L;
	private ZipResourceLoader myZipResourceLoader;
	private String myPrefix;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TmlResourceLoader.class);
	
	public TmlResourceLoader(ZipResourceLoader z, String prefix) {
		myPrefix = prefix;
		myZipResourceLoader = z;
	}

	@Override
	public URL getResourceURL(String loc) throws IOException {
		String location = myPrefix + loc;
		logger.info("Trying to locate in zip: " + location);
		return myZipResourceLoader.getResourceURL(location);
	}

	@Override
	public InputStream getResourceStream(String loc) throws IOException {
		String location = myPrefix + loc;
		logger.info("Trying to locate in zip: " + location);
		return myZipResourceLoader.getResourceStream(location);

	}
}
