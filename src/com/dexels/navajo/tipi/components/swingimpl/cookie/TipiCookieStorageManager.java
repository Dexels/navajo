package com.dexels.navajo.tipi.components.swingimpl.cookie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL; 

import javax.jnlp.BasicService;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiStorageManager;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class TipiCookieStorageManager implements TipiStorageManager {

	private TipiContext myContext;
	PersistenceService ps; 
   BasicService bs; 

	public TipiCookieStorageManager() {
	    try { 
	        ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService"); 
	        bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService"); 
	    } catch (UnavailableServiceException e) { 
	        ps = null; 
	        bs = null; 
	    } 

	    
	}
	
	public Navajo getStorageDocument(String id) throws TipiException {
		FileContents fc;
		try {
			id = cleanUpId(id);
			fc = ps.get(new URL(bs.getCodeBase(),id));
			InputStream inputStream = fc.getInputStream();
			Navajo n = NavajoFactory.getInstance().createNavajo(inputStream);
			return n;
		} catch (MalformedURLException e) {
			throw new TipiException("Error loading cookie: "+id,e);
		} catch (FileNotFoundException e) {
			throw new TipiException("Error loading cookie: "+id,e);
		} catch (IOException e) {
			throw new TipiException("Error loading cookie: "+id,e);
		}
	}

	public void setInstanceId(String id) {
		// ignoring?
	}
	
	
	private String cleanUpId(String id) {
		id = id.replace('/', '_');
		id = id.replace('\\', '_');
		id = id.replace(':', '_');
		id = id.replace('|', '_');

		return id;
	}

	public void setStorageDocument(String id, Navajo n) throws TipiException {
		try {
			id = cleanUpId(id);
			URL cookieURL = new URL(bs.getCodeBase(),id);
			try {
				FileContents fc = ps.get(cookieURL);
				// found, as we did not jump
//				ps.delete(cookieURL);
			} catch (FileNotFoundException e) {
				System.err.println("Cookie not found. Thats fine.");
				long allowed = ps.create(cookieURL, 100000);
				System.err.println("New muffin, size granted: "+allowed);
			}
//			InputStream inputStream = fc.getInputStream();
			FileContents  ff= ps.get(cookieURL);
			OutputStream os = ff.getOutputStream(true);
			n.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new TipiException("Error storing document: "+id,e);
		} catch (NavajoException e) {
			e.printStackTrace();
			throw new TipiException("Error storing document: "+id,e);
		}
	}

	public void setContext(TipiContext tc) {
		myContext = tc;
	}

	public static void main(String[] args) {
		String s = "http://www.sportlink.com/apps/teaminschrijvingen_test_frank/admin|c:/vladb/columns_teamRegistrationClubOverviewWindow_nonTeams.tml";
		try {
			URL u = new URL(s);
			System.err.println("Not malformed");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
}
