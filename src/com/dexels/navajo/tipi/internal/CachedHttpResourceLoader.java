package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;
import java.util.*;

public class CachedHttpResourceLoader extends HttpResourceLoader {

	//http://www.sportlink.com/knvb/club-asp/6/productie/tipi/init.xml
	public CachedHttpResourceLoader(URL baseURL) {
		super(baseURL);
	}

	@Override
	public InputStream getResourceStream(String location) throws IOException {
		
		URL u = getResourceURL(location);
		System.err.println("Lookin: "+u);
		HttpURLConnection urlc = (HttpURLConnection)u.openConnection();
		((HttpURLConnection) urlc).setRequestMethod("HEAD");
		Map<String,List<String>> m =  urlc.getHeaderFields();
		long l = urlc.getLastModified();
		System.err.println("Last mod.: "+new Date(l));
		System.err.println("headers: "+m);
		return super.getResourceStream(location);
	}
	
	public static void main(String[] args) throws IOException {
		CachedHttpResourceLoader ch = new CachedHttpResourceLoader(new URL("http://www.sportlink.com/knvb/club-asp/6/productie/"));
		ch.getResourceStream("tipi/init.xml");
	}

}
