/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
