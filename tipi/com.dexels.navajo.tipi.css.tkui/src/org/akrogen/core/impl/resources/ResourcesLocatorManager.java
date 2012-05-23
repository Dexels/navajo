/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.core.impl.resources;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.akrogen.core.resources.IResourceLocator;
import org.akrogen.core.resources.IResourcesLocatorManager;
import org.akrogen.core.utils.StringUtils;

/**
 * Resources locator manager implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class ResourcesLocatorManager implements IResourcesLocatorManager {

	/**
	 * ResourcesLocatorManager Singleton
	 */
	public static final IResourcesLocatorManager INSTANCE = new ResourcesLocatorManager();

	/**
	 * List of IResourceLocator instance which was registered.
	 */
	private List uriResolvers = null;

	public ResourcesLocatorManager() {		
		registerResourceLocator(new HttpResourcesLocatorImpl());
		registerResourceLocator(new FileResourcesLocatorImpl());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.core.resources.IResourcesLocatorManager#registerResourceLocator(org.akrogen.core.resources.IResourceLocator)
	 */
	public void registerResourceLocator(IResourceLocator resourceLocator) {
		if (uriResolvers == null)
			uriResolvers = new ArrayList();
		uriResolvers.add(resourceLocator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.core.resources.IResourcesLocatorManager#unregisterResourceLocator(org.akrogen.core.resources.IResourceLocator)
	 */
	public void unregisterResourceLocator(IResourceLocator resourceLocator) {
		if (uriResolvers == null)
			return;
		uriResolvers.remove(resourceLocator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.core.resources.IURIResolver#resolve(java.lang.String)
	 */
	public String resolve(String uri) {
		if (StringUtils.isEmpty(uri))
			return null;
		if (uriResolvers == null)
			return null;
		// Loop for IResourceLocator registered and return the uri resolved
		// as soon as an IResourceLocator return an uri resolved which is not
		// null.
		for (Iterator iterator = uriResolvers.iterator(); iterator.hasNext();) {
			IResourceLocator resolver = (IResourceLocator) iterator.next();
			String s = resolver.resolve(uri);
			if (s != null)
				return s;
		}
		return null;
	}

	public InputStream getInputStream(String uri) throws Exception {
		if (StringUtils.isEmpty(uri))
			return null;
		if (uriResolvers == null)
			return null;
		// Loop for IResourceLocator registered and return the InputStream from
		// the uri resolved
		// as soon as an IResourceLocator return an uri resolved which is not
		// null.
		for (Iterator iterator = uriResolvers.iterator(); iterator.hasNext();) {
			IResourceLocator resolver = (IResourceLocator) iterator.next();
			String s = resolver.resolve(uri);
			if (s != null) {
				InputStream inputStream = resolver.getInputStream(uri);
				if (inputStream != null)
					return inputStream;
			}
		}
		return null;
	}

	public Reader getReader(String uri) throws Exception {
		if (StringUtils.isEmpty(uri))
			return null;
		if (uriResolvers == null)
			return null;
		// Loop for IResourceLocator registered and return the Reader from
		// the uri resolved
		// as soon as an IResourceLocator return an uri resolved which is not
		// null.
		for (Iterator iterator = uriResolvers.iterator(); iterator.hasNext();) {
			IResourceLocator resolver = (IResourceLocator) iterator.next();
			String s = resolver.resolve(uri);
			if (s != null) {
				Reader reader = resolver.getReader(uri);
				if (reader  != null)
					return reader;
			}
		}

		return null;
	}

}
