/*
 * Created on Apr 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import org.eclipse.core.internal.resources.*;
import org.eclipse.core.resources.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;

import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FindTml extends BaseNavajoAction {

    public FindTml() {
        super();
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
        //    		MessageDialog.openInformation(
        //    			window.getShell(),
        //    			"Navajo Studio Plug-in",
        //    			scriptName);
        try {
            IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(file.getProject(), scriptName);
            if (tmlFile != null) {
                //                System.err.println("not null");
                //                System.err.println("TML: "+tmlFile.getFullPath().toString());
                if (tmlFile.exists()) {
                    //                    System.err.println("And it exists");
                    NavajoScriptPluginPlugin.getDefault().openInEditor(tmlFile);
                } else {
                    NavajoScriptPluginPlugin.getDefault().showInfo("Tml file for: "+scriptName+" not found. Run the script first.");
                }
            }
        } catch (NavajoPluginException e) {
           e.printStackTrace();
        }
    }

}
