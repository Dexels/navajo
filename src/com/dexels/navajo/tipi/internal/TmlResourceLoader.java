package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

import net.java.javafx.jazz.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.*;

public class TmlResourceLoader extends ClassPathResourceLoader {

	private ZipResourceLoader myZipResourceLoader;
	private String myPrefix;
	
	public TmlResourceLoader(ZipResourceLoader z, String prefix) throws IOException {
		myPrefix = prefix;
		myZipResourceLoader = z;
	}

	public URL getResourceURL(String loc) throws MalformedURLException {
		String location = myPrefix + loc;
		System.err.println("Trying to locate in zip: "+location);
		return myZipResourceLoader.getResourceURL(location);
	}

	public InputStream getResourceStream(String loc) throws IOException {
		String location = myPrefix + loc;
		System.err.println("Trying to locate in zip: "+location);
		return myZipResourceLoader.getResourceStream(location);
	
	}
}
