package com.dexels.navajo.tipi.internal.cookie;

import java.io.*;

public interface CookieManager {
	public String getCookie(String key);

	public void setCookie(String key, String value);

	public void saveCookies() throws IOException;

	public void loadCookies() throws IOException;
}
