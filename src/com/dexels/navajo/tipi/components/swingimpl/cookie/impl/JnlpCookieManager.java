package com.dexels.navajo.tipi.components.swingimpl.cookie.impl;

import java.io.*;
import java.net.*;

import javax.jnlp.*;

import com.dexels.navajo.tipi.internal.cookie.*;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class JnlpCookieManager implements CookieManager {
    PersistenceService ps; 
    BasicService bs; 

	public JnlpCookieManager() throws MalformedURLException, IOException {
	
	    try { 
	        ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService"); 
	        bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService"); 
	    } catch (UnavailableServiceException e) { 
	        ps = null; 
	        bs = null; 
	    } 

	    if (ps != null && bs != null) { 
	            // find all the muffins for our URL
	            URL codebase = bs.getCodeBase(); 
	            System.err.println("MY CODEBASE:"+codebase);
	            String [] muffins = ps.getNames(codebase); 
	            for (int i = 0; i < muffins.length; i++) {
		            System.err.println("Muffin: "+muffins[i]);
				}
	            ps.create(new URL("brrrr"), 10000);
	    }
	}
	
	public String getCookie(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadCookies() throws IOException {
		// TODO Auto-generated method stub

	}

	public void saveCookies() throws IOException {
		// TODO Auto-generated method stub

	}

	public void setCookie(String key, String value) {
		// TODO Auto-generated method stub

	}

}
