/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.vaadin.actions;

import java.net.URL;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.actions.base.TipiVaadinActionImpl;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.URIHandler;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiOpenBinary extends TipiVaadinActionImpl {

	private static final long serialVersionUID = -6166751362772736306L;

	/*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.tipi.internal.TipiEvent)
     */
    protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
        final Binary b  = (Binary) getEvaluatedParameterValue("binary", event);
        if(b==null) {
        	System.err.println("No binary found");
        	return;
        }
//			
			  URIHandler uriHandler = new URIHandler() {
				private static final long serialVersionUID = 1L;

					public DownloadStream handleURI(URL context,
			                                        String relativeUri) {
			            // Do something here
			            System.out.println("handleURI=" + relativeUri);

			            // Should be null unless providing dynamic data.
			            return new DownloadStream(b.getDataAsStream(), b.guessContentType(), "download"+b.getExtension());
			        }
			    };
			    getVaadinApplication().getMainWindow().addURIHandler(uriHandler);
    }
}
