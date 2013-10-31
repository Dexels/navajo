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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
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
   
   private final static Logger logger = LoggerFactory
		.getLogger(TipiOpenBrowser.class);
	/*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.tipi.internal.TipiEvent)
     */
    @Override
	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
        Object url  = getEvaluatedParameterValue("url", event);
		Object newWindow = getEvaluatedParameterValue("newWindow",
				event);
		boolean openNewWindow = true;

		if(newWindow!=null && (newWindow instanceof Boolean)) {
			openNewWindow =  ((Boolean)newWindow).booleanValue();
			
		}
		try {
			URL u = new URL(""+url);
			if (openNewWindow) {
				getApplication().getMainWindow().open(new ExternalResource(u),"_blank");
			} else {
				getApplication().getMainWindow().open(new ExternalResource(u));

			}
		} catch (MalformedURLException e) {
			logger.warn("Malformed URL: "+url +". Ok, try a file:");
			// ... so u might be a file:
			File base = ((VaadinTipiContext)getContext()).getInstallationFolder();
			logger.debug("Base resolved to: "+base.getAbsolutePath());
			File fil = new File(base,""+url);
			logger.debug("File resolved to: "+fil.getAbsolutePath());
			
			if(fil.exists()) {
				final FileResource fr = new FileResource(fil,getApplication());
				if (openNewWindow) {
					getApplication().getMainWindow().open(fr,"_blank");
				} else {
					getApplication().getMainWindow().open(fr);
				}
			}
		}
    }

}
