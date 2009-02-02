package com.dexels.navajo.tipi.components.swingimpl.jnlp;

import java.io.*;
import java.net.*;

//import javax.jnlp.*;

import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;

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

	
	public static TipiResourceLoader createDefaultWebstartLoader(String relativePath) throws IOException {
		javax.jnlp.BasicService bs;
				try {
					bs = (javax.jnlp.BasicService)javax.jnlp.ServiceManager.lookup("javax.jnlp.BasicService");
					System.err.println("BS: "+bs.getCodeBase());
					URL codeURL = new URL(bs.getCodeBase(),relativePath);
					System.err.println("Using code url: "+codeURL);
					return new HttpResourceLoader(codeURL.toString());
				} catch (javax.jnlp.UnavailableServiceException e) {
					
					e.printStackTrace();
					throw new IOException("Error loading basic webstartservice: ",e);
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
