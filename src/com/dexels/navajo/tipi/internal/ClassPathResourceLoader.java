package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClassPathResourceLoader implements TipiResourceLoader {

	public URL getResourceURL(String location) throws IOException {

		return getClassResourceURL(location);
	}

	private URL getClassResourceURL(String location) {
		return getClass().getClassLoader().getResource(location);
	}

	public InputStream getResourceStream(String location) throws IOException {
		URL u = getClassResourceURL(location);
		if (u == null) {
			return null;
		}
		return u.openStream();
	}


	public OutputStream writeResource(String resourceName) throws IOException {
		throw new UnsupportedOperationException("The classpath resource loader is unable to write to the resource path");
			
	}

	public List<File> getAllResources() throws IOException {
		throw new UnsupportedOperationException("The classpath resource loader is unable to enumerate resources");
}

	public boolean isReadOnly() {
		return true;
	}
}
