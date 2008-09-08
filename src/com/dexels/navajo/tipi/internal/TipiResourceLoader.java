package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;
import java.util.*;

public interface TipiResourceLoader {
	
	/**
	 * Tries to read a location from this loader
	 * @param location
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getResourceURL(String location) throws IOException;

	/**
	 * Reads a resource to a stream.
	 * @param location
	 * @return
	 * @throws IOException
	 */
	public InputStream getResourceStream(String location) throws IOException;

	/**
	 * Writes to this resource loader. This method may throw an UnsupportedOperationException if 
	 * this implementations does not support this. (http/classloader do not support it)
	 * @param resourceName
	 * @return
	 * @throws IOException
	 */
	public OutputStream writeResource(String resourceName) throws IOException;

	/**
	 * Enumerates all the resource, only supported in the file implementation, http/ classloader will
	 * throw a UnsupportedOperationException
	 * @return
	 * @throws IOException
	 */
	public List<File> getAllResources() throws IOException;

	public boolean isReadOnly();
}
