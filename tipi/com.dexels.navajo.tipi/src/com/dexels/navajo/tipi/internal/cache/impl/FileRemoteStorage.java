/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class FileRemoteStorage implements RemoteStorage {
	private File base = null;

	public FileRemoteStorage(File base) {
		this.base = base;
	}

	@Override
	public InputStream getContents(String location, Map<String, Object> metadata)
			throws IOException {
		File u = new File(base, location);
		if (!u.exists()) {
			return null;
		}
		metadata.put("length", u.length());

		FileInputStream fis = new FileInputStream(u);
		return fis;
	}

	@Override
	public long getRemoteModificationDate(String location) throws IOException {
		File u = new File(base, location);
		return u.lastModified();
	}

	@Override
	public URL getURL(String location) throws IOException {
		File u = new File(base, location);
		return u.toURI().toURL();
	}

}
