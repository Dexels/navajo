package com.dexels.navajo.tipi.internal.cookie.impl;

import java.io.*;
import java.util.*;

import com.dexels.navajo.tipi.internal.cookie.*;

public class TmpFileCookieManager implements CookieManager {
	private final Map<String, String> cookieMap = new HashMap<String, String>();

	public String getCookie(String key) {
		return cookieMap.get(key);
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
		File tmp = new File(System.getProperty("java.io.tmpdir"));
		File f = new File(tmp, "tipi.cookie");
		PrintWriter fw = new PrintWriter(new FileWriter(f, false));
		Set<String> ss = cookieMap.keySet();
		for (String key : ss) {
			fw.println(key + "|" + cookieMap.get(key));
		}
		fw.flush();
		fw.close();
	}

	public void loadCookies() throws IOException {
		File tmp = new File(System.getProperty("java.io.tmpdir"));
		File f = new File(tmp, "tipi.cookie");
		BufferedReader fw = new BufferedReader(new FileReader(f));
		String line = fw.readLine();
		while (line != null) {
			StringTokenizer st = new StringTokenizer(line, "|");
			String key = st.nextToken();
			String value = st.nextToken();
			cookieMap.put(key, value);
			line = fw.readLine();
		}
	}


}
