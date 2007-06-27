package com.dexels.navajo.tipi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class ClassPathResourceLoader implements TipiResourceLoader {
	
	 public URL getResourceURL(String location) throws MalformedURLException {

		 return getClassResourceURL(location);
	 }

	 private URL getClassResourceURL(String location) throws MalformedURLException {
		 return getClass().getClassLoader().getResource(location);
	 }  
	
	  public InputStream getResourceStream(String location) throws IOException {
			URL u = getClassResourceURL(location);
			if(u==null) {
				System.err.println("Problem getting resource from classpath: "+location);
				return null;
			}
		  return u.openStream();
	  }
}
