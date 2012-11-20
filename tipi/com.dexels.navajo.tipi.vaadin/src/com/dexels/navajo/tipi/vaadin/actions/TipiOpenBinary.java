/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.vaadin.actions;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.actions.base.TipiVaadinActionImpl;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;

public class TipiOpenBinary extends TipiVaadinActionImpl {

	private static final long serialVersionUID = -6166751362772736306L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiOpenBinary.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.
	 * tipi.internal.TipiEvent)
	 */
	protected void execute(TipiEvent event) throws TipiBreakException,
			TipiException {
		Object evaluatedParameterValue = getEvaluatedParameterValue("binary",
				event);
		Object newWindow = getEvaluatedParameterValue("newWindow",
				event);
		String disposition = (String) getEvaluatedParameterValue("disposition",event);
		boolean openNewWindow = true;
		if(newWindow!=null && (newWindow instanceof Boolean)) {
			openNewWindow =  ((Boolean)newWindow).booleanValue();
			
		}
		Binary bb = null;
		if (evaluatedParameterValue instanceof Binary) {
			bb = (Binary) evaluatedParameterValue;
		}
		if (evaluatedParameterValue instanceof URL) {
			URL u = (URL) evaluatedParameterValue;
			try {
				bb = new Binary(u.openStream(), false);
			} catch (IOException e) {
				logger.error("Error: ",e);
				bb = null;
			}
		}
		if (evaluatedParameterValue instanceof String) {
			URL u;
			try {
				u = new URL((String) evaluatedParameterValue);
				try {
					bb = new Binary(u.openStream(), false);
				} catch (IOException e) {
					logger.error("Error: ",e);
					bb = null;
				}
			} catch (MalformedURLException e1) {
				logger.error("Error: ",e1);
			}
		}
		
		final Binary b = bb;

		if (b == null) {
			return;
		}

		StreamSource ss = new StreamSource() {
			
			private static final long serialVersionUID = -3276273030576419841L;

			@Override
			public InputStream getStream() {
				return b.getDataAsStream();
			}
			
			
		};
		Random r = new Random(System.currentTimeMillis());
		int rand = r.nextInt()%10000;
		
		StreamResource sr = new StreamResource( ss, "file"+rand+"."+b.getExtension(), getApplication());
		if(disposition==null) {
			disposition = "inline";
		}
		sr.getStream().setParameter("Content-Disposition", disposition);
	    sr.getStream().setParameter("Cache-Control","no-store, no-cache, no-transform, must-revalidate, private");

		if(openNewWindow) {
			getApplication().getMainWindow().open(sr,"_blank");
		} else {
			getApplication().getMainWindow().open(sr);
		}

	}
}
