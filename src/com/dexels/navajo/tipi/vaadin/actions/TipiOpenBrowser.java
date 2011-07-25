/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.vaadin.actions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.actions.base.TipiVaadinActionImpl;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.FileResource;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiOpenBrowser extends TipiVaadinActionImpl {

   private static final long serialVersionUID = -7235686945038762814L;

	/*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.tipi.internal.TipiEvent)
     */
    protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
        Object url  = getEvaluatedParameterValue("url", event);
        try {
			URL u = new URL(""+url);
			getVaadinApplication().getMainWindow().open(new ExternalResource(u),"_blank");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			// ... so u might be a file:
			File base = getVaadinApplication().getInstallationFolder();
			File fil = new File(base,""+url);
			System.err.println("Trying file: "+fil);
			if(fil.exists()) {
				System.err.println("Found!");
				final FileResource fr = new FileResource(fil,getVaadinApplication());
//				  URIHandler uriHandler = new URIHandler() {
//				        public DownloadStream handleURI(URL context,
//				                                        String relativeUri) {
//				            // Do something here
//				            System.out.println("handleURI=" + relativeUri+" context: "+relativeUri);
//
//				            // Should be null unless providing dynamic data.
//				            return fr.getStream();
//				        }
//				   };
				getVaadinApplication().getMainWindow().open(fr,"_blank");

//				URL url = new URL("http://dev.vaadin.com/");
//				Embedded browser = new Embedded("", new ExternalResource(url));
//				browser.setType(Embedded.TYPE_BROWSER);
//				main.addComponent(browser);				
				
			}
		}
    }

}
