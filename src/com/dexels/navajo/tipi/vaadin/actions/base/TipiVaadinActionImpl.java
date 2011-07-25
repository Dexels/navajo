package com.dexels.navajo.tipi.vaadin.actions.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.vaadin.terminal.StreamResource;

public abstract class TipiVaadinActionImpl extends TipiAction {

	
	private static final long serialVersionUID = 5997392321011697285L;


	// TODO Use version in TipiVaadinComponentIml
	@SuppressWarnings("serial")
	public StreamResource getResource(Object u) {
		 if(u==null) {
			 return null;
		 }
		 InputStream is = null;
		 if(u instanceof URL) {
			 System.err.println("URL: "+u);
			 try {
				is = ((URL) u).openStream();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		 }
		 if(u instanceof Binary) {
			 is = ((Binary) u).getDataAsStream();
		 }
		 if(is==null) {
			 return null;
		 }
		 final InputStream stream = is; 
		 StreamResource.StreamSource s = new StreamResource.StreamSource() {
			
			@Override
			public InputStream getStream() {
				return stream;
			}
			
			
		};
		StreamResource sr = new StreamResource(s, ""+u, getVaadinApplication());
		return sr;
	}


	public TipiVaadinApplication getVaadinApplication() {
		VaadinTipiContext c = (VaadinTipiContext) getContext();
		return c.getVaadinApplication();
	}
}
