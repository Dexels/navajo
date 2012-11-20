package com.dexels.navajo.tipi.components.swingimpl.cookie.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.jnlp.BasicService;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cookie.CookieManager;
import com.dexels.navajo.tipi.internal.cookie.impl.TmpFileCookieManager;

public class JnlpCookieManager extends TmpFileCookieManager implements
		CookieManager {
	PersistenceService ps;
	BasicService bs;

	
	private final static Logger logger = LoggerFactory
			.getLogger(JnlpCookieManager.class);
	
	
	public JnlpCookieManager() {

		try {
			ps = (PersistenceService) ServiceManager
					.lookup("javax.jnlp.PersistenceService");
			bs = (BasicService) ServiceManager
					.lookup("javax.jnlp.BasicService");
		} catch (UnavailableServiceException e) {
			ps = null;
			bs = null;
		}

		if (ps != null && bs != null) {
			try {
				loadCookies();
			} catch (IOException e) {
				logger.debug("No cookies. Probably ok.");
			}
		}
	}

	// private void loadCookieMuffin(String path) throws MalformedURLException,
	// FileNotFoundException, IOException {
	// FileContents fc = ps.get(new URL(bs.getCodeBase(),path));
	// logger.debug("Muffin found, filled with cookies. Size: "+fc.getLength());
	// InputStream inputStream = fc.getInputStream();
	// loadCookieFromStream(inputStream);
	// inputStream.close();
	// logger.debug("Current cookiemap: "+cookieMap);
	// }

	public void loadCookies() throws IOException {
		FileContents fc = ps.get(new URL(bs.getCodeBase(), "tipi.cookie"));
		InputStream inputStream = fc.getInputStream();
		loadCookieFromStream(inputStream);
		inputStream.close();
	}

	@SuppressWarnings("unused")
	public void saveCookies() throws MalformedURLException, IOException {

		URL cookieURL = new URL(bs.getCodeBase(), "tipi.cookie");
		try {
			// TODO Reuse fc if it is retrieved successfully
			FileContents fc = ps.get(cookieURL);
			// found, as we did not jump
			// ps.delete(cookieURL);
		} catch (FileNotFoundException e) {
			logger.debug("Cookie not found. Thats fine.");
			// allow for a bit bigger cookie
			long allowed = ps.create(cookieURL, 100000);
			logger.debug("New muffin, size granted: " + allowed);
		}
		// InputStream inputStream = fc.getInputStream();
		FileContents ff = ps.get(cookieURL);
		OutputStream os = ff.getOutputStream(true);

		saveCookieWithStream(os);
		os.flush();
		os.close();
		// loadCookieFromStream(inputStream);
		// inputStream.close();

	}



	@Override
	public void deleteCookies() throws IOException {

		URL cookieURL = new URL(bs.getCodeBase(), "tipi.cookie");
		ps.delete(cookieURL);
		cookieMap.clear();
		logger.debug("JNLP Cookie deleted!");
	}

}
