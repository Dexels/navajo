package com.dexels.navajo.tipi.vaadin.components;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
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
	private static final long serialVersionUID = 6384086271954436821L;
	private Embedded browser = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBrowser.class);
	
	public TipiBrowser() {
	}

	public Object createContainer() {

		browser = new Embedded();
		browser.setType(Embedded.TYPE_BROWSER);
		browser.setImmediate(true);
//		browser.setHeight("100px");
//		browser.setWidth("100px");
		//		browser.setSizeFull();
		return browser;
	}

	protected void setComponentValue(String name, Object object) {
        if ("url".equals(name)) {
        	String bare = ""+object;
        	setUrl(bare);
        	
        	return;
        }
        if ("binary".equals(name)) {
        	setBinary((Binary)object);
        }
        if ("property".equals(name)) {
        	setProperty((Property)object);
        }
        super.setComponentValue(name, object);
    }


	private void setBinary(final Binary binary) {
		logger.debug("Setting binary content. Length:  "+binary.getLength()+" type: "+binary.getMimeType());
		StreamResource sr = new StreamResource(new StreamSource() {
			
			private static final long serialVersionUID = -352043364387051337L;

			@Override
			public InputStream getStream() {
				return binary.getDataAsStream();
			}
		},"m.html",getVaadinApplication());
		if(binary.getMimeType()!=null) {
			sr.setMIMEType(binary.getMimeType());
		} else {
			sr.setMIMEType(binary.guessContentType());
		}
		browser.setSource(sr);
	}

	private void setProperty(Property p) {
		
		Object value = p.getTypedValue();
		if(value==null) {
			logger.warn("Null value property passed to TipiBrowser");
			return;
		}
		if(!(value instanceof Binary)) {
			logger.warn("Non-binary value property passed to TipiBrowser");
			return;
		}
		final Binary binary = (Binary)value;
		logger.debug("Setting binary content. Length:  "+binary.getLength()+" type: "+binary.getMimeType()+" property: "+p.getSubType("browserBinary"));
		StreamResource sr = new StreamResource(new StreamSource() {
			
			private static final long serialVersionUID = -352043364387051337L;

			@Override
			public InputStream getStream() {
				return binary.getDataAsStream();
			}
		},"m.html",getVaadinApplication());
		if(binary.getMimeType()!=null) {
			sr.setMIMEType(binary.getMimeType());
		} else {
			sr.setMIMEType(binary.guessContentType());
		}
		browser.setSource(sr);
	}
	
	private void setUrl(String url) {
		try {
			URL u = new URL(url);
			ExternalResource er = new ExternalResource(u);
			logger.debug("Navigating to: "+u);
			logger.debug("Size: "+browser.getHeight()+"  "+browser.getHeight());
			browser.setSource(er);
//			browser.requestRepaint();
		} catch (MalformedURLException e) {
			logger.debug("Not a regular url. Trying file: "+url);
			File base = getVaadinContext().getInstallationFolder();
			File fil = new File(base,""+url);
			logger.debug("Trying file: "+fil);
			if(fil.exists()) {
				logger.debug("Found!");
				final FileResource fr = new FileResource(fil,getVaadinApplication());
				browser.setSource(fr);
			}
		}
	}

}
