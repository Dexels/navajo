/*
 * Created on May 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

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
                         NavajoScriptPluginPlugin.getDefault().log("Error refreshing TML file. Strange.",e);
                         
                    }
                }
                if (tmlFile.exists()) {
                    //                    System.err.println("And it exists");
                    NavajoScriptPluginPlugin.getDefault().showTml(tmlFile,scriptName);
                }else {
                    NavajoScriptPluginPlugin.getDefault().showInfo("Tml file for: "+scriptName+" not found. Run the script first.");
                }
            } else {
                NavajoScriptPluginPlugin.getDefault().showInfo("Tml file for: "+scriptName+" not found. Run the script first.");
            } 
        } catch (Exception e) {
            NavajoScriptPluginPlugin.getDefault().showInfo("Error retrieving TML file: "+e.getMessage());
        }

    }

}
