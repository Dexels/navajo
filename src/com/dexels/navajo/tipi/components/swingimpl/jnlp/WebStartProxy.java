package com.dexels.navajo.tipi.components.swingimpl.jnlp;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.jnlp.PersistenceService;

//import javax.jnlp.*;

import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.internal.cache.impl.CachedHttpJnlpResourceLoader;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class WebStartProxy {

	public static void appendJnlpCodeBase(SwingTipiContext myContext, String loaderType) {
	      try {
	    	  javax.jnlp.BasicService bs = (javax.jnlp.BasicService)javax.jnlp.ServiceManager.lookup("javax.jnlp.BasicService");

			System.err.println("BS: "+bs.getCodeBase());
			URL tipiCodeBase = new URL(bs.getCodeBase(),loaderType);
			System.err.println("TipiBS: "+bs.getCodeBase());
			URL resourceCodeBase = new URL(bs.getCodeBase(),"resource");
			
			myContext.setTipiResourceLoader(tipiCodeBase.toString());
			myContext.setGenericResourceLoader(resourceCodeBase.toString());
			System.err.println("WEBSTARTPROXY found: "+tipiCodeBase);
	      } catch (javax.jnlp.UnavailableServiceException e) {
			System.err.println("Service unavailable");
//			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.err.println("URL trouble");
			e.printStackTrace();
		} 
	      
	}

	
	public static TipiResourceLoader createDefaultWebstartLoader(
			String relativePath, boolean useCache, CookieManager manager) throws IOException {
		javax.jnlp.BasicService bs;
		try {
			bs = (javax.jnlp.BasicService) javax.jnlp.ServiceManager
					.lookup("javax.jnlp.BasicService");
			System.err.println("BS: " + bs.getCodeBase());
			URL codeURL = new URL(bs.getCodeBase(), relativePath);
			System.err.println("Using code url: " + codeURL);
			if (useCache) {
				try {

					return new CachedHttpJnlpResourceLoader(relativePath,codeURL,manager);
				} catch (javax.jnlp.UnavailableServiceException e) {
					System.err
							.println("Cached HTTP/JNLP cacheloader failed. Returning uncached loader.");
					return new HttpResourceLoader(codeURL.toString());
				}
			} else {
				return new HttpResourceLoader(codeURL.toString());

			}
		} catch (javax.jnlp.UnavailableServiceException e) {

			e.printStackTrace();
			throw new IOException("Error loading basic webstartservice: ", e);
		}

	}


	public static boolean hasJnlpContext() {
		try {
			javax.jnlp.BasicService bs = (javax.jnlp.BasicService)javax.jnlp.ServiceManager.lookup("javax.jnlp.BasicService");
			return bs!=null;
		} catch (Exception e) {
			return false;
		}
	}
}
