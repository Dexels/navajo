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
			if(fil.exists()) {
				final FileResource fr = new FileResource(fil,getVaadinApplication());
				getVaadinApplication().getMainWindow().open(fr,"_blank");
			}
		}
    }

}
