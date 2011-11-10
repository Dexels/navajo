/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.vaadin.actions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.actions.base.TipiVaadinActionImpl;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;

public class TipiOpenBinary extends TipiVaadinActionImpl {

	private static final long serialVersionUID = -6166751362772736306L;

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
		Binary bb = null;
		if (evaluatedParameterValue instanceof Binary) {
			bb = (Binary) evaluatedParameterValue;
		}
		if (evaluatedParameterValue instanceof URL) {
			URL u = (URL) evaluatedParameterValue;
			System.err.println("URL detected: " + u);
			try {
				bb = new Binary(u.openStream(), false);
			} catch (IOException e) {
				e.printStackTrace();
				bb = null;
			}
		}
		final Binary b = bb;

		if (b == null) {
			System.err.println("No binary found");
			return;
		}

		StreamSource ss = new StreamSource() {
			
			private static final long serialVersionUID = -3276273030576419841L;

			@Override
			public InputStream getStream() {
				return b.getDataAsStream();
			}
		};
		StreamResource sr = new StreamResource( ss, "aap."+b.getExtension(), getVaadinApplication());
		getVaadinApplication().getMainWindow().open(sr,"_blank");

	}
}
