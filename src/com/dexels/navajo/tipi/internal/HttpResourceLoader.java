package com.dexels.navajo.tipi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpResourceLoader extends ClassPathResourceLoader {

	private final URL baseURL;
	
	public HttpResourceLoader(URL baseURL) {
		this.baseURL = baseURL;
	}
	
	 public URL getResourceURL(String location) throws MalformedURLException {
		 URL u = new URL(baseURL,location);
//		 System.err.println("HttpResourceLoader: Resolved to : "+u+" base: "+baseURL);
		 if(u==null) {
			 return super.getResourceURL(location);
		 }
		 return u;
	 }  
	
	  public InputStream getResourceStream(String location) throws IOException {
//		  System.err.println("HttpResourceLoader.getResourceStream:  "+location+" base: "+baseURL);
			 URL u = getResourceURL(location);
		  InputStream is = null;
		  try {
			is = u.openStream();
		} catch (IOException e) {
		}
		  if(is!=null) {
			  return is;
		  }
		  return super.getResourceStream(location);
	  }
}
