package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileResourceLoader extends ClassPathResourceLoader {

	private File baseFile;

	public FileResourceLoader(File baseFile) {
		this.baseFile = baseFile;
	}

	public void setBaseFile(File baseFile) {
		this.baseFile = baseFile;
	}

	public String toString() {
		return "FILELOADER: "+baseFile.toString();
		
	}
	
	@Override
	public OutputStream writeResource(String resourceName) throws IOException {
		File res = new File(baseFile,resourceName);
		FileOutputStream fos = new FileOutputStream(res);
		return fos;
	}

	
	
	public URL getResourceURL(String location) throws MalformedURLException {
		File f = null;
		if (baseFile==null) {
			f = new File(location);
		} else {
			f = new File(baseFile,location);
		}
//		System.err.println("Opening file location: "+f.getAbsolutePath());

		if(!f.exists()) {
			return super.getResourceURL(location);
		}
		URL u =  f.toURI().toURL();
		//URL u = new URL(baseURL, location);
		// System.err.println("HttpResourceLoader: Resolved to : "+u+" base:
		// "+baseURL);
		if (u == null) {
			return super.getResourceURL(location);
		}
		return u;
	}

	public InputStream getResourceStream(String location) throws IOException {
//		System.err.println("Stream: FILE: LOOKING FOR: "+location);
		URL u = getResourceURL(location);
		if(u==null) {
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
//		System.err.println("FileResourceLoader failed. Looking in classpath: "+location+" base: "+baseFile);
		return super.getResourceStream(location);
	}
	
	private void listAll(List<File> result, File current) {
		File[] ff = current.listFiles();
		for (int i = 0; i < ff.length; i++) {
			if(ff[i].isDirectory()) {
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
