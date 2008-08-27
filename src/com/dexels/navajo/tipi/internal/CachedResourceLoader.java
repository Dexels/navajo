package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;
import java.util.*;

import com.dexels.navajo.tipi.internal.cache.*;
import com.dexels.navajo.tipi.internal.cache.impl.*;

public abstract class CachedResourceLoader extends ClassPathResourceLoader {

	public abstract CacheManager getCacheManager();


	public InputStream getResourceStream(String location) throws IOException {
		InputStream contents = getCacheManager().getContents(location);
		if(contents!=null) {
			return contents;
		}
		return super.getResourceStream(location);
	}


	@Override
	public URL getResourceURL(String location) throws IOException {
		URL u =  getCacheManager().getLocalURL(location);
//		URL u = getCacheManager().getRemoteURL(location);
		if(u!=null) {
			return u;
		}
		return super.getResourceURL(location);
	}
	

}
