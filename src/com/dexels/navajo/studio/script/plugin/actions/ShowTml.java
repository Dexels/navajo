/*
 * Created on May 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import java.io.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowTml extends BaseNavajoAction {

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        try {
            IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(file.getProject(), scriptName);
            if (tmlFile != null) {
                //                System.err.println("not null");
                //                System.err.println("TML: "+tmlFile.getFullPath().toString());
                if (!tmlFile.exists()) {
                    try {
                        tmlFile.refreshLocal(0, null);
                    } catch (CoreException e) {
                         e.printStackTrace();
                    }
                }
                if (tmlFile.exists()) {
                    //                    System.err.println("And it exists");
                    NavajoScriptPluginPlugin.getDefault().showTml(tmlFile);
                }
            }
        } catch (NavajoPluginException e) {
            e.printStackTrace();
        }

    }

}
