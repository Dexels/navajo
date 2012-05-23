package com.dexels.navajo.tipi.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public interface TipiResourceLoader {

	/**
	 * Tries to read a location from this loader
	 * 
	 * @param location
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getResourceURL(String location) throws IOException;

	/**
	 * Reads a resource to a stream.
	 * 
	 * @param location
	 * @return
	 * @throws IOException
	 */
	public InputStream getResourceStream(String location) throws IOException;

	/**
	 * Writes to this resource loader. This method may throw an
	 * UnsupportedOperationException if this implementations does not support
	 * this. (http/classloader do not support it)
	 * 
	 * @param resourceName
	 * @return
	 * @throws IOException
	 */
	public OutputStream writeResource(String resourceName) throws IOException;

	/**
	 * Enumerates all the resource, only supported in the file implementation,
	 * http/ classloader will throw a UnsupportedOperationException
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<File> getAllResources() throws IOException;

	public boolean isReadOnly();

	/**
	 * Only applies for cached loaders. Will remove local cache
	 */
	public void flushCache() throws IOException;
}
