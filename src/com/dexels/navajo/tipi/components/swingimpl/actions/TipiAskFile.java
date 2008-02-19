/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.*;
import java.io.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiAskFile extends TipiAction {

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.tipi.internal.TipiEvent)
     */
    protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
        Operand globalvalue = getEvaluatedParameter("value", event);
        JFileChooser jf = new JFileChooser();
        Object o = myComponent.getContainer();
        Container c = null;
        if (o instanceof Container) {
            c = (Container) o;
        }
        try {
			jf.setCurrentDirectory(new File(System.getProperty("user.home")));
		} catch (SecurityException e) {
			throw new TipiException("No file access allowed. Sorry.");
		}
        int result = jf.showSaveDialog(c);
        if (result != JFileChooser.APPROVE_OPTION) {
            throw new TipiBreakException(0);
        }
        File f = jf.getSelectedFile();

        myContext.setGlobalValue((String) globalvalue.value, "" + f.getAbsolutePath());
    }

}
