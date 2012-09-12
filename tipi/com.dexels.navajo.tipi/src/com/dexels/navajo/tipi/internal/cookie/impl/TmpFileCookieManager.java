package com.dexels.navajo.tipi.internal.cookie.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class TmpFileCookieManager implements CookieManager {
	protected final Map<String, String> cookieMap = new HashMap<String, String>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(TmpFileCookieManager.class);
	
	private File cookieFile = null;

	public String getCookie(String key) {
		String cookieValue = cookieMap.get(key);
		return cookieValue;
	}

	public void setCookie(String key, String value) {
		cookieMap.put(key, value);
		try {
			saveCookies();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveCookies() throws IOException {
		FileOutputStream fis = new FileOutputStream(cookieFile);
		saveCookieWithStream(fis);
		fis.close();
	}

	protected void saveCookieWithStream(OutputStream fos) {
		PrintWriter fw = new PrintWriter(fos);
		Set<String> ss = cookieMap.keySet();
		for (String key : ss) {
			fw.println(key + "|" + cookieMap.get(key));
		}
		fw.flush();
		// fw.close();
	}

	public void loadCookies() throws IOException {
		cookieFile = new File(new File(System.getProperty("java.io.tmpdir")),
				"tipi.cookie");

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(cookieFile);
			loadCookieFromStream(fis);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	/**
	 * Will not close the stream! Remember.
	 * 
	 * @param fis
	 * @throws IOException
	 */
	protected void loadCookieFromStream(InputStream fis) throws IOException {
		BufferedReader fw = new BufferedReader(new InputStreamReader(fis));
		String line = fw.readLine();
		while (line != null) {
			if (line.equals("")) {
				line = fw.readLine();
				continue;
			}
			if (line.indexOf('|') != -1) {
				StringTokenizer st = new StringTokenizer(line, "|");
				String key = st.nextToken();
				String value = st.nextToken();
				cookieMap.put(key, value);
				line = fw.readLine();
			}
		}
	}

	public void deleteCookies() throws IOException {
		cookieFile.delete();
		cookieMap.clear();
		logger.info("Tmp file cookies deleted!");
	}

}
