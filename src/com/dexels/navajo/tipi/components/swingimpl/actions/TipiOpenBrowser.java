/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.components.swingimpl.TipiApplet;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiOpenBrowser extends TipiAction {

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.tipi.internal.TipiEvent)
     */
    protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
        Operand url  = getEvaluatedParameter("url", event);
        String urlVal = (String)url.value;
        TipiApplet rr = ((SwingTipiContext)myContext).getAppletRoot();
        if(rr!=null) {
        	try {
				rr.getAppletContext().showDocument(new URL(urlVal), "_blank");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else {
            DefaultBrowser.displayURL(urlVal);
        }
    }

}
