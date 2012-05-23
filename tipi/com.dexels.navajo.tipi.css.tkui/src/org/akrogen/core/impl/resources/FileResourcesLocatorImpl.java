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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;

import org.akrogen.core.resources.IResourceLocator;

/**
 * Basic File resources locator implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class FileResourcesLocatorImpl implements IResourceLocator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.core.resources.IURIResolver#resolve(java.lang.String)
	 */
	public String resolve(String uri) {
		return uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.core.resources.IResourceLocator#getInputStream(java.lang.String)
	 */
	public InputStream getInputStream(String uri) throws Exception {
		return new FileInputStream(new File(uri));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.core.resources.IResourceLocator#getReader(java.lang.String)
	 */
	public Reader getReader(String uri) throws Exception {
		return new FileReader(new File(uri));
	}
}
