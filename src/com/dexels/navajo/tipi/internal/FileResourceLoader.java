package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileResourceLoader extends ClassPathResourceLoader {

	private File baseFile;

	public FileResourceLoader(File baseFile) {
		// System.err.println("(Constr.) FileResourceLoader: Basefile set to: "+
		// baseFile);
		// Thread.dumpStack();
		this.baseFile = baseFile;
	}

	public void setBaseFile(File baseFile) {
		this.baseFile = baseFile;
		// System.err.println("FileResourceLoader: Basefile set to: "+baseFile);
	}

	public String toString() {
		return "FILELOADER: " + baseFile.toString();

	}

	@Override
	public OutputStream writeResource(String resourceName) throws IOException {
		File res = new File(baseFile, resourceName);
		FileOutputStream fos = new FileOutputStream(res);
		return fos;
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	public URL getResourceURL(String location) throws IOException {
		File f = null;
		if (baseFile == null) {
			f = new File(location);
			// System.err.println("Null basefile, using relative path: "+f);
		} else {
			f = new File(baseFile, location);
		}
		// System.err.println("Created basefile link: "+f.getAbsolutePath());
		if (!f.exists()) {
			return super.getResourceURL(location);
		}
		URL u = f.toURI().toURL();
		// URL u = new URL(baseURL, location);
		// System.err.println("FileResourceLoader: Resolved to : "+u);
		// "+baseURL);
		if (u == null) {
			return super.getResourceURL(location);
		}
		// System.err.println("Existing & ok: "+u);
		return u;
	}

	public InputStream getResourceStream(String location) throws IOException {
		// System.err.println("Stream: FILE: LOOKING FOR: "+location);
		URL u = getResourceURL(location);
		// System.err.println("Url resolved: "+u);
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
		System.err.println("FileResourceLoader failed. Looking in classpath: " + location + " base: " + baseFile);
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

	public List<File> getAllResources() throws IOException {
		List<File> result = new ArrayList<File>();
		listAll(result, baseFile);
		return result;
	}
}
