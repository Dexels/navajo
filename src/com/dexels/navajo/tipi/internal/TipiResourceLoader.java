package com.dexels.navajo.tipi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public interface TipiResourceLoader {
	 public URL getResourceURL(String location) throws MalformedURLException;
	 public InputStream getResourceStream(String location) throws IOException;
}
