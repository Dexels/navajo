package com.dexels.navajo.tipi.vaadin.cookie;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class BrowserCookieManager implements CookieManager, Serializable {
	private static final long serialVersionUID = 8084634201430716891L;
	private transient HttpServletRequest request;
	private transient HttpServletResponse response;

	private Cookie createCookie(String s) {
		Cookie cc = new Cookie(s, "");
		cc.setPath("/");
		cc.setMaxAge(60*60*24*365);

		return cc;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	// TODO refactor into cookiemanager
	@Override
	public void setCookie(String name, String value) {
		Cookie cc = getBrowserCookie(name);
		if (cc == null) {
			cc = createCookie(name);
		}
		cc.setValue(value);
		response.addCookie(cc);
	}
	
	// TODO refactor into cookiemanager
	public Cookie getBrowserCookie(String s) {
		request.getCookies();
		
		Cookie[] cc = request.getCookies();
		for (int i = 0; i < cc.length; i++) {
			if (cc[i].getName().equals(s)) {
				return cc[i];
			}
		}
		return null;
	}


	@Override
	public String getCookie(String key) {
		Cookie c = getBrowserCookie(key);
		if(c!=null) {
			return c.getValue();
		}
		return null;
	}



	@Override
	public void loadCookies() throws IOException {
		// nothing
	}

	@Override
	public void saveCookies() throws IOException {
		// nothing
	}

	@Override
	public void deleteCookies() throws IOException {
		Cookie[] cc = request.getCookies();
		for (Cookie cookie : cc) {
			cookie.setMaxAge(0);
		}
	}

}
