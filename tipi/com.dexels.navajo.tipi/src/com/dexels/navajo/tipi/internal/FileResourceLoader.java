/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileResourceLoader extends ClassPathResourceLoader {

	private static final long serialVersionUID = -3138445157642602156L;
	private File baseFile;

	public FileResourceLoader(File baseFile) {
		this.baseFile = baseFile;
	}

	public void setBaseFile(File baseFile) {
		this.baseFile = baseFile;
	}

	@Override
	public String toString() {
		return "FILELOADER: " + baseFile.toString()+" full: "+baseFile.getAbsolutePath();

	}

	@Override
	public OutputStream writeResource(String resourceName) throws IOException {
		File res = new File(baseFile, resourceName);
		if (!res.getParentFile().exists()) {
			res.getParentFile().mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(res);
		return fos;
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public URL getResourceURL(String location) throws IOException {
		File f = null;
		if (baseFile == null) {
			f = new File(location);
		} else {
			f = new File(baseFile, location);
		}

		if (!f.exists()) {
			return super.getResourceURL(location);
		}
		URL u = f.toURI().toURL();
		if (u == null) {
			return super.getResourceURL(location);
		}
		return u;
	}

	@Override
	public InputStream getResourceStream(String location) throws IOException {
		URL u = getResourceURL(location);
		if (u == null) {
			return super.getResourceStream(location);
		}
		InputStream is = null;
		try {
			is = u.openStream();
		} catch (IOException e) {
		}
		if (is != null) {
			return is;
		}
		return super.getResourceStream(location);
	}

	private void listAll(List<File> result, File current) {
		File[] ff = current.listFiles();
		for (int i = 0; i < ff.length; i++) {
			if (ff[i].isDirectory()) {
				listAll(result, ff[i]);
			} else {
				result.add(ff[i]);
			}
		}
	}

	@Override
	public List<File> getAllResources() throws IOException {
		List<File> result = new ArrayList<File>();
		listAll(result, baseFile);
		return result;
	}
}
