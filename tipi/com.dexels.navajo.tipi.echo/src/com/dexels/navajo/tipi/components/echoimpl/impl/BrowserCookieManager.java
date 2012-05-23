package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.io.IOException;

import javax.servlet.http.Cookie;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.webcontainer.ContainerContext;
import nextapp.echo2.webcontainer.command.BrowserSetCookieCommand;

import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class BrowserCookieManager implements CookieManager {
	private Cookie createCookie(String s) {
		Cookie cc = new Cookie(s, "");
		cc.setPath("/");
		cc.setMaxAge(60*60*24*365);

		return cc;
	}

	public void setCookie(String name, String value) {
		Cookie cc = getBrowserCookie(name);
		if (cc == null) {
			cc = createCookie(name);
		}
		cc.setValue(value);
		BrowserSetCookieCommand bs = new BrowserSetCookieCommand(cc);
		ApplicationInstance.getActive().enqueueCommand(bs);
	}
	
	public Cookie getBrowserCookie(String s) {
		ContainerContext containerContext = (ContainerContext) ApplicationInstance.getActive().getContextProperty(
				ContainerContext.CONTEXT_PROPERTY_NAME);
		Cookie[] cc = containerContext.getCookies();
		for (int i = 0; i < cc.length; i++) {
			if (cc[i].getName().equals(s)) {
				return cc[i];
			}
		}
		return null;
	}


	public String getCookie(String key) {
		Cookie c = getBrowserCookie(key);
		if(c!=null) {
			return c.getValue();
		}
		return null;
	}



	public void loadCookies() throws IOException {
		// nothing
	}

	public void saveCookies() throws IOException {
		// nothing
	}

	public void deleteCookies() throws IOException {
//		ContainerContext containerContext = (ContainerContext) ApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
//		Cookie[] cc = containerContext.getCookies();
		System.err.println("Warning: Echo cookie deletion not implemented");
	}

}
