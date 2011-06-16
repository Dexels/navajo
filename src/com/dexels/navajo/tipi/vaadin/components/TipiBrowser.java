package com.dexels.navajo.tipi.vaadin.components;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Embedded;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiBrowser extends TipiVaadinComponentImpl {
	Embedded browser = null;

	public TipiBrowser() {
	}

	public Object createContainer() {

		browser = new Embedded("Embedded browser");
		browser.setType(Embedded.TYPE_BROWSER);

		return browser;
	}

	protected void setComponentValue(String name, Object object) {
        if ("url".equals(name)) {
        	String bare = ""+object;
        	setUrl(bare);
        }
        super.setComponentValue(name, object);
    }


	private void setUrl(String url) {
		try {
			URL u = new URL(url);
			ExternalResource er = new ExternalResource(u);
			browser.setSource(er);
		} catch (MalformedURLException e) {
			System.err.println("Not a regular url. Trying file: "+url);
			File base = getVaadinApplication().getInstallationFolder();
			File fil = new File(base,""+url);
			System.err.println("Trying file: "+fil);
			if(fil.exists()) {
				System.err.println("Found!");
				final FileResource fr = new FileResource(fil,getVaadinApplication());
				browser.setSource(fr);
			}
		}
	}

}
