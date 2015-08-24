package com.dexels.navajo.tipi.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class ClassPathResourceLoader implements TipiResourceLoader, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7334859258749849005L;

	@Override
	public URL getResourceURL(String location) throws IOException {

		return getClassResourceURL(location);
	}

	private URL getClassResourceURL(String location) {
		ClassLoader classLoader = getClass().getClassLoader();
		// this is nuts... right?
		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
			
		}
		return classLoader.getResource(location);
	}

	@Override
	public InputStream getResourceStream(String location) throws IOException {
		URL u = getClassResourceURL(location);
		if (u == null) {
			return null;
		}
		return u.openStream();
	}

	@Override
	public OutputStream writeResource(String resourceName) throws IOException {
		throw new UnsupportedOperationException(
				"The classpath resource loader is unable to write to the resource path");

	}

	@Override
	public List<File> getAllResources() throws IOException {
		throw new UnsupportedOperationException(
				"The classpath resource loader is unable to enumerate resources");
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	@Override
	public void flushCache() {
		// DO NOTHING
	}

	@Override
	public void invalidate() throws IOException {
		// DO NOTHING
	}
}
